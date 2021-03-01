package com.a2z.deliver.fragments.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import com.a2z.deliver.activities.ItemSummary.ItemSummaryActivity;
import com.a2z.deliver.adapters.ItemAdapter;
import com.a2z.deliver.BaseFragmentApp;
import com.a2z.deliver.interfaces.OnItemClickListener;
import com.a2z.deliver.models.FiltersModel;
import com.a2z.deliver.models.items.ItemDetails;
import com.a2z.deliver.models.items.ItemMaster;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.RecyclerViewPositionHelper;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.databinding.FragmentHomeBinding;
import com.a2z.deliver.networking.Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class HomeFragment extends BaseFragmentApp implements OnItemClickListener, HomeFragmentView {

    private ItemAdapter adapter;
    private List<ItemDetails> itemDetailsList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    SharedPref sharedPref;
    LoginDetails loginDetails;
    RecyclerViewPositionHelper mRecyclerViewHelper;

    int startlimit = 0;
    int totalCount = 0;
    boolean isApiLoading = false;
    String filter = "0";
    String category = "0";
    String TAG = "TAG";

    FragmentHomeBinding binding;
    HomeFragmentPresenter presenter;
    @Inject
    Service service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDeps().injectHomeFragment( this );
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false );
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        init();
        listApiCallng();
        onScrollListner();
        setAction();
    }

    private void init() {
        presenter = new HomeFragmentPresenter( getActivity(), service, this );
        sharedPref = SharedPref.getInstance( getActivity() );
        loginDetails = sharedPref.getLoginDetailsModel();
        adapter = new ItemAdapter( getActivity(), itemDetailsList, this );
        layoutManager = new StaggeredGridLayoutManager( 2, LinearLayoutManager.VERTICAL );
        binding.recyclerviewItems.setLayoutManager( layoutManager );
        binding.recyclerviewItems.setItemAnimator( new DefaultItemAnimator() );
        binding.recyclerviewItems.setAdapter( adapter );
    }

    private void onScrollListner() {
        binding.recyclerviewItems.setOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged( recyclerView, newState );
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );
                ItemMaster itemMaster = new ItemMaster();
                mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper( recyclerView );

                int visibleItemCount = layoutManager.getItemCount();
                int lastVisible = mRecyclerViewHelper.findLastVisibleItemPosition();
                boolean endHasBeenReached = lastVisible + 5 >= visibleItemCount;

                if (!isApiLoading && startlimit < totalCount && endHasBeenReached) {
                    adapter.setShowLoader( true );
                    startlimit = visibleItemCount;
                    listApiCallng();
                }
            }
        } );
    }

    private void listApiCallng() {
        if (startlimit == 0)
            itemDetailsList.clear();
        presenter.getItemList( itemParams() );
    }

    private JsonObject itemParams() {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put( API_Params.userId, loginDetails.getUserId() );
            jsonObject.put( API_Params.deviceAccess, API_Params.DEVICE_ACCESS );
            jsonObject.put( API_Params.startLimit, startlimit );
            jsonObject.put( API_Params.isFilter, filter );
            jsonObject.put( API_Params.category, category );

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse( jsonObject.toString() );
            Log.e( "PARAMETERS", gsonObject.toString() );

        } catch (Exception e) {
            Log.e( TAG, "error :" + e.getMessage() );
            e.printStackTrace();
        }
        return gsonObject;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void getFilterItemListApi() {
        String res = sharedPref.getFilters();
        final Gson gsn = new Gson();
        Type ct = new TypeToken<FiltersModel>() {
        }.getType();
        FiltersModel filtersModel = gsn.fromJson( res, ct );
        if (filtersModel != null) {
            filter = filtersModel.getIsFilter().toString();
            category = filtersModel.getCategory().toString();
            Log.e( "BEFORE VALUE FILTER", "" + filter );
            Log.e( "BEFORE VALUE CATEGORY", "" + category );
            if (category == null) {
                category = "0";
            }
            Log.e( "AFTER VALUE CATEGORY", "" + category );
        }

        itemDetailsList.clear();
        adapter.notifyDataSetChanged();
        listApiCallng();
    }

    @Override
    public void onItemClick(String orderId,String senderId) {
        Intent intent = new Intent( getActivity(), ItemSummaryActivity.class );
        intent.putExtra( API_Params.ORDER_ID, orderId );
        intent.putExtra( API_Params.SENDER_ID,senderId);
        startActivity( intent );
    }

    private void setAction() {
        binding.swipeRefreshHome.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startlimit = 0;
                listApiCallng();
                binding.swipeRefreshHome.setRefreshing( false );
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
    public void getItemListSuccess(ItemMaster itemMaster) {
        if (startlimit > 0) {
            adapter.setShowLoader( false );
        }
        if (itemMaster != null) {
            if (itemMaster.getSuccess() == 1) {
                itemDetailsList.addAll( itemMaster.getItemDetailsList() );
            } else if (itemMaster.getSuccess() == 0) {
                CommonUtils.makeToast( getActivity(), itemMaster.getMessage() );
            }
            adapter.notifyDataSetChanged();
        }
    }

}
