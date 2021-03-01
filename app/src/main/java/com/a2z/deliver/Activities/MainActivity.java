package com.a2z.deliver.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.a2z.deliver.Models.Login.LoginMaster;
import com.a2z.deliver.R;
import com.a2z.deliver.Utils.CommonUtils;
import com.a2z.deliver.Utils.SharedPref;
import com.a2z.deliver.Views.CustomButton;
import com.a2z.deliver.Views.CustomTextview;
import com.a2z.deliver.WebService.API_Codes;
import com.a2z.deliver.WebService.API_Params;
import com.a2z.deliver.WebService.ApiHandler;
import com.a2z.deliver.WebService.ApiManager;
import com.a2z.deliver.WebService.ApiResponseInterface;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements ApiResponseInterface {

    @BindView(R.id.btn_loginnow)
    CustomButton btnLoginnow;
    @BindView(R.id.btn_googlesignup)
    CustomButton btnGooglesignup;
    @BindView(R.id.btn_facebooksignup)
    CustomButton btnFacebooksignup;
    @BindView(R.id.btn_signup)
    CustomButton btnSignup;
    @BindView(R.id.tv_skip)
    CustomTextview tvSkip;

    //Facebook
    private CallbackManager callbackManager;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 234;
    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;

    //And also a Firebase Auth object
    FirebaseAuth mAuth;
    ApiManager apiManager;
    Activity activity;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        FacebookSdk.sdkInitialize( this );
        ButterKnife.bind( this );

        if(getIntent().getBooleanExtra( "Exit", false )) {
            finish();
        }

        activity = this;
        sharedPref = new SharedPref(activity);
        apiManager = new ApiManager( activity, this );

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.a2z.deliver",
                    PackageManager.GET_SIGNATURES );
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance( "SHA" );
                md.update( signature.toByteArray() );
                Log.e( "KeyHash:", Base64.encodeToString( md.digest(), Base64.DEFAULT ) );
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility( uiOptions );
//            ActionBar actionBar = getActionBar();
//            actionBar.hide();
        }

        btnFacebooksignup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        } );
        googleLogin();
    }

    @OnClick(R.id.btn_loginnow)
    public void onLoginNowClicked() {

        Intent intent = new Intent( MainActivity.this, LoginActivity.class );
        startActivity( intent );
    }

    @OnClick(R.id.btn_signup)
    public void onViewClicked() {

        Intent intent = new Intent( MainActivity.this, Signup_Email_Activity.class );
        startActivity( intent );
    }


    @OnClick(R.id.tv_skip)
    public void onSkipClicked() {
        Intent intent = new Intent( MainActivity.this, HomeActivity.class );
        startActivity( intent );

    }

    private void facebookLogin() {
        // TODO Auto-generated method stub

        callbackManager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(
                MainActivity.this,
                Arrays.asList( "email", "public_profile" ) );

        LoginManager.getInstance().registerCallback( callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Log.e( "facebook", "Facebook Request Success" );
                        Log.e( TAG, loginResult.getAccessToken().getToken() + "" );
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject json, GraphResponse response) {

                                        if (response.getError() != null) {
                                            Log.e( TAG, response.getError().toString() );
                                        } else {
                                            Log.e( TAG, "Graph api success" );
                                            try {
                                                String jsonresult = String.valueOf( json );
                                                System.out.println( "JSON Result" + jsonresult );

                                                String str_id = json.getString( "id" );
                                                String str_firstname = json.getString( "first_name" );
                                                String str_lastname = json.getString( "last_name" );
                                                String photo = "https://graph.facebook.com/" + str_id + "/picture?type=large";
                                                String str_email = "";
                                                try {
                                                    str_email = json.getString( "email" );
                                                } catch (JSONException e) {
                                                    Log.e( "tag", "email not found" );
                                                    str_email = str_firstname + str_lastname + "@facebook.com";
                                                    e.printStackTrace();
                                                } finally {
                                                    Log.e( "tag", "------------------FB----------------------------" );
                                                    Log.e( "tag", "idFacebook=" + str_id );
                                                    Log.e( "tag", "first_name=" + str_firstname );
                                                    Log.e( "tag", "last_name=" + str_lastname );
                                                    Log.e( "tag", "email=" + str_email );
                                                    Log.e( "tag", "----------------------------------------------" );
                                                }
                                                JsonObject params = getLoginParams( str_firstname, str_lastname, str_email, "1",
                                                        str_id, "", photo, "" );
                                                LoginApiCall( params );
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } );
                        Bundle parameters = new Bundle();
                        parameters.putString( "fields", "id,name,link,email,first_name,last_name,gender" );
                        request.setParameters( parameters );
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.e( "TAG_CANCEL", "On cancel" );

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e( "TAG_ERROR", error.toString() );

                    }
                } );

    }

    private void callLoginApi() {

    }

    private void googleLogin() {
        //first we intialized the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        //Then we need a GoogleSignInOptions object
        //And we need to build it as below
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestIdToken( getString( R.string.default_web_client_id ) )
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient( this, gso );
        btnGooglesignup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the google signin intent
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult( signInIntent, RC_SIGN_IN );
            }
        } );
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
                firebaseAuthWithGoogle( account );
            } catch (ApiException e) {
                Toast.makeText( MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } else {
            callbackManager.onActivityResult( requestCode, resultCode, data );
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d( TAG, "firebaseAuthWithGoogle:" + acct.getId() );
        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential( acct.getIdToken(), null );

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential( credential )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d( TAG, "signInWithCredential:success" );
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e( TAG, "name : " + user.getDisplayName() );
                            Log.e( TAG, "Email : " + user.getEmail() );
                            Log.e( TAG, "Image : " + user.getPhotoUrl() );
                            //Toast.makeText(LoginActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            JsonObject params = getLoginParams( user.getDisplayName(), "", user.getEmail(), "0",
                                    "", user.getProviderId(), "", user.getPhotoUrl().toString() );
                            LoginApiCall( params );
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w( TAG, "signInWithCredential:failure", task.getException() );
                            Toast.makeText( MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT ).show();

                        }

                        // ...
                    }
                } );
    }

    // Login api call
    private void LoginApiCall(JsonObject params) {
        CommonUtils.setProgressDialog( this, "A2Z","Signing you in..." );
        Call<LoginMaster> loginApiCall = ApiHandler.getApiService( ).loginApi( params );
        apiManager.makeApiRequest( loginApiCall, API_Codes.login );
    }

    private JsonObject getLoginParams(String firstname, String lastname, String email, String isFacebook,
                                      String fbId, String gmailId, String fbImageUrl, String gmailImageUrl) {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.firstName, firstname );
            jsonObject.put( API_Params.lastName, lastname );
            jsonObject.put( API_Params.email, email );
            jsonObject.put( API_Params.password, "" );
            jsonObject.put( API_Params.isSocial, "1" );
            jsonObject.put( API_Params.isFacebook, isFacebook );
            jsonObject.put( API_Params.deviceAccess, API_Params.DEVICE_ACCESS );
            jsonObject.put( API_Params.deviceToken, "fgdhfhfhdf" );
            jsonObject.put( API_Params.fbId, fbId );
            jsonObject.put( API_Params.gmailId, gmailId );
            jsonObject.put( API_Params.fbImageUrl, fbImageUrl );
            jsonObject.put( API_Params.gmailImageUrl, gmailImageUrl );

            JsonParser parser = new JsonParser();
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString() );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsonObject;
    }

    @Override
    public void isError(String errorCode, int apiCode) {
        CommonUtils.hideProgressDialog();
        CommonUtils.makeToast( getApplicationContext(),"Network Error!!" );
    }

    @Override
    public void isSuccess(Object response, int apiCode) {
        if (apiCode == API_Codes.login){
            CommonUtils.hideProgressDialog();
            LoginMaster loginMaster = ( LoginMaster ) response;
            if (loginMaster != null) {
                if (loginMaster.getSuccess() == 1) {
                    Toast.makeText(getApplicationContext(), loginMaster.getMessage(), Toast.LENGTH_SHORT).show();
                    String login_response = new Gson().toJson(loginMaster.getLoginDetails()).toString();
                    sharedPref.storeLoginDetails(login_response);
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if (loginMaster.getSuccess() == 0) {
                    Toast.makeText(getApplicationContext(), loginMaster.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Response null");
            }
        }
    }
}
