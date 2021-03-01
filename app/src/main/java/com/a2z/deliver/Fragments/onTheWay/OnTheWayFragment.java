package com.a2z.deliver.fragments.onTheWay;

import android.app.Activity;
import android.content.Intent;
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
import com.a2z.deliver.activities.pickupDetails.PickUpDetailsActivity;
import com.a2z.deliver.adapters.OnTheWayAdapter;
import com.a2z.deliver.databinding.FragmentOnTheWayBinding;
import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.interfaces.OnItemDetailClickListeners;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.models.onTheWay.OnTheWayDetails;
import com.a2z.deliver.models.onTheWay.OnTheWayMaster;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.RecyclerViewPositionHelper;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OnTheWayFragment extends BaseFragmentApp implements OnTheWayFragmentView, OnItemDetailClickListeners {

    private OnTheWayAdapter adapter;
    private List<OnTheWayDetails> onTheWayDetailsList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    SharedPref sharedPref;
    LoginDetails loginDetails;
    FragmentOnTheWayBinding binding;
    OnTheWayFragmentPresenter presenter;
    RecyclerViewPositionHelper helper;
    @Inject
    Service service;
    Activity activity;

    int startlimit = 0;
    int totalCount = 0;
    boolean isApiLoading = false;
    String TAG = "A2Z";
    String OnTheWayModel = "OnTheWayModel";
    int REQUEST_CODE = 112;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
//        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDeps().injectOnTheWayOrders( this );
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_on_the_way, container, false );
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
        presenter = new OnTheWayFragmentPresenter( getActivity(), service, this );
        sharedPref = SharedPref.getInstance( getActivity() );
        loginDetails = sharedPref.getLoginDetailsModel();
        adapter = new OnTheWayAdapter( onTheWayDetailsList, getActivity(), this );
        layoutManager = new LinearLayoutManager( getActivity() );
        binding.recyclerviewOntheway.setLayoutManager( layoutManager );
        binding.recyclerviewOntheway.setItemAnimator( new DefaultItemAnimator() );
        binding.recyclerviewOntheway.setAdapter( adapter );
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint( isVisibleToUser );
//        if (isVisibleToUser){
//            apiCalling();
//        }
//    }
//    Load this when you want to load data on fragment visible

    private void apiCalling() {
        if (startlimit == 0) {
            onTheWayDetailsList.clear();
            presenter.getOnTheWayOrders( onTheWayParams() );
        }
    }

    private void onScrollListner() {
        binding.recyclerviewOntheway.setOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged( recyclerView, newState );
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );
                OnTheWayMaster onTheWayMaster = new OnTheWayMaster();
                helper = RecyclerViewPositionHelper.createHelper( recyclerView );

                int visibleItemCount = layoutManager.getItemCount();
                int lastVisible = helper.findLastVisibleItemPosition();
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
        binding.swipeRefreshOntheway.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startlimit = 0;
                apiCalling();
                binding.swipeRefreshOntheway.setRefreshing( false );
            }
        } );
    }

    private JsonObject onTheWayParams() {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put( API_Params.deviceAccess, API_Params.DEVICE_ACCESS );
            jsonObject.put( API_Params.userId, loginDetails.getUserId() );

            JsonParser parser = new JsonParser();
            gsonObject = (JsonObject) parser.parse( jsonObject.toString() );
            Log.e( "OnTheWay Params ", gsonObject.toString() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsonObject;
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
    public void getOnTheWayOrdersSuccess(OnTheWayMaster theWayMaster) {
        if (startlimit > 0) {
            adapter.setShowLoader( false );
        }
        if (theWayMaster != null) {
            if (theWayMaster.getSuccess() == 1) {

                binding.tvNodata.setVisibility( View.GONE );
                onTheWayDetailsList.addAll( theWayMaster.getResult() );
//                Log.e( TAG, "Response: " + new Gson().toJson( theWayMaster ).toString());
            } else if (theWayMaster.getSuccess() == 0) {
//                CommonUtils.makeToast( getActivity(), theWayMaster.getMessage() );
                binding.tvNodata.setText( theWayMaster.getMessage() );
                binding.tvNodata.setVisibility( View.VISIBLE );
            }
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
    }

    @Override
    public void onItemClick(OnTheWayDetails details) {
        Intent intent = new Intent( getActivity(), PickUpDetailsActivity.class );
        intent.putExtra( OnTheWayModel, details );
        startActivityForResult( intent, REQUEST_CODE );
    }


}
