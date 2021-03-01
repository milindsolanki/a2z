package com.a2z.deliver.activities.ItemSummary;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.databinding.ActivityItemSummaryBinding;
import com.a2z.deliver.databinding.LayoutAlertDialogBinding;
import com.a2z.deliver.models.driverInterested.DriverInterestedMaster;
import com.a2z.deliver.models.items.DeliveryAddress;
import com.a2z.deliver.models.items.ItemDetailMaster;
import com.a2z.deliver.models.items.ItemDetails;
import com.a2z.deliver.models.items.PickupAddress;
import com.a2z.deliver.R;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.networking.Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import javax.inject.Inject;

public class ItemSummaryActivity extends BaseApp implements ItemSummaryView, View.OnClickListener {

    boolean visibleLayout = false;
    ActivityItemSummaryBinding binding;
    @Inject
    public Service service;
    ItemDetails itemDetails;
    SharedPref sharedPref;
    LoginDetails loginDetails;
    String orderid, senderid;
    public static final int REQUEST_CODE = 010;
    ItemSummaryPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps( ).injectItems( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_item_summary );
        binding.setClickListener( this );
        binding.layoutHeaderItemSummary.setClickListener( this );
        init( );
    }

    public void init() {

        sharedPref = SharedPref.getInstance( this );
        loginDetails = sharedPref.getLoginDetailsModel( );
        binding.layoutHeaderItemSummary.tvApplyFilter.setText( getResources( ).getString( R.string.interested ) );
        binding.layoutHeaderItemSummary.tvApplyFilter.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
        binding.layoutHeaderItemSummary.tvHeaderText.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
        //binding.layoutLong.layoutLong.setVisibility( View.GONE );
        presenter = new ItemSummaryPresenter( this, service, this, binding );
        presenter.getItemsApi( itemDetailParems( ) );
    }

    private JsonObject itemDetailParems() {
        Intent intent = getIntent( );
        orderid = intent.getStringExtra( API_Params.ORDER_ID );
        senderid = intent.getStringExtra( API_Params.SENDER_ID );
        Log.e( "USER ID ", loginDetails.getUserId( ) );
        Log.e( "ORDER ID ", orderid );
        JsonObject gsonObject = new JsonObject( );
        try {
            JSONObject jsonObject = new JSONObject( );

            jsonObject.put( API_Params.userId, loginDetails.getUserId( ) );
            jsonObject.put( API_Params.orderId, orderid );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (Exception e) {
            e.printStackTrace( );
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
        CommonUtils.makeToast( getApplicationContext( ), appErrorMessage );
    }

    @Override
    public void getItemSuccess(ItemDetailMaster itemDetailMaster) {
        if (itemDetailMaster != null && itemDetailMaster.getItemDetails( ) != null) {
            if (itemDetailMaster.getSuccess( ) == 1) {
                ItemDetails itemDetails = itemDetailMaster.getItemDetails( );
                PickupAddress pickupAddress = itemDetails.getPickupAddress( );
                DeliveryAddress deliveryAddress = itemDetails.getDeliveryAddress( );

                Log.e( "JSON RESPONSE: ", new Gson( ).toJson( itemDetailMaster.getItemDetails( ) ).toString( ) );

                binding.setItem( itemDetails );
                /*binding.layoutShort.setItem( itemDetails );
                binding.layoutLong.setItem( itemDetails );*/
                binding.layoutHeaderItemSummary.tvHeaderText.setText( itemDetails.getItemName( ) );
                String itemType = "";
                if (!CommonUtils.isEmpty( itemDetails.getItemType( ) )) {
                    itemType = itemDetails.getItemType( );
                }
                String whoWillPass = "";
                if (!CommonUtils.isEmpty( itemDetails.getWhoWillPass( ) )) {
                    whoWillPass = itemDetails.getWhoWillPass( );
                }
                presenter.checkItemType( itemType );

                if (whoWillPass.equalsIgnoreCase( "1" )) {
                    /*binding.layoutLong.tvPickupName.setText( "I will pass" );
                    binding.layoutLong.tvPickupPhone.setVisibility( View.GONE );
                    binding.layoutLong.tvPickupEmail.setVisibility( View.GONE );*/
                } else if (whoWillPass.equalsIgnoreCase( "0" )) {
                   /* binding.layoutLong.tvPickupName.setText( itemDetails.getPassName() );
                    binding.layoutLong.tvPickupPhone.setText( itemDetails.getPassMobile() );
                    binding.layoutLong.tvPickupEmail.setText( itemDetails.getPasstEmailId() );*/
                }


                if (itemDetails.getWhoWillMeet( ).toString( ).equalsIgnoreCase( "1" )) {
                   /* binding.layoutLong.tvDeliverName.setText( "I will meet" );
                    binding.layoutLong.tvDeliverPhone.setVisibility( View.GONE );
                    binding.layoutLong.tvDeliverEmail.setVisibility( View.GONE );*/
                } else if (itemDetails.getWhoWillMeet( ).toString( ).equalsIgnoreCase( "0" )) {
                   /* binding.layoutLong.tvDeliverName.setText( itemDetails.getMeetName() );
                    binding.layoutLong.tvDeliverPhone.setText( itemDetails.getMeetMobile() );
                    binding.layoutLong.tvDeliverEmail.setText( itemDetails.getMeetEmailId() );*/
                }

            } else if (itemDetailMaster.getSuccess( ) == 0) {
                CommonUtils.makeToast( getApplicationContext( ), "Failure" );
            }
        } else {

            Log.e( "API RESPONSE", "NULL" );
        }
    }

    @Override
    public void getDriverInterestedSuccess(DriverInterestedMaster driverInterestedMaster) {
        if (driverInterestedMaster != null) {
            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( driverInterestedMaster ).toString( ) );
            if (driverInterestedMaster.getSuccess( ) == 1) {
                if (driverInterestedMaster.getDocuments( ) == "0") {
                    Intent intent = new Intent( ItemSummaryActivity.this, MainActivity.class );
                    startActivityForResult( intent, REQUEST_CODE );
                }
                CommonUtils.makeLongToast( getApplicationContext( ), driverInterestedMaster.getMessage( ) );
            } else if (driverInterestedMaster.getSuccess( ) == 0) {
                Toast.makeText( getApplicationContext( ), driverInterestedMaster.getMessage( ), Toast.LENGTH_SHORT ).show( );
            }
        }
    }


    public void onClick(View v) {
        if (v == binding.layoutHeaderItemSummary.ivLoginBack) {
            finish( );
        } else if (v == binding.layoutHeaderItemSummary.tvApplyFilter) {
            alertDailog( );
        } else if (v == binding.btnInterest) {
            alertDailog( );
        } else if (v == binding.layoutHeaderItemSummary.tvApplyFilter) {
            alertDailog( );
            binding.layoutHeaderItemSummary.tvApplyFilter.setVisibility( View.GONE );

        }
    }

    public void alertDailog() {
        final Dialog dialog = new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.layout_alert_dialog );
        dialog.getWindow( ).setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        dialog.getWindow( ).setGravity( Gravity.CENTER );

        Button btnOk = ( Button ) dialog.findViewById( R.id.btn_ok );
        btnOk.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                dialog.dismiss( );
                loginDetails = sharedPref.getLoginDetailsModel( );
                if (loginDetails != null) {
                    presenter.getDriverInterestedApi( getDriverParams( ) );
                } else {
                    Intent intent = new Intent( ItemSummaryActivity.this, MainActivity.class );
                    startActivityForResult( intent, REQUEST_CODE );
                }
            }
        } );
        dialog.show( );
    }

    private JsonObject getDriverParams() {
        JsonObject gsonObject = new JsonObject( );
        try {
            itemDetails = new ItemDetails( );
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.orderId, orderid );
            jsonObject.put( API_Params.driverId, loginDetails.getUserId( ) );
            jsonObject.put( API_Params.senderId, senderid );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (Exception e) {
            e.printStackTrace( );
        }
        return gsonObject;
    }
}
