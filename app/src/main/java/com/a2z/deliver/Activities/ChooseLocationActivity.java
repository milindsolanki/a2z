package com.a2z.deliver.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a2z.deliver.Adapters.ChooseLocationAdapter;
import com.a2z.deliver.Interfaces.OnAddressClickListener;
import com.a2z.deliver.Models.ChooseLocation.AddressDetail;
import com.a2z.deliver.Models.ChooseLocation.AddressMaster;
import com.a2z.deliver.Models.Login.LoginDetails;
import com.a2z.deliver.R;
import com.a2z.deliver.Utils.CommonUtils;
import com.a2z.deliver.Utils.SharedPref;
import com.a2z.deliver.Views.CustomTextview;
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
import butterknife.OnClick;
import retrofit2.Call;

public class ChooseLocationActivity extends AppCompatActivity implements ApiResponseInterface, OnAddressClickListener {

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.tv_applyFilter)
    CustomTextview tvApplyFilter;
    @BindView(R.id.rv_location)
    RecyclerView rvLocation;
    @BindView(R.id.layout_setlocation)
    LinearLayout layoutSetlocation;
    SharedPref sharedPref;
    LoginDetails loginDetails;
    AddressDetail addressDetail;
    Activity activity;
    ApiManager apiManager;
    ProgressDialog progressDialog;

    String addressId;
    public ChooseLocationAdapter recyclerViewAdapter;
    public List <AddressDetail> addLocationList = new ArrayList <>( );
    String fromScreen = "";

    public static final int REQUEST_PICKUP_ADDRESS = 101;
    public static final int REQUEST_DELIVERY_ADDRESS = 102;

    public static final int REQUEST_EDIT_ADDRESS= 103;

    String isEdit="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose_location );
        ButterKnife.bind( this );
        tvHeaderText.setText( R.string.choosepicup );
        progressDialog = new ProgressDialog( ChooseLocationActivity.this );
        progressDialog.setMessage( "Signing you in..." );
        progressDialog.setTitle( "A2Z" );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        progressDialog.setCancelable( false );

        activity = this;
        fromScreen = getIntent( ).getExtras( ).getString( API_Params.FROM_SCREEN );
        //isEdit = getIntent( ).getExtras( ).getString( API_Params.isEDIT );
        sharedPref = new SharedPref( this );
        String response = sharedPref.getLoginDetails( );
        final Gson gson = new Gson( );
        Type collectionType = new TypeToken <LoginDetails>( ) {
        }.getType( );
        loginDetails = gson.fromJson( response, collectionType );

        String responseaddress = sharedPref.getAddressDetails( );
        final Gson gsonaddress = new Gson( );
        Type addresscollectionType = new TypeToken <AddressDetail>( ) {
        }.getType( );
        addressDetail = gsonaddress.fromJson( responseaddress, addresscollectionType );

        apiManager = new ApiManager( this, this );
        recyclerViewAdapter = new ChooseLocationAdapter( activity, addLocationList, this );
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager( getApplicationContext( ) );
        rvLocation.setLayoutManager( mlayoutManager );
        rvLocation.setItemAnimator( new DefaultItemAnimator( ) );
        rvLocation.setAdapter( recyclerViewAdapter );
        chooseLocation( );

    }

    private void chooseLocation() {
        progressDialog.show( );
        Call <AddressMaster> addressMasterCall = ApiHandler.getApiService( ).chooselocationpApi( getchooselocationParams( ) );
        apiManager.makeApiRequest( addressMasterCall, API_Codes.getAddressList );
    }

    private JsonObject getchooselocationParams() {
        JsonObject gsonObject = new JsonObject( );
        try {
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.userId, loginDetails.getUserId( ) );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (Exception e) {
            e.printStackTrace( );
        }
        return gsonObject;
    }

    @OnClick(R.id.layout_setlocation)
    public void onSetMapLocationClick() {
        Intent intent = new Intent( ChooseLocationActivity.this, SetMapLocationActivity.class );
        intent.putExtra( API_Params.FROM_SCREEN, fromScreen );
        intent.putExtra( API_Params.ADDRESS_TYPE,"3" );
        intent.putExtra( API_Params.IS_EDIT,"0" );
        intent.putExtra(  API_Params.ADDRESS_ID,"0");
        if (fromScreen.equals( API_Params.PICKUP_SCREEN )) {
            startActivityForResult( intent, REQUEST_PICKUP_ADDRESS );
        } else if (fromScreen.equals( API_Params.DELIVERY_SCREEN )) {
            startActivityForResult( intent, REQUEST_DELIVERY_ADDRESS );
        }
    }

    @OnClick(R.id.iv_login_back)
    public void onLoginBackClicked() {
        finish( );
    }

    @Override
    public void isError(String errorCode, int apiCode) {
        if (progressDialog != null && progressDialog.isShowing( ))
            progressDialog.dismiss( );
        CommonUtils.makeToast( getApplicationContext( ), "Network Error!!" );
    }

    @Override
    public void isSuccess(Object response, int apiCode) {
        if (apiCode == API_Codes.getAddressList) {
            if (progressDialog != null && progressDialog.isShowing( ))
                progressDialog.dismiss( );

            AddressMaster addressMaster = ( AddressMaster ) response;

            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( addressMaster ).toString( ) );

            String AddressMaster = new Gson( ).toJson( addressMaster.getAddressList()).toString( );
            sharedPref.storeAddressDetails(AddressMaster  );

            if (addressMaster.getSuccess( ) == 1) {
                addLocationList.clear( );
                addLocationList.addAll( addressMaster.getAddressList( ) );
                recyclerViewAdapter.notifyDataSetChanged( );
            } else if (addressMaster.getSuccess( ) == 0) {
                Toast.makeText( getApplicationContext( ), "Invalid  Credentials!", Toast.LENGTH_SHORT ).show( );
            }
        }

    }

    @Override
    public void onAddressClick(AddressDetail addressDetail) {
        Log.e( "address", addressDetail.toString( ) );
        Intent intent = new Intent( );
        intent.putExtra( API_Params.RESULT, addressDetail );
        addressId =addressDetail.getAddressId();
        if ( addressId == null){
            addressId="0";
        }else{
            addressId="1";
        }

        if (addressDetail.getDisplayName().equalsIgnoreCase( "home" ) ||
                addressDetail.getDisplayName().equalsIgnoreCase( "office" )) {
            Log.e("intent","fill");
            if (fromScreen.equals( API_Params.PICKUP_SCREEN )) {
                setResult( API_Params.RESULT_PICKUP_ADDRESS, intent );
            } else if (fromScreen.equals( API_Params.DELIVERY_SCREEN )) {
                setResult( API_Params.RESULT_DELIVERY_ADDRESS, intent );
            }
            finish( );
        } else {
            if (addressDetail.getDisplayName().equalsIgnoreCase( "add home" ) ||
                    addressDetail.getDisplayName().equalsIgnoreCase( "add office" )){
                isEdit="0";
               // addressId="0";
            }else if (addressDetail.getDisplayName().equalsIgnoreCase( " home" ) ||
                    addressDetail.getDisplayName().equalsIgnoreCase( " office" )){
                isEdit="1";
                //addressId="123";
            }

            Intent i=new Intent( ChooseLocationActivity.this,SetMapLocationActivity.class );
            Log.e("intent","null");
            i.putExtra( API_Params.FROM_SCREEN,fromScreen );
            i.putExtra( API_Params.IS_EDIT,isEdit );
            i.putExtra( API_Params.ADDRESS_ID,addressDetail.getAddressId() );
            intent.putExtra( API_Params.ADDRESS_TYPE,addressDetail.getAddressType()  );

            if (isEdit.equals( API_Params.PICKUP_SCREEN ) ){
                startActivityForResult( i,API_Params.RESULT_PICKUP_ADDRESS );
            }else if (isEdit.equals( API_Params.DELIVERY_SCREEN )){
                startActivityForResult( i,API_Params.RESULT_DELIVERY_ADDRESS );
            }

        }
    }

    @Override
    public void onEditClick(AddressDetail addressDetail) {
        Intent intent=new Intent( ChooseLocationActivity.this,SetMapLocationActivity.class );
        intent.putExtra( API_Params.FROM_SCREEN,fromScreen );
        intent.putExtra( API_Params.ADDRESS_TYPE,addressDetail.getAddressType() );
        intent.putExtra( API_Params.IS_EDIT,"1");
        intent.putExtra( API_Params.ADDRESS_ID,addressDetail.getAddressId() );
        startActivity( intent );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == API_Params.RESULT_PICKUP_ADDRESS) {
            if (requestCode == REQUEST_PICKUP_ADDRESS) {
                AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                Intent intent = new Intent( );
                intent.putExtra( API_Params.RESULT, addressDetail );
                setResult( API_Params.RESULT_PICKUP_ADDRESS, intent );
                finish( );
            }
        } else if (resultCode == API_Params.RESULT_DELIVERY_ADDRESS) {
            if (requestCode == REQUEST_DELIVERY_ADDRESS) {
                AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                Intent intent = new Intent( );
                intent.putExtra( API_Params.RESULT, addressDetail );
                setResult( API_Params.RESULT_DELIVERY_ADDRESS, intent );
                finish( );
            }


        }
    }
}
