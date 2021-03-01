package com.a2z.deliver.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a2z.deliver.Activities.FilterActivity;
import com.a2z.deliver.Activities.ItemSummaryActivity;
import com.a2z.deliver.Adapters.ItemAdapter;
import com.a2z.deliver.Interfaces.OnItemClickListener;
import com.a2z.deliver.Models.FiltersModel;
import com.a2z.deliver.Models.Items.ItemDetails;
import com.a2z.deliver.Models.Items.ItemMaster;
import com.a2z.deliver.Models.Login.LoginDetails;
import com.a2z.deliver.R;
import com.a2z.deliver.Utils.CommonUtils;
import com.a2z.deliver.Utils.RecyclerViewPositionHelper;
import com.a2z.deliver.Utils.SharedPref;
import com.a2z.deliver.WebService.API_Codes;
import com.a2z.deliver.WebService.API_Params;
import com.a2z.deliver.WebService.ApiHandler;
import com.a2z.deliver.WebService.ApiManager;
import com.a2z.deliver.WebService.ApiResponseInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

public class HomeFragment extends Fragment implements ApiResponseInterface, OnItemClickListener {

    @BindView(R.id.recyclerview_items)
    RecyclerView recyclerviewItems;
    Unbinder unbinder;
    @BindView(R.id.more_progress)
    ProgressBar moreProgress;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;

    private ItemAdapter adapter;
    private List<ItemDetails> itemDetailsList = new ArrayList<>();
    public static RecyclerView.LayoutManager layoutManager;
    private boolean userScrolled = true;

    SharedPref sharedPref;
    LoginDetails loginDetails;
    ApiManager apiManager;
    RecyclerViewPositionHelper mRecyclerViewHelper;
    FilterActivity filterActivity;

    int startlimit = 0;
    int totalCount = 0;
    boolean isApiLoading = false;
    int filter = 0;
    String category = "0";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        Log.e( "TEST", "HOME Fragment" );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e( "TEST", "HOME View " );
        View view = inflater.inflate( R.layout.fragment_home, container, false );
        unbinder = ButterKnife.bind( this, view );
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e( "TEST", "HOME View Created" );
        super.onViewCreated( view, savedInstanceState );

        ImageView ivHomeFilter = (ImageView) getActivity().findViewById( R.id.iv_home_filter );
        ImageView ivHomeMap = (ImageView) getActivity().findViewById( R.id.iv_home_map );
        TextView tvHeaderText = (TextView) getActivity().findViewById( R.id.tv_header_text );

        ImageView ivBottomHome = (ImageView) getActivity().findViewById( R.id.iv_bottom_home );
        TextView tvBottomHome = (TextView) getActivity().findViewById( R.id.tv_bottom_home );
        ImageView ivBottomMydelivery = (ImageView) getActivity().findViewById( R.id.iv_bottom_mydelivery );
        TextView tvBottomMydelivery = (TextView) getActivity().findViewById( R.id.tv_bottom_mydelivery );
        ImageView ivBottomMysend = (ImageView) getActivity().findViewById( R.id.iv_bottom_mysend );
        TextView tvBottomMysend = (TextView) getActivity().findViewById( R.id.tv_bottom_mysend );
        ImageView ivBottomMore = (ImageView) getActivity().findViewById( R.id.iv_bottom_more );
        TextView tvBottomMore = (TextView) getActivity().findViewById( R.id.tv_bottom_more );

        ivHomeMap.setImageResource( R.drawable.ic_map );
        ivBottomHome.setImageResource( R.drawable.ic_home_colorprimary );
        tvBottomHome.setTextColor( getResources().getColor( R.color.colorPrimary ) );
        ivBottomMydelivery.setImageResource( R.drawable.ic_steering_wheel_gray );
        tvBottomMydelivery.setTextColor( getResources().getColor( R.color.color_c1c1c1 ) );
        ivBottomMysend.setImageResource( R.drawable.ic_send_gray );
        tvBottomMysend.setTextColor( getResources().getColor( R.color.color_c1c1c1 ) );
        ivBottomMore.setImageResource( R.drawable.ic_more );
        tvBottomMore.setTextColor( getResources().getColor( R.color.color_c1c1c1 ) );

