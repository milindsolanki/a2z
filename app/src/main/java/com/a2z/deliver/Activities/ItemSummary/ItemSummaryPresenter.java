package com.a2z.deliver.activities.ItemSummary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a2z.deliver.R;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.databinding.ActivityItemSummaryBinding;
import com.a2z.deliver.databinding.LayoutAlertDialogBinding;
import com.a2z.deliver.models.driverInterested.DriverInterestedMaster;
import com.a2z.deliver.models.items.ItemDetailMaster;
import com.a2z.deliver.models.items.ItemDetails;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.models.onTheWay.OnTheWayDetails;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ItemSummaryPresenter {
    private final Service service;
    private final ItemSummaryView view;
    private CompositeSubscription subscriptions;
    private ActivityItemSummaryBinding binding;
    ItemDetails itemDetails;
    private Activity activity;
    LoginDetails loginDetails;
    SharedPref sharedPref;

    public ItemSummaryPresenter(Activity activity, Service service, ItemSummaryView view, ActivityItemSummaryBinding binding) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription( );
        this.binding = binding;
        this.activity = activity;
    }

    public void getItemsApi(JsonObject jsonObject) {
        Log.e( "JSON PARAMS", new Gson( ).toJson( jsonObject ).toString( ) );
        view.showWait( );

        Subscription subscription = service.getItemSummaryList( jsonObject, new Service.GetItemDetailsCallback( ) {
            @Override
            public void onSuccess(ItemDetailMaster itemDetailMaster) {
                view.removeWait( );
                view.getItemSuccess( itemDetailMaster );
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait( );
                view.onFailure( networkError.getAppErrorMessage( ) );
            }
        } );
        subscriptions.add( subscription );
    }

    public void onStop() {
        subscriptions.unsubscribe( );
    }

    public void checkItemType(String itemType) {
        if (itemType.equalsIgnoreCase( API_Params.SMALL )) {
            binding.ivLongItemSizeImage.setImageResource( R.drawable.ic_small_select );
            binding.tvLongItemDescription.setText( R.string.small_text );
        } else if (itemType.equalsIgnoreCase( API_Params.MEDIUM )) {
            binding.ivLongItemSizeImage.setImageResource( R.drawable.ic_medium_select );
            binding.tvLongItemDescription.setText( R.string.medium_text );
            binding.tvLongItemDescription.setText( R.string.medium_text );
        } else if (itemType.equalsIgnoreCase( API_Params.LARGE )) {
            binding.ivLongItemSizeImage.setImageResource( R.drawable.ic_large_select );
            binding.tvLongItemDescription.setText( R.string.large_text );
            binding.tvLongItemDescription.setText( R.string.large_text );
        } else if (itemType.equalsIgnoreCase( API_Params.XLARGE )) {
            binding.ivLongItemSizeImage.setImageResource( R.drawable.ic_xlarge_select );
            binding.tvLongItemDescription.setText( R.string.xlarge_text );
            binding.tvLongItemDescription.setText( R.string.xlarge_text );
        } else if (itemType.equalsIgnoreCase( API_Params.HUGE )) {
            binding.ivLongItemSizeImage.setImageResource( R.drawable.ic_huge_select );
            binding.tvLongItemDescription.setText( R.string.huge_text );
            binding.tvLongItemDescription.setText( R.string.huge_text );
        } else if (itemType.equalsIgnoreCase( API_Params.PET )) {
            binding.ivLongItemSizeImage.setImageResource( R.drawable.ic_pet_select );
            binding.tvLongItemDescription.setText( R.string.pet_text );
            binding.tvLongItemDescription.setText( R.string.pet_text );
        } else {
            Toast.makeText( activity, R.string.itemtypenotfound, Toast.LENGTH_SHORT ).show( );
        }
    }
    /*--------------------------------------------Driver Interested------------------------------------------------*/

    public void getDriverInterestedApi(JsonObject jsonObject) {
        Log.e( "JSON PARAMS", new Gson( ).toJson( jsonObject ).toString( ) );
        view.showWait( );

        Subscription subscription = service.getDriverInterested( jsonObject, new Service.GetDriverInterestedCallback( ) {
            @Override
            public void onSuccess(DriverInterestedMaster driverInterestedMaster) {
                view.removeWait( );
                view.getDriverInterestedSuccess( driverInterestedMaster );
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait( );
                view.onFailure( networkError.getAppErrorMessage( ) );
            }
        } );
        subscriptions.add( subscription );
    }



}
