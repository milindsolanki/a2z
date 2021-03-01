package com.a2z.deliver.activities.mainActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.a2z.deliver.R;
import com.a2z.deliver.models.login.LoginMaster;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.webService.API_Params;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MainPresenter {
    private final Service service;
    private final MainView view;
    private CompositeSubscription subscriptions;
    private Activity activity;
    private static final String TAG = MainPresenter.class.getSimpleName();

    public MainPresenter(Activity activity, Service service, MainView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
        this.activity = activity;
    }

    public void getLoginApi(JsonObject jsonObject) {
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

    public void onStop() {
        subscriptions.unsubscribe();
    }

    public void getKeyHash() {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(),
                    PackageManager.GET_SIGNATURES );
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance( "SHA" );
                md.update( signature.toByteArray() );
                Log.e( "KeyHash:", Base64.encodeToString( md.digest(), Base64.DEFAULT ) );
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }


    public void facebookLogin(CallbackManager callbackManager) {
        // TODO Auto-generated method stub

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(
                activity,
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
                                                view.onFacebookSuccess( params );
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

    public GoogleSignInClient getGoogleSignInClient() {
        //creating a GoogleSignInClient object
        GoogleSignInClient mGoogleSignInClient;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestIdToken( activity.getString( R.string.default_web_client_id ) )
                .requestEmail()
                .build();
        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient( activity, gso );
        return mGoogleSignInClient;
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Log.d( TAG, "firebaseAuthWithGoogle:" + acct.getId() );
        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential( acct.getIdToken(), null );

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential( credential )
                .addOnCompleteListener( activity, new OnCompleteListener<AuthResult>() {
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
                            view.onGoogleSuccess( params );
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w( TAG, "signInWithCredential:failure", task.getException() );
                            Toast.makeText( activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT ).show();

                        }

                        // ...
                    }
                } );
    }

    private JsonObject getLoginParams(String firstname, String lastname, String email, String isFacebook,
                                      String fbId, String gmailId, String fbImageUrl, String gmailImageUrl) {
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObject = new JSONObject();
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
            gsonObject = (JsonObject) parser.parse( jsonObject.toString() );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsonObject;
    }

    public void showExitDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder( activity );

        builder.setIcon( R.drawable.ic_exit );
        builder.setTitle( R.string.app_name );
        builder.setMessage( R.string.exit_msg );
        builder.setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.finishAffinity( activity );
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
