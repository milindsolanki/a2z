package com.a2z.deliver.activities.mainActivity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.activities.home.HomeActivity;
import com.a2z.deliver.activities.login.LoginActivity;
import com.a2z.deliver.activities.signUp.SignupActivity;
import com.a2z.deliver.databinding.ActivityMainBinding;
import com.a2z.deliver.models.login.LoginMaster;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import javax.inject.Inject;

public class MainActivity extends BaseApp implements MainView, View.OnClickListener {

    ActivityMainBinding binding;
    MainPresenter presenter;
    @Inject
    public Service service;

    //Facebook
    private CallbackManager callbackManager;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 234;
    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;

    Activity activity;
    SharedPref sharedPref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps().injectMain( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        binding.setClickListener( this );
        init();
    }

    private void init() {
        activity = this;
        presenter = new MainPresenter( MainActivity.this, service, this );
        FacebookSdk.sdkInitialize( this );
        sharedPref = SharedPref.getInstance( activity );
        presenter.getKeyHash();
        mGoogleSignInClient = presenter.getGoogleSignInClient();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {
            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent( data );
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult( ApiException.class );
                //authenticating with firebase
                presenter.firebaseAuthWithGoogle( account );
            } catch (ApiException e) {
                Toast.makeText( MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } else {
            callbackManager.onActivityResult( requestCode, resultCode, data );
        }
    }

    public void onBackPressed() {
        presenter.showExitDialog();
    }

    /*--------------------------------------------------------------------------------------------*/
    @Override
    public void onClick(View v) {
        if (v == binding.btnLoginnow) {
            Intent intent = new Intent( MainActivity.this, LoginActivity.class );
            startActivity( intent );
        } else if (v == binding.btnSignup) {
            Intent intent = new Intent( MainActivity.this, SignupActivity.class );
            startActivity( intent );

        } else if (v == binding.tvSkip) {
            Intent intent = new Intent( MainActivity.this, HomeActivity.class );
            startActivity( intent );
        } else if (v == binding.btnFacebooksignup) {
            callbackManager = CallbackManager.Factory.create();
            presenter.facebookLogin( callbackManager );
        } else if (v == binding.btnGooglesignup) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult( signInIntent, RC_SIGN_IN );
        }

    }

    @Override
    public void showWait() {
        CommonUtils.setProgressDialog( this, "A2Z", "Signing you in..." );
    }

    @Override
    public void removeWait() {
        CommonUtils.hideProgressDialog();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Toast.makeText( getApplicationContext(),appErrorMessage, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onFacebookSuccess(JsonObject params) {
        presenter.getLoginApi( params );
    }

    @Override
    public void onGoogleSuccess(JsonObject params) {
        presenter.getLoginApi( params );
    }

    @Override
    public void getLoginSuccess(LoginMaster loginMaster) {
        if (loginMaster != null) {
            if (loginMaster.getSuccess() == 1) {
                String login_response = new Gson().toJson( loginMaster.getLoginDetails() ).toString();
                sharedPref.storeLoginDetails( login_response );
                Intent intent = new Intent( MainActivity.this, HomeActivity.class );
                startActivity( intent );
            } else if (loginMaster.getSuccess() == 0) {

            }
        } else {
            Log.e( TAG, "Response null" );
        }
    }
}
