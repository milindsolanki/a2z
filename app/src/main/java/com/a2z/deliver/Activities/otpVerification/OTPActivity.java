package com.a2z.deliver.activities.otpVerification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.a2z.deliver.activities.home.HomeActivity;
import com.a2z.deliver.BaseApp;
import com.a2z.deliver.models.MessageModel;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.webService.API_Codes;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.webService.ApiManager;
import com.a2z.deliver.webService.ApiResponseInterface;
import com.a2z.deliver.databinding.ActivityOtpBinding;
import com.a2z.deliver.networking.Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import javax.inject.Inject;

public class OTPActivity extends BaseApp implements OtpView, View.OnClickListener {

    String otpId = "";
    Activity activity;
    ProgressDialog progressDialog;
    ActivityOtpBinding binding;
    OtpPresenter presenter;
    @Inject
    public Service service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps().injectOtp(this);
        binding = DataBindingUtil.setContentView( this, R.layout.activity_otp );
        binding.setClickListener( this );
        init();
    }

    private void init() {
        activity = this;
        presenter = new OtpPresenter( OTPActivity.this, service, this );
        progressDialog = new ProgressDialog( activity );
        progressDialog.setMessage( "Signing you in..." );
        progressDialog.setTitle( getResources( ).getString( R.string.app_name ) );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        progressDialog.setCancelable( false );
        Bundle bundle = getIntent( ).getExtras( );
        if (bundle != null) {
            otpId = bundle.getString( API_Params.id );
        }

        binding.otpHeader.tvHeaderText.setText( R.string.verify );
        binding.otpLinearlayout.setOnTouchListener( new View.OnTouchListener( ) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = ( InputMethodManager ) getSystemService( INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow( getCurrentFocus( ).getWindowToken( ), 0 );
                return false;
            }
        } );
    }

    private JsonObject getverifyOtpApiParams() {
        JsonObject gsonObject = new JsonObject( );
        try {
            String insertOtp=binding.etOtp.getText().toString().trim();

            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.id, otpId );
            Log.e( "TAG", "Otp" + otpId );
            jsonObject.put( API_Params.otp, insertOtp );
            Log.e( "TAG", "etotp" + binding.etOtp.toString( ) );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );

        } catch (Exception e) {
            e.printStackTrace( );
        }
        return gsonObject;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.otpHeader.ivLoginBack) {
            finish( );
        } else if (v == binding.btnOtp) {
            presenter.getOtpAPI( getverifyOtpApiParams( ) );
        }
    }

    @Override
    public void showWait() {
        progressDialog.show();
    }

    @Override
    public void removeWait() {
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        CommonUtils.makeToast( getApplicationContext( ), appErrorMessage );
    }

    @Override
    public void getOtpSuccess(MessageModel messageModel) {
        if (messageModel != null) {
            if (messageModel.getSuccess() == 1) {
                Log.e("TAG", "SUCCESS OTP");
                Log.e("JSON RESPONSE: OTP ", new Gson().toJson(messageModel).toString());
                Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OTPActivity.this, HomeActivity.class);
                startActivity(intent);
            } else if (messageModel.getSuccess() == 0) {
                Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