        ivHomeFilter.setVisibility( View.VISIBLE );
        ivHomeMap.setVisibility( View.VISIBLE );
        tvHeaderText.setText( R.string.pickyourdelivery );

        adapter = new ItemAdapter( getActivity(), itemDetailsList, this );

        layoutManager = new StaggeredGridLayoutManager( 2, LinearLayoutManager.VERTICAL );
        recyclerviewItems.setLayoutManager( layoutManager );
        recyclerviewItems.setItemAnimator( new DefaultItemAnimator() );
        recyclerviewItems.setAdapter( adapter );

        sharedPref = new SharedPref( getActivity() );
        String response = sharedPref.getLoginDetails();
        final Gson gson = new Gson();
        Type collectionType = new TypeToken<LoginDetails>() {}.getType();
        loginDetails = gson.fromJson( response, collectionType );
        apiManager = new ApiManager( getActivity(), this );

        //listApiCallng();
        onScrollListner();

        sharedPref = new SharedPref( getActivity() );

    }

    private void onScrollListner() {
        recyclerviewItems.setOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged( recyclerView, newState );
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );
                ItemMaster itemMaster = new ItemMaster();
                mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);

                int visibleItemCount = layoutManager.getItemCount();
                int lastVisible = mRecyclerViewHelper.findLastVisibleItemPosition();
                boolean endHasBeenReached = lastVisible + 5 >= visibleItemCount;

                if (!isApiLoading && startlimit < totalCount && endHasBeenReached){
                    adapter.setShowLoader( true );
                    startlimit = visibleItemCount;
                    listApiCallng();
                }
            }
        } );
    }

    private void listApiCallng() {
        Call<ItemMaster> itemMasterCall = ApiHandler.getApiService().getAllItemApi( itemParams() );
        apiManager.makeApiRequest( itemMasterCall, API_Codes.getAllItems );
    }

    private JsonObject itemParams() {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put( API_Params.userId, loginDetails.getUserId() );
            jsonObject.put( API_Params.deviceAccess, API_Params.DEVICE_ACCESS );
            jsonObject.put( API_Params.startLimit, startlimit );
            jsonObject.put( API_Params.isFilter,filter);
            jsonObject.put( API_Params.category, category);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse( jsonObject.toString() );

        } catch (Exception e) {
            Log.e( "tag", "error :" + e.getMessage() );
            e.printStackTrace();
        }
        return gsonObject;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void isError(String errorCode, int apiCode) {
        CommonUtils.makeToast( getActivity(), "Please Check your Network!" );
    }

    @Override
    public void isSuccess(Object response, int apiCode) {
        if (startlimit > 0){
            adapter.setShowLoader( false );
        }
        if (apiCode == API_Codes.getAllItems) {
            ItemMaster itemMaster = (ItemMaster) response;
            if (itemMaster != null) {
                if (itemMaster.getSuccess() == 1) {
                    itemDetailsList.addAll( itemMaster.getItemDetailsList() );
                } else if (itemMaster.getSuccess() == 0) {
                    CommonUtils.makeToast( getActivity(), "Failed to get data!" );
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        itemDetailsList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        String res = sharedPref.getFilters();
        final Gson gsn = new Gson();
        Type ct = new TypeToken<FiltersModel>() {}.getType();
        FiltersModel filtersModel = gsn.fromJson( res, ct );
        if (filtersModel != null) {
            filter = filtersModel.getIsFilter();
            category = filtersModel.getCategory().toString();
            Log.e( "BEFORE VALUE FILTER", "" + filter );
            Log.e( "BEFORE VALUE CATEGORY", category );
            if (category == null) {
                category = "0";
            }
            Log.e( "AFTER VALUE CATEGORY", category );
        }
        listApiCallng();
        onScrollListner();
    }

    @Override
    public void onItemClick(String orderId) {
        Intent intent = new Intent( getActivity(), ItemSummaryActivity.class );
        intent.putExtra( API_Params.USER_ID, orderId );
        startActivity( intent );
    }
}
