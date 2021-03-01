package com.a2z.deliver.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.a2z.deliver.Models.ForgotPassword.ForgotPasswordDetails;
import com.a2z.deliver.Models.Login.LoginMaster;
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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements ApiResponseInterface {

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forgotPassword)
    CustomTextview tvForgotPassword;
    @BindView(R.id.et_login_email)
    CustomEditText etLoginEmail;
    @BindView(R.id.et_login_password)
    CustomEditText etLoginPassword;
    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.login)
    LinearLayout login;
    @BindView(R.id.touch)
    RelativeLayout touch;
    ProgressDialog progressDialog;
    SharedPref sharedPref;

    ApiManager apiManager;
    boolean isApiLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        ButterKnife.bind( this );

        sharedPref = new SharedPref( this );

        progressDialog = new ProgressDialog( LoginActivity.this );
        progressDialog.setMessage( "Signing you in..." );
        progressDialog.setTitle( "A2Z" );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        progressDialog.setCancelable( false );

        apiManager = new ApiManager( this, this );

        etLoginEmail.getBackground( ).mutate( ).setColorFilter( getResources( ).getColor( R.color.color_c1c1c1 ), PorterDuff.Mode.SRC_ATOP );
        etLoginPassword.getBackground( ).mutate( ).setColorFilter( getResources( ).getColor( R.color.color_c1c1c1 ), PorterDuff.Mode.SRC_ATOP );

        login.setOnTouchListener( new View.OnTouchListener( ) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = ( InputMethodManager ) getSystemService( INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow( getCurrentFocus( ).getWindowToken( ), 0 );
                return false;
            }
        } );

    }

    @OnClick(R.id.iv_login_back)
    public void onBackClicked() {
        Intent intent = new Intent( LoginActivity.this, MainActivity.class );
        startActivity( intent );
    }

    @OnClick(R.id.btn_login)
    public void onLoginClicked() {
        String email = etLoginEmail.getText( ).toString( ).trim( );
        String password = etLoginPassword.getText( ).toString( ).trim( );

        if (TextUtils.isEmpty( email )) {
            CommonUtils.setEditTextError( etLoginEmail, getResources( ).getString( R.string.toast_email ) );
        } else if (!CommonUtils.checkEmail( email )) {
            CommonUtils.setEditTextError( etLoginEmail, getResources( ).getString( R.string.validemail ) );
        } else if (TextUtils.isEmpty( password )) {
            CommonUtils.setEditTextError( etLoginPassword, getResources( ).getString( R.string.enterpassword ) );
        } else if (password.length( ) < 8) {
            CommonUtils.setEditTextError( etLoginPassword, getResources( ).getString( R.string.minpassword ) );
        } else {
            progressDialog.show( );
            Call <LoginMaster> loginApiCall = ApiHandler.getApiService( ).loginApi( getLoginParams( ) );
            apiManager.makeApiRequest( loginApiCall, API_Codes.login );
        }

    }

    private JsonObject getLoginParams() {
        JsonObject gsonObject = new JsonObject( );
        try {

            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.firstName, "" );
            jsonObject.put( API_Params.lastName, "" );
            jsonObject.put( API_Params.email, etLoginEmail.getText( ).toString( ).trim( ) );
            jsonObject.put( API_Params.password, etLoginPassword.getText( ).toString( ).trim( ) );
            jsonObject.put( API_Params.isSocial, "0" );
            jsonObject.put( API_Params.isFacebook, "0" );
            jsonObject.put( API_Params.deviceAccess, API_Params.DEVICE_ACCESS );
            jsonObject.put( API_Params.deviceToken, "fgdhfhfhdf" );
            jsonObject.put( API_Params.fbId, "" );
            jsonObject.put( API_Params.gmailId, "" );
            jsonObject.put( API_Params.fbImageUrl, "" );
            jsonObject.put( API_Params.gmailImageUrl, "" );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (JSONException e) {
            e.printStackTrace( );
        }
        return gsonObject;
    }

    @OnClick(R.id.tv_forgotPassword)
    public void onForgotClicked() {
        forgotPassword( );
    }

    private void forgotPassword() {

        final Dialog dialog = new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setCancelable( true );
        dialog.setContentView( R.layout.dialog_forgotpwd );
        dialog.getWindow( ).setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        dialog.getWindow( ).setGravity( Gravity.CENTER );
        dialog.show( );

        final EditText forgotemail = dialog.findViewById( R.id.et_forget_mail );
        Button button_ok = dialog.findViewById( R.id.btn_cd_email_ok );
        Button button_cancel = dialog.findViewById( R.id.btn_cd_email_cancel );

        button_ok.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                String fmail = forgotemail.getText().toString().trim();

                if (TextUtils.isEmpty( fmail )){
                    CommonUtils.setEditTextError( etLoginEmail, getResources( ).getString( R.string.toast_email ) );
                } else if (!CommonUtils.checkEmail( fmail )) {
                    CommonUtils.setEditTextError( etLoginEmail, getResources().getString( R.string.validemail ) );
                } else {
                    Call <ForgotPasswordDetails> forgotPasswordDetailsCallApiCall = ApiHandler.getApiService( ).forgotPasswordDetails( getForgotpwdParams( fmail) );
                    apiManager.makeApiRequest( forgotPasswordDetailsCallApiCall, API_Codes.forgotpassword );
                }

                dialog.dismiss( );
            }
        } );
        button_cancel.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Toast.makeText( getApplicationContext( ), "Cancel", Toast.LENGTH_LONG ).show( );
                dialog.dismiss( );
            }
        } );
    }

    private JsonObject getForgotpwdParams(String email){
        JsonObject gsonObject = new JsonObject( );
        try {
            JSONObject jsonObject = new JSONObject(  );
            jsonObject.put( API_Params.deviceAccess, API_Params.DEVICE_ACCESS );
            jsonObject.put( API_Params.email, email );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (Exception e) {
            e.printStackTrace();
        } return gsonObject;
    }

    @Override
    public void isError(String errorCode, int apiCode) {
        if (progressDialog != null && progressDialog.isShowing( ))
            progressDialog.dismiss( );
            CommonUtils.makeToast( getApplicationContext(),"Network Error!!" );
    }

    @Override
    public void isSuccess(Object response, int apiCode) {
        if (progressDialog != null && progressDialog.isShowing( ))
            progressDialog.dismiss( );
        isApiLoading = false;
        if (apiCode == API_Codes.login) {
            LoginMaster loginMaster = ( LoginMaster ) response;

            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( loginMaster ).toString( ) );

            String login_response = new Gson( ).toJson( loginMaster.getLoginDetails( ) ).toString( );
            sharedPref.storeLoginDetails( login_response );

            if (loginMaster.getSuccess( ) == 1) {
                Toast.makeText( getApplicationContext( ), "Welcome", Toast.LENGTH_SHORT ).show( );
                Intent intent = new Intent( LoginActivity.this, HomeActivity.class );
                startActivity( intent );
            } else if (loginMaster.getSuccess( ) == 0) {
                Toast.makeText( getApplicationContext( ), "Invalid Login Credentials!", Toast.LENGTH_SHORT ).show( );
            }
        } else if (apiCode == API_Codes.forgotpassword){
            ForgotPasswordDetails forgotPasswordDetails = (ForgotPasswordDetails) response;

            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( forgotPasswordDetails ).toString( ) );

            if (forgotPasswordDetails.getSuccess() == 1){
                CommonUtils.makeLongToast( getApplicationContext(), forgotPasswordDetails.getMessage( ) );
            } else if (forgotPasswordDetails.getSuccess() == 0){
                Toast.makeText( getApplicationContext( ), forgotPasswordDetails.getMessage(), Toast.LENGTH_SHORT ).show( );
            }
        }
    }
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder( this );

        builder.setIcon( R.drawable.ic_exit );
        builder.setTitle( R.string.app_name );
        builder.setMessage( R.string.exit_msg );
        builder.setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if (MainActivity.activity != null && SettingActivity.setting != null) {
//                    MainActivity.activity.finish();
//                    SettingActivity.setting.finish();
//                }
                Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                intent.putExtra( "Exit", true );
                startActivity( intent );
            }
        } );
        builder.setNegativeButton( R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        } );
        builder.show();
    }
}
