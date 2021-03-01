package com.a2z.deliver.fragments.toPickUp;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.a2z.deliver.BaseFragmentApp;
import com.a2z.deliver.R;
import com.a2z.deliver.adapters.ToPickUpAdapter;
import com.a2z.deliver.databinding.FragmentToPickUpBinding;
import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.models.toPickUp.ToPickUpDetails;
import com.a2z.deliver.models.toPickUp.ToPickUpMaster;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.RecyclerViewPositionHelper;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ToPickUpFragment extends BaseFragmentApp implements ToPickUpFragmentView, OnItemClickListener {

    private ToPickUpAdapter adapter;
    private List<ToPickUpDetails> pickUpDetailsList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    SharedPref sharedPref;
    LoginDetails loginDetails;
    FragmentToPickUpBinding binding;
    ToPickUpFragmentPresenter presenter;
    RecyclerViewPositionHelper mRecyclerViewHelper;
    @Inject
    Service service;

    int startlimit = 0;
    int totalCount = 0;
    boolean isApiLoading = false;
    String TAG = "ToPickUpFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
//        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDeps().injectTopickupOrders( this );
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_to_pick_up, container, false );
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        init();
        apiCalling();
        onScrollListner();
        refreshList();
    }

    private void init() {
        presenter = new ToPickUpFragmentPresenter( getActivity(), service, this );
        sharedPref = SharedPref.getInstance( getActivity() );
        loginDetails = sharedPref.getLoginDetailsModel();
        adapter = new ToPickUpAdapter( pickUpDetailsList, getActivity(), this );
        layoutManager = new LinearLayoutManager( getActivity().getApplicationContext() );
        binding.recyclerviewPickup.setLayoutManager( layoutManager );
        binding.recyclerviewPickup.setItemAnimator( new DefaultItemAnimator() );
        binding.recyclerviewPickup.setAdapter( adapter );
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint( isVisibleToUser );
//        if (isVisibleToUser){
//            apiCalling();
//        }
//    }

    private void apiCalling() {
        if (startlimit == 0) {
            pickUpDetailsList.clear();
            presenter.getPickupOrders( pickupParams() );
        }
    }

    private JsonObject pickupParams() {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put( API_Params.deviceAccess, API_Params.DEVICE_ACCESS );
            jsonObject.put( API_Params.userId, loginDetails.getUserId() );

            JsonParser parser = new JsonParser();
            gsonObject = (JsonObject) parser.parse( jsonObject.toString() );
            Log.e( "PARAMETERS", gsonObject.toString() );

        } catch (Exception e) {
            Log.e( TAG, "error :" + e.getMessage() );
            e.printStackTrace();
        }
        return gsonObject;
    }


    private void onScrollListner() {
        binding.recyclerviewPickup.setOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged( recyclerView, newState );
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );
                ToPickUpMaster toPickUpMaster = new ToPickUpMaster();
                mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper( recyclerView );

                int visibleItemCount = layoutManager.getItemCount();
                int lastVisible = mRecyclerViewHelper.findLastVisibleItemPosition();
                boolean endHasBeenReached = lastVisible + 5 >= visibleItemCount;

                if (!isApiLoading && startlimit < totalCount && endHasBeenReached) {
                    adapter.setShowLoader( true );
                    startlimit = visibleItemCount;
                    apiCalling();
                }
            }
        } );
    }


    private void refreshList() {
        binding.swipeRefreshHome.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startlimit = 0;
                apiCalling();
                binding.swipeRefreshHome.setRefreshing( false );
            }
        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showWait() {

    }

    @Override
    public void removeWait() {

    }

    @Override
    public void onFailure(String appErrorMessage) {
        CommonUtils.makeToast( getActivity(), "Network Error!" );
    }

    @Override
    public void getPickUpOrderSuccess(ToPickUpMaster toPickUpMaster) {
        if (startlimit > 0){
            adapter.setShowLoader( false );
        }
        if (toPickUpMaster != null){
            if (toPickUpMaster.getSuccess() == 1){
                binding.tvNodataPickup.setVisibility( View.GONE );
                pickUpDetailsList.addAll( toPickUpMaster.getPickUpDetails() );
            } else if (toPickUpMaster.getSuccess() == 0){
//                CommonUtils.makeToast( getActivity(), toPickUpMaster.getMessage() );
                binding.tvNodataPickup.setText( toPickUpMaster.getMessage() );
                binding.tvNodataPickup.setVisibility( View.VISIBLE );
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(String orderId,String senderId) {
        CommonUtils.makeToast( getActivity(), "" + orderId );
    }

    @Override
    public void onResume() {
        super.onResume();
        apiCalling();
    }
}
