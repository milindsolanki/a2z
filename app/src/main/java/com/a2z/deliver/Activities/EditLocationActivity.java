package com.a2z.deliver.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.a2z.deliver.Models.ChooseLocation.AddressDetail;
import com.a2z.deliver.Models.ChooseLocation.GoogleAddress;
import com.a2z.deliver.Models.EditLocation.EditLocationMaster;
import com.a2z.deliver.Models.Login.LoginDetails;
import com.a2z.deliver.R;
import com.a2z.deliver.Utils.CommonUtils;
import com.a2z.deliver.Utils.SharedPref;
import com.a2z.deliver.Views.CustomEditText;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class EditLocationActivity extends AppCompatActivity implements ApiResponseInterface {

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.tv_applyFilter)
    CustomTextview tvApplyFilter;
    @BindView(R.id.appbar_editAddress)
    AppBarLayout appbarEditAddress;
    String fromScreen = "";
    public static final int REQUEST_EDIT_ADDRESS = 103;
    String isedit = "";

    public static final int REQUEST_PICKUP_ADDRESS = 101;
    public static final int REQUEST_DELIVERY_ADDRESS = 102;

    @BindView(R.id.et_editaddress)
    CustomEditText etEditaddress;
    @BindView(R.id.et_unit_editlocation)
    CustomEditText etUnitEditlocation;
    @BindView(R.id.et_companyname_editlocation)
    CustomEditText etCompanynameEditlocation;
    @BindView(R.id.btn_Add_Address)
    Button btnAddAddress;
    Activity activity;
    ApiManager apiManager;
    AddressDetail addressDetail;
    SharedPref sharedPref;
    LoginDetails loginDetails;
    AddressDetail addressDetails;

    String addressID="";
    String block, compnyname;
    String addresstype = "";
    @BindView(R.id.et_note_editlocation)
    CustomEditText etNoteEditlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_location );
        ButterKnife.bind( this );
        tvHeaderText.setText( R.string.editaddress );
        fromScreen = getIntent( ).getExtras( ).getString( API_Params.FROM_SCREEN );
        isedit = getIntent( ).getExtras( ).getString( API_Params.IS_EDIT );
        addresstype = getIntent( ).getExtras( ).getString( API_Params.ADDRESS_TYPE );
        addressID=getIntent( ).getExtras( ).getString( API_Params.ADDRESS_ID );


        Toast.makeText( this,isedit,Toast.LENGTH_LONG ).show();

        activity = this;
        apiManager = new ApiManager( this, this );
        addressDetail = ( AddressDetail ) getIntent( ).getSerializableExtra( API_Params.RESULT );


        sharedPref = new SharedPref( this );
        String response = sharedPref.getLoginDetails( );
        final Gson gson = new Gson( );
        Type collectionType = new TypeToken <LoginDetails>( ) {
        }.getType( );
        loginDetails = gson.fromJson( response, collectionType );

        String data= getIntent( ).getExtras( ).getString( API_Params.DATA );
        etEditaddress.setText( data );

    }

    @OnClick(R.id.iv_login_back)
    public void onBack() {
        finish( );
    }

    @OnClick(R.id.tv_applyFilter)
    public void onViewClicked() {
        //Intent intent  = new Intent(  )
    }

    @OnClick(R.id.btn_Add_Address)
    public void onDone() {
        String block=etUnitEditlocation.getText().toString().trim();
        String companyname=etCompanynameEditlocation.getText().toString().trim();
        if (TextUtils.isEmpty( block )){
            CommonUtils.setEditTextError( etUnitEditlocation, getResources( ).getString( R.string.block ) );
        }else if (TextUtils.isEmpty( companyname )){
            CommonUtils.setEditTextError( etUnitEditlocation, getResources( ).getString( R.string.companyerror ) );
        }else {

            editaddress( );
        }



    }

    private void editaddress() {

        Call <EditLocationMaster> editLocationMasterCall = ApiHandler.getApiService( ).editLocationApi( getEditLocationApi( ) );
        apiManager.makeApiRequest( editLocationMasterCall, API_Codes.addAddress );

    }

    private JsonObject getEditLocationApi() {
        JsonObject gsonObject = new JsonObject( );
        try {

            JSONObject jsonObject1 = new JSONObject( );
            GoogleAddress googleAddress = addressDetail.getGoogleAddress( );
            Log.e("tag", googleAddress.toString());
            jsonObject1.put( API_Params.userId, loginDetails.getUserId( ) );
            jsonObject1.put( API_Params.fullAddress, googleAddress.getFullAddress( ) );
            jsonObject1.put( API_Params.notes,etNoteEditlocation.getText().toString().trim() );
            jsonObject1.put( API_Params.lat, addressDetail.getLat( ) );
            jsonObject1.put( API_Params.Long, addressDetail.getLong( ) );
            jsonObject1.put( API_Params.isEdit, isedit );
            jsonObject1.put( API_Params.addressId, addressID );
            jsonObject1.put( API_Params.block, etUnitEditlocation.getText( ).toString( ));
            jsonObject1.put( API_Params.companyName, etCompanynameEditlocation.getText( ).toString( ));
            jsonObject1.put( API_Params.addressType, addresstype );

            JSONObject jsonObject = new JSONObject( googleAddress.toString() );

            JSONObject subObj1 = new JSONObject();
            JSONObject subObj2 = new JSONObject();
            JSONObject subObj3 = new JSONObject();
            JSONObject subObj4 = new JSONObject();

            subObj1.put("type","locality");
            subObj1.put("name",googleAddress.getAcLocality());

            subObj2.put("type","sublocality");
            subObj2.put("name",googleAddress.getAcSubLocality());

            subObj3.put("type","administrativeArea");
            subObj3.put("name",googleAddress.getAcAdminArea());

            subObj4.put("type","subadministrativeArea");
            subObj4.put("name",googleAddress.getAcSubAdminArea());

            JSONArray acArray=new JSONArray();

            acArray.put(subObj1);
            acArray.put(subObj2);
            acArray.put(subObj3);
            acArray.put(subObj4);

            jsonObject.put(API_Params.addressComponents,acArray);
            jsonObject1.put( API_Params.googleAddress, jsonObject);


            JsonParser jsonParser = new JsonParser( );
            gsonObject = ( JsonObject ) jsonParser.parse( jsonObject1.toString( ) );
            Log.e( "error", gsonObject.toString( ) );

        } catch (Exception e) {
            e.printStackTrace( );
        }
        return gsonObject;

    }

    @Override
    public void isError(String errorCode, int apiCode) {

    }

    @Override
    public void isSuccess(Object response, int apiCode) {
        if (apiCode == API_Codes.addAddress) {
            EditLocationMaster editLocationMaster = ( EditLocationMaster ) response;
            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( editLocationMaster ).toString( ) );

            String EditLocationMaster = new Gson( ).toJson( editLocationMaster.getEditLocationDetails( ) ).toString( );
            sharedPref.storeEditAddressDetailsList( EditLocationMaster );

            if (editLocationMaster.getSuccess( ) == 1) {
                Toast.makeText( getApplicationContext( ), "Address updated successfully.", Toast.LENGTH_SHORT ).show( );

            } else if (editLocationMaster.getSuccess( ) == 0) {
                Toast.makeText( getApplicationContext( ), "Invalid Address !", Toast.LENGTH_SHORT ).show( );
            }
        }

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
