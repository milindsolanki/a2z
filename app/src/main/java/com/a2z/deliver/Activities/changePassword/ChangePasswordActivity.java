package com.a2z.deliver.activities.changePassword;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.a2z.deliver.activities.home.HomeActivity;
import com.a2z.deliver.BaseApp;
import com.a2z.deliver.databinding.ActivityChangePasswordBinding;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.models.MessageModel;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.views.CustomEditText;
import com.a2z.deliver.views.CustomTextview;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.networking.Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseApp implements ChangePasswordView, View.OnClickListener {


    ActivityChangePasswordBinding binding;

    SharedPref sharedPref;
    LoginDetails loginDetails;

    @Inject
    public Service service;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps( ).injectChangePassword( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_change_password );
        binding.setClickListener( this );
        ButterKnife.bind( this );
        binding.changepwdHeader.tvHeaderText.setText( R.string.changepassword );
        binding.changepwdHeader.setClickListener( this );

        init( );


    }

    private void init() {
        sharedPref = new SharedPref( this );
        String response = sharedPref.getLoginDetails( );
        final Gson gson = new Gson( );
        Type collectionType = new TypeToken <LoginDetails>( ) {
        }.getType( );
        loginDetails = gson.fromJson( response, collectionType );

    }


    private JsonObject changePasswordParams() {
        JsonObject gsonObject = new JsonObject( );
        try {

            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.userId, loginDetails.getUserId( ) );
            jsonObject.put( API_Params.email, loginDetails.getEmail( ) );
            jsonObject.put( API_Params.password,binding.etConfirmpwd.getText( ).toString( ).trim( ) );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (JSONException e) {
            e.printStackTrace( );
        }
        return gsonObject;
    }


    @Override
    public void showWait() {
        CommonUtils.setProgressDialog(this,getResources().getString( R.string.app_name ),getResources().getString(  R.string.changepassword) );
    }

    @Override
    public void removeWait() {
        CommonUtils.hideProgressDialog();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        CommonUtils.makeToast( getApplicationContext( ), appErrorMessage );
    }

    @Override
    public void getChangePasswordSuccess(MessageModel messageModelResponse) {
        if (messageModelResponse.getSuccess( ) == 1) {
            Toast.makeText( getApplicationContext( ), messageModelResponse.getMessage( ), Toast.LENGTH_SHORT ).show( );
            Intent intent = new Intent( ChangePasswordActivity.this, HomeActivity.class );
            startActivity( intent );
        } else if (messageModelResponse.getSuccess( ) == 0) {
            Toast.makeText( getApplicationContext( ), messageModelResponse.getMessage( ), Toast.LENGTH_SHORT ).show( );
        }
    }

    @Override
    public void onClick(View v) {
        if (v ==binding.btnSavePassword) {
            String newpassword =binding.etNewpwd.getText( ).toString( ).trim( );
            String confirmpassowrd =binding.etConfirmpwd.getText( ).toString( ).trim( );

            ChangePasswordPresenter presenter = new ChangePasswordPresenter( ChangePasswordActivity.this, service, this );
            if (presenter.isValidate( newpassword, confirmpassowrd,binding.etNewpwd,binding.etConfirmpwd ))
                presenter.getChangePasswordAPI( changePasswordParams( ) );
        }
    }
}
