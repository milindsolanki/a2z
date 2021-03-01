package com.a2z.deliver.activities.chooseLocation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.activities.setMapLocation.SetMapLocationActivity;
import com.a2z.deliver.adapters.ChooseLocationAdapter;
import com.a2z.deliver.databinding.ActivityChooseLocationBinding;
import com.a2z.deliver.interfaces.OnAddressClickListener;
import com.a2z.deliver.models.chooseLocation.AddressDetail;
import com.a2z.deliver.models.chooseLocation.AddressMaster;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.R;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChooseLocationActivity extends BaseApp implements OnAddressClickListener, View.OnClickListener, ChooseLocationView {

    SharedPref sharedPref;
    LoginDetails loginDetails;
    Activity activity;
    ProgressDialog progressDialog;

    String addressId;
    public ChooseLocationAdapter recyclerViewAdapter;
    public List <AddressDetail> addLocationList = new ArrayList <>( );
    String fromScreen = "";

    public static final int REQUEST_PICKUP_ADDRESS = 101;
    public static final int REQUEST_DELIVERY_ADDRESS = 102;
    public static final int REQUEST_EDIT_ADDRESS = 103;

    String isEdit = "";
    ActivityChooseLocationBinding binding;
    ChooseLocationPresenter presenter;
    @Inject
    Service service;
    private static final String TAG = ChooseLocationActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps().injectChooseLocation(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_location);
        binding.setClickListener(this);
        binding.layoutHeader.setClickListener(this);
        init();

    }

    private void init() {
        activity = this;
        presenter = new ChooseLocationPresenter(activity, service, this);
        binding.layoutHeader.tvHeaderText.setText( R.string.choosepicup );

        fromScreen = getIntent( ).getExtras( ).getString( API_Params.FROM_SCREEN );
        sharedPref = SharedPref.getInstance( ChooseLocationActivity.this );
        loginDetails = sharedPref.getLoginDetailsModel();
        recyclerViewAdapter = new ChooseLocationAdapter( activity, addLocationList, this );
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager( getApplicationContext( ) );
        binding.rvLocation.setLayoutManager( mlayoutManager );
        binding.rvLocation.setItemAnimator( new DefaultItemAnimator( ) );
        binding.rvLocation.setAdapter( recyclerViewAdapter );
        getAddressList( );
    }

    private void getAddressList() {
        presenter.getAddressListApi();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.layoutSetlocation) {
            setLocationClick();
        } else if (v == binding.layoutHeader.ivLoginBack) {
            finish();
        }
    }

    private void setLocationClick() {
        Intent intent = new Intent( ChooseLocationActivity.this, SetMapLocationActivity.class );
        intent.putExtra( API_Params.FROM_SCREEN, fromScreen );
        intent.putExtra( API_Params.ADDRESS_TYPE, "3" );
        intent.putExtra( API_Params.IS_EDIT, "2" );
        intent.putExtra( API_Params.ADDRESS_ID, "0" );
        if (fromScreen.equals( API_Params.PICKUP_SCREEN )) {
            startActivityForResult( intent, REQUEST_PICKUP_ADDRESS );
        } else if (fromScreen.equals( API_Params.DELIVERY_SCREEN )) {
            startActivityForResult( intent, REQUEST_DELIVERY_ADDRESS );
        }
    }

    @Override
    public void onAddressClick(AddressDetail addressDetail) {
        Log.e( "address type ", addressDetail.getAddressType() );
        Intent intent = new Intent( );
        intent.putExtra( API_Params.RESULT, addressDetail );
        addressId = addressDetail.getAddressId( );
        if (addressId == null) {
            addressId = "0";
        } else {
            addressId = "1";
        }

        if (addressDetail.getDisplayName( ).equalsIgnoreCase( "add home" ) ||
                addressDetail.getDisplayName( ).equalsIgnoreCase( "add office" )) {
            isEdit = "0";
            Intent i = new Intent( ChooseLocationActivity.this, SetMapLocationActivity.class );
            i.putExtra( API_Params.FROM_SCREEN, fromScreen );
            i.putExtra( API_Params.IS_EDIT, isEdit );
            i.putExtra( API_Params.ADDRESS_ID, addressId );
            i.putExtra( API_Params.ADDRESS_TYPE, addressDetail.getAddressType( ) );

            if (fromScreen.equals( API_Params.PICKUP_SCREEN )) {
                startActivityForResult( i, API_Params.RESULT_PICKUP_ADDRESS );
            } else if (fromScreen.equals( API_Params.DELIVERY_SCREEN )) {
                startActivityForResult( i, API_Params.RESULT_DELIVERY_ADDRESS );
            }
        } else {
            if (fromScreen.equals( API_Params.PICKUP_SCREEN )) {
                setResult( API_Params.RESULT_PICKUP_ADDRESS, intent );
            } else if (fromScreen.equals( API_Params.DELIVERY_SCREEN )) {
                setResult( API_Params.RESULT_DELIVERY_ADDRESS, intent );
            }
            finish( );
        }

    }

    @Override
    public void onEditClick(AddressDetail addressDetail) {
        Intent intent = new Intent( ChooseLocationActivity.this, SetMapLocationActivity.class );
        intent.putExtra( API_Params.FROM_SCREEN, fromScreen );
        intent.putExtra( API_Params.ADDRESS_TYPE, addressDetail.getAddressType( ) );
        intent.putExtra( API_Params.IS_EDIT, "1" );
        intent.putExtra( API_Params.ADDRESS_ID, addressDetail.getAddressId( ) );
        startActivityForResult( intent, REQUEST_EDIT_ADDRESS );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == API_Params.RESULT_PICKUP_ADDRESS) {
            if (requestCode == REQUEST_PICKUP_ADDRESS) {
                String is_edit = data.getExtras( ).getString( API_Params.IS_EDIT );
                if (is_edit.equals( "2" )) {
                    AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                    Intent intent = new Intent( );
                    intent.putExtra( API_Params.RESULT, addressDetail );
                    setResult( API_Params.RESULT_PICKUP_ADDRESS, intent );
                    finish( );
                } else {
                    getAddressList( );
                }
            } else {
                getAddressList();
            }
        } else if (resultCode == API_Params.RESULT_DELIVERY_ADDRESS) {
            if (requestCode == REQUEST_DELIVERY_ADDRESS) {
                String is_edit = data.getExtras( ).getString( API_Params.IS_EDIT );
                if (is_edit.equals( "2" )) {
                    AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                    Intent intent = new Intent( );
                    intent.putExtra( API_Params.RESULT, addressDetail );
                    setResult( API_Params.RESULT_DELIVERY_ADDRESS, intent );
                    finish( );
                } else {
                    getAddressList( );
                }
            } else {
                getAddressList();
            }
        }
    }

    @Override
    public void showWait() {

        CommonUtils.setProgressDialog( activity,getResources().getString( R.string.app_name ),getResources().getString( R.string.listingaddress ) );

    }

    @Override
    public void removeWait() {
        CommonUtils.hideProgressDialog();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        progressDialog.dismiss();
        Log.e(TAG, "Error : " + appErrorMessage);
    }

    @Override
    public void getAddressList(AddressMaster addressMaster) {
        if (addressMaster != null) {
            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( addressMaster ).toString( ) );
            if (addressMaster.getSuccess( ) == 1) {
                addLocationList.clear( );
                addLocationList.addAll( addressMaster.getAddressList( ) );
                recyclerViewAdapter.notifyDataSetChanged( );
            } else if (addressMaster.getSuccess( ) == 0) {
                Toast.makeText( getApplicationContext( ), "Invalid  Credentials!", Toast.LENGTH_SHORT ).show( );
            }
        }
    }
}
