package com.a2z.deliver.activities.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.activities.home.HomeActivity;
import com.a2z.deliver.databinding.ActivityLoginBinding;
import com.a2z.deliver.models.forgotPassword.ForgotPasswordDetails;
import com.a2z.deliver.models.login.LoginMaster;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class LoginActivity extends BaseApp implements View.OnClickListener, LoginView {
    ActivityLoginBinding binding;
    LoginPresenter presenter;
    ProgressDialog progressDialog;
    SharedPref sharedPref;
    Activity activity;
    @Inject
    public Service service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps().injectLogin( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_login );
        binding.setClickListener( this );
        binding.loginHeader.setClickListener( this );
        binding.loginHeader.tvHeaderText.setText( R.string.login );

        init();
    }

    private void init() {
        activity = this;
        sharedPref = SharedPref.getInstance( this );
        presenter = new LoginPresenter( LoginActivity.this, service, this );

        /*binding.etLoginEmail.getBackground().mutate().setColorFilter( getResources().getColor( R.color.color_c1c1c1 ), PorterDuff.Mode.SRC_ATOP );
        binding.etLoginPassword.getBackground().mutate().setColorFilter( getResources().getColor( R.color.color_c1c1c1 ), PorterDuff.Mode.SRC_ATOP );*/

        binding.login.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService( INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0 );
                return false;
            }
        } );

    }

    /*--------------------------------------------------------------------------------------------*/
    @Override
    public void onClick(View v) {
        if (v == binding.btnLogin) {
            String email = binding.etLoginEmail.getText().toString().trim();
            String password = binding.etLoginPassword.getText().toString().trim();
            if (presenter.isValidate( email, password, binding.etLoginEmail, binding.etLoginPassword )) {
                presenter.getLoginApi( loginParams() );
            }
        } else if (v == binding.loginHeader.ivLoginBack) {
            finish();
        } else if (v == binding.tvForgotPassword) {
            presenter.forgotPassword();
        }
    }

    private JsonObject loginParams() {
        JsonObject gsonObject = new JsonObject();
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put( API_Params.firstName, "" );
            jsonObject.put( API_Params.lastName, "" );
            jsonObject.put( API_Params.email, binding.etLoginEmail.getText().toString().trim() );
            jsonObject.put( API_Params.password, binding.etLoginPassword.getText().toString().trim() );
            jsonObject.put( API_Params.isSocial, "0" );
            jsonObject.put( API_Params.isFacebook, "0" );
            jsonObject.put( API_Params.deviceAccess, API_Params.DEVICE_ACCESS );
            jsonObject.put( API_Params.deviceToken, "fgdhfhfhdf" );
            jsonObject.put( API_Params.fbId, "" );
            jsonObject.put( API_Params.gmailId, "" );
            jsonObject.put( API_Params.fbImageUrl, "" );
            jsonObject.put( API_Params.gmailImageUrl, "" );


            JsonParser parser = new JsonParser();
            gsonObject = (JsonObject) parser.parse( jsonObject.toString() );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gsonObject;
    }

    @Override
    public void showWait() {

        CommonUtils.setProgressDialog( activity,getResources().getString( R.string.app_name ),getResources().getString( R.string.signin_msg ) );
    }

    @Override
    public void removeWait() {
        CommonUtils.hideProgressDialog();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        CommonUtils.makeToast( getApplicationContext(), appErrorMessage );
    }

    @Override
    public void getLoginSuccess(LoginMaster loginMaster) {
        if (loginMaster != null) {
            Log.e( "JSON RESPONSE: ", new Gson().toJson( loginMaster.getLoginDetails() ).toString() );
            if (loginMaster.getSuccess() == 1) {
                String login_response = new Gson().toJson( loginMaster.getLoginDetails() ).toString();
                sharedPref.storeLoginDetails( login_response );
                Toast.makeText( getApplicationContext(), loginMaster.getMessage(), Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent( LoginActivity.this, HomeActivity.class );
                startActivity( intent );
            } else if (loginMaster.getSuccess() == 0) {

            }
        }
    }

    @Override
    public void getForgetPasswordSuccess(ForgotPasswordDetails forgotPasswordDetails) {
        if (forgotPasswordDetails != null) {
            Log.e( "JSON RESPONSE: ", new Gson().toJson( forgotPasswordDetails ).toString() );
            if (forgotPasswordDetails.getSuccess() == 1) {

            } else if (forgotPasswordDetails.getSuccess() == 0) {

            }
        }
    }

    @Override
    public void getForgotPasswordParams(JsonObject params) {
        presenter.getForgotPasswordApi( params );
    }
}
