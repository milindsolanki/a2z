package com.a2z.deliver.activities.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.a2z.deliver.R;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.models.forgotPassword.ForgotPasswordDetails;
import com.a2z.deliver.models.login.LoginMaster;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.webService.API_Codes;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.webService.ApiHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import retrofit2.Call;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class LoginPresenter {
    private final Service service;
    private LoginView view;
    private CompositeSubscription subscriptions;
    private Activity activity;

    public LoginPresenter(Activity activity, Service service, LoginView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
        this.activity = activity;
    }

    public boolean isValidate(String email, String password, EditText etLoginEmail, EditText etLoginPassword){
        if (TextUtils.isEmpty( email )) {
            CommonUtils.setEditTextError( etLoginEmail, activity.getResources( ).getString( R.string.toast_email ) );
            return false;
        } else if (!CommonUtils.checkEmail( email )) {
            CommonUtils.setEditTextError( etLoginEmail, activity.getResources( ).getString( R.string.validemail ) );
            return false;
        } else if (TextUtils.isEmpty( password )) {
            CommonUtils.setEditTextError( etLoginPassword, activity.getResources( ).getString( R.string.enterpassword ) );
            return false;
        } else if (password.length( ) < 8) {
            CommonUtils.setEditTextError( etLoginPassword, activity.getResources( ).getString( R.string.minpassword ) );
            return false;
        } return true;
    }

    public void getLoginApi (JsonObject jsonObject){
        view.showWait();

        Subscription subscription = service.getLogin( jsonObject, new Service.GetLoginCallback() {
            @Override
            public void onSuccess(LoginMaster loginMaster) {
                view.removeWait();
                view.getLoginSuccess( loginMaster );
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure( networkError.getAppErrorMessage() );
            }
        } );
        subscriptions.add( subscription );
    }
    public void onStop(){
        subscriptions.unsubscribe();
    }

    /*-----------------------------------------------------------------------------------------------------*/
    public void getForgotPasswordApi (JsonObject jsonObject){
        Log.e( "JSON PARAMS =", new Gson( ).toJson( jsonObject ).toString( ) );
        view.showWait();

        Subscription subscription = service.getForgetPassword( jsonObject, new Service.GetForgetPasswordCallback() {
            @Override
            public void onSuccess(ForgotPasswordDetails forgotPasswordDetails) {
                view.removeWait();
                view.getForgetPasswordSuccess(forgotPasswordDetails);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure( networkError.getAppErrorMessage() );
            }
        });
        subscriptions.add( subscription );
    }

    public void forgotPassword() {

        final Dialog dialog = new Dialog( activity, R.style.PauseDialog);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setCancelable( true );
        dialog.setContentView(R.layout.dialog_forgotpwd);
        dialog.getWindow( ).setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        dialog.getWindow( ).setGravity( Gravity.CENTER );
        dialog.show();

        Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText etForgetMail = (EditText) dialog.findViewById(R.id.et_forget_mail);
        btnOk.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                String fmail = etForgetMail.getText().toString().trim();

                if (TextUtils.isEmpty( fmail )){
                    CommonUtils.setEditTextError( etForgetMail, activity.getResources( ).getString( R.string.toast_email ) );
                } else if (!CommonUtils.checkEmail( fmail )) {
                    CommonUtils.setEditTextError( etForgetMail, activity.getResources().getString( R.string.validemail ) );
                } else {
                    Toast.makeText( activity, "Success", Toast.LENGTH_SHORT ).show();
                    view.getForgotPasswordParams(getForgotpwdParams(fmail));
                }

                dialog.dismiss( );
            }
        } );
        btnCancel.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Toast.makeText( activity, "Cancel", Toast.LENGTH_LONG ).show( );
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
}
