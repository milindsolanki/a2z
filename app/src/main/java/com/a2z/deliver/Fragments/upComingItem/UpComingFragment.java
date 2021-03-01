package com.a2z.deliver.fragments.upComingItem;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.a2z.deliver.adapters.UpComingItemAdapter;
import com.a2z.deliver.databinding.FragmentUpcomingBinding;
import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.models.upComeingItem.UpComingItemDetails;
import com.a2z.deliver.models.upComeingItem.UpComingItemMaster;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.RecyclerViewPositionHelper;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UpcomingFragment extends BaseFragmentApp implements UpComingView,OnItemClickListener{

    FragmentUpcomingBinding binding;
    @Inject
    Service service;
    UpComingPresenter presenter;
    private UpComingItemAdapter adapter;
    private List<UpComingItemDetails> upComingItemDetails=new ArrayList <>(  );
    private RecyclerView.LayoutManager layoutManager;
    RecyclerViewPositionHelper mRecyclerViewHelper;
    SharedPref sharedPref;
    LoginDetails loginDetails;

    int startlimit = 0;
    int totalCount = 0;
    boolean isApiLoading = false;
    String TAG = "A2Z";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDeps().injectUpComing( this );
        binding= DataBindingUtil.inflate( inflater,R.layout.fragment_upcoming,container,false );
        View view=binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        init( );
        apiCalling( );
        onScrollListner( );
        refreshList( );
    }

    private void apiCalling() {
        if (startlimit == 0) {
            upComingItemDetails.clear();
            presenter.getOnGoingItem( getOnGoingItemsParams() );
        }
    }

    private JsonObject getOnGoingItemsParams() {
        JsonObject gsonObject = new JsonObject( );
        try {
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.deviceAccess, API_Params.DEVICE_ACCESS );
            jsonObject.put( API_Params.userId, loginDetails.getUserId( ) );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
            Log.e( "PARAMETERS", gsonObject.toString() );
        } catch (Exception e) {
            Log.e( TAG, "error :" + e.getMessage() );
            e.printStackTrace( );
        }
        return gsonObject;
    }

    private void init() {
        presenter = new UpComingPresenter( getActivity(),service,this );
        sharedPref = SharedPref.getInstance( getActivity( ) );
        loginDetails = sharedPref.getLoginDetailsModel( );
        adapter=new UpComingItemAdapter( upComingItemDetails, ( Activity ) getContext(),this );
        layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        binding.recyclerviewUpcoming.setLayoutManager( layoutManager );
        binding.recyclerviewUpcoming.setItemAnimator( new DefaultItemAnimator() );
        binding.recyclerviewUpcoming.setAdapter( adapter );
    }

    private void onScrollListner() {
        binding.recyclerviewUpcoming.setOnScrollListener( new RecyclerView.OnScrollListener( ) {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged( recyclerView, newState );
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );
                mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper( recyclerView );
                int visibleItemCount = layoutManager.getItemCount( );
                int lastVisible = mRecyclerViewHelper.findLastVisibleItemPosition( );
                boolean endHasBeenReached = lastVisible + 5 >= visibleItemCount;

                if (!isApiLoading && startlimit < totalCount && endHasBeenReached) {
                    adapter.setShowLoader( true );
                    startlimit = visibleItemCount;
                    apiCalling( );
                }

            }
        } );
    }

    private void refreshList() {
        binding.swipeRefreshOntheway.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener( ) {
            @Override
            public void onRefresh() {
                startlimit = 0;
                apiCalling( );
                binding.swipeRefreshOntheway.setRefreshing( false );
            }
        } );
    }

    @Override
    public void showWait() {

    }

    @Override
    public void removeWait() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getUpComingItems(UpComingItemMaster upComingItemMaster) {
        if (startlimit > 0) {
            adapter.setShowLoader( false );
        }
        if (upComingItemMaster != null) {
            Log.e( "UPCOMING RESPONSE: ", new Gson( ).toJson( upComingItemMaster ).toString( ) );
            if (upComingItemMaster.getSuccess( ) == 1) {
                Log.e("success","= 1");
                binding.tvNodataUpcoming.setVisibility( View.GONE );
                upComingItemDetails.addAll( upComingItemMaster.getUpComingItemDetailslist( ) );
            } else if (upComingItemMaster.getSuccess( ) == 0) {
                Log.e("success","= 0");
                binding.tvNodataUpcoming.setText( upComingItemMaster.getMessage() );
                binding.tvNodataUpcoming.setVisibility( View.VISIBLE );
            }
            adapter.notifyDataSetChanged( );
        }

    }

    @Override
    public void onItemClick(String orderId,String senderId) {

    }
}
