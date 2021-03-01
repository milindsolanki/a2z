package com.a2z.deliver.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.a2z.deliver.Models.Login.LoginMaster;
import com.a2z.deliver.Models.SignUp.RegisterMaster;
import com.a2z.deliver.Models.OTP.SendOtpMaster;
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
import com.harmis.imagepicker.CameraUtils.CameraIntentHelper;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelperCallback;
import com.harmis.imagepicker.activities.CropImageActivity;
import com.harmis.imagepicker.activities.GalleryActivity;
import com.harmis.imagepicker.model.Images;
import com.harmis.imagepicker.utils.CommonKeyword;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class Signup_Email_Activity extends AppCompatActivity implements ApiResponseInterface {

    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    @BindView(R.id.et_firstname)
    CustomEditText etFirstname;
    @BindView(R.id.et_lastname)
    CustomEditText etLastname;
    @BindView(R.id.et_mobilenumber)
    CustomEditText etMobilenumber;
    @BindView(R.id.et_email)
    CustomEditText etEmail;
    @BindView(R.id.et_password)
    CustomEditText etPassword;
    @BindView(R.id.et_confirmpassword)
    CustomEditText etConfirmpassword;
    @BindView(R.id.iv_profilepic)
    ImageView ivProfilepic;
    @BindView(R.id.tv_image_picker_signup)
    CustomTextview tvImagePickerSignup;
    @BindView(R.id.image_picker_signup)
    FrameLayout imagePickerSignup;

    String[] cameraPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int REQUEST_PERMISSION_CAMERA = 1;
    String usertImage = "";

    SharedPref sharedPref;
    CameraIntentHelper mCameraIntentHelper;
    @BindView(R.id.progressbar_profile)
    ProgressBar progressbarProfile;
    ProgressDialog progressDialog;
    Activity activity;
    ApiManager apiManager;
    String otpId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup_email );
        ButterKnife.bind( this );
        activity = this;
        apiManager = new ApiManager( activity, this );
        tvHeaderText.setText( R.string.email );
        setupCameraIntentHelper( );
        sharedPref = new SharedPref( this );
        progressDialog = new ProgressDialog( activity );
        progressDialog.setMessage( "Signing you in..." );
        progressDialog.setTitle( getResources( ).getString( R.string.app_name ) );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        progressDialog.setCancelable( false );

    }

    @OnClick(R.id.iv_login_back)
    public void onBackClicked() {
        Intent intent = new Intent( Signup_Email_Activity.this, MainActivity.class );
        startActivity( intent );
    }

    @OnClick(R.id.btn_continue)
    public void onContinueClicked() {
        String fname = etFirstname.getText( ).toString( ).trim( );
        String lname = etLastname.getText( ).toString( ).trim( );
        String phone = etMobilenumber.getText( ).toString( ).trim( );
        String email = etEmail.getText( ).toString( ).trim( );
        String password = etPassword.getText( ).toString( ).trim( );
        String repassword = etConfirmpassword.getText( ).toString( ).trim( );

        if (CommonUtils.isEmpty( fname )) {
            CommonUtils.setEditTextError( etFirstname, getResources( ).getString( R.string.enterfname ) );
        } else if (CommonUtils.isEmpty( lname )) {
            CommonUtils.setEditTextError( etLastname, getResources( ).getString( R.string.enterlname ) );
        } else if (CommonUtils.isEmpty( phone )) {
            CommonUtils.setEditTextError( etMobilenumber, getResources( ).getString( R.string.entermobno ) );
        } else if (phone.length( ) < 10) {
            CommonUtils.setEditTextError( etMobilenumber, getResources( ).getString( R.string.validtendigit ) );
        } else if (CommonUtils.isEmpty( email )) {
            CommonUtils.setEditTextError( etEmail, getResources( ).getString( R.string.enteremailid ) );
        } else if (!CommonUtils.checkEmail( email )) {
            CommonUtils.setEditTextError( etEmail, getResources( ).getString( R.string.validemail ) );
        } else if (CommonUtils.isEmpty( password )) {
            CommonUtils.setEditTextError( etPassword, getResources( ).getString( R.string.enterpassword ) );
        } else if (CommonUtils.isEmpty( repassword )) {
            CommonUtils.setEditTextError( etConfirmpassword, getResources( ).getString( R.string.entercpassword ) );
        } else if (password.length( ) < 8) {
            CommonUtils.setEditTextError( etPassword, getResources( ).getString( R.string.minpassword ) );
        } else if (repassword.length( ) < 8) {
            CommonUtils.setEditTextError( etConfirmpassword, getResources( ).getString( R.string.minpassword ) );
        } else if (!password.equals( repassword )) {
            CommonUtils.makeToast( Signup_Email_Activity.this, getResources( ).getString( R.string.validcpassword ) );
        } else if (CommonUtils.isEmpty( usertImage )) {
            CommonUtils.makeToast( Signup_Email_Activity.this, "Please Select Image" );
        } else {
            sendOtpApi( );
        }
    }

    private void sendOtpApi() {
        progressDialog.show( );
        Call <SendOtpMaster> sandOtpMasterCall = ApiHandler.getApiService( ).sendOtpApi( getSendOtpParams( ) );
        apiManager.makeApiRequest( sandOtpMasterCall, API_Codes.sendotp );
        Log.e( "Tag", "sendOtp" );
    }

    private JsonObject getSendOtpParams() {
        JsonObject gsonObject = new JsonObject( );
        try {

            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.email, etEmail.getText( ).toString( ).trim( ) );
            jsonObject.put( API_Params.isRegistration, "1" );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (JSONException e) {
            e.printStackTrace( );
        }
        return gsonObject;

    }

    private void registerApiCall() {
        progressDialog.show( );
        MultipartBody.Part body = ApiManager.getMultipartImageBody( usertImage, API_Params.profileImage );
        RequestBody deviceAccess = RequestBody.create( MediaType.parse( "multipart/form-data" ), API_Params.DEVICE_ACCESS );
        RequestBody deviceToken = RequestBody.create( MediaType.parse( "multipart/form-data" ), "0" );
        RequestBody firstName = RequestBody.create( MediaType.parse( "multipart/form-data" ), etFirstname.getText( ).toString( ).trim( ) );
        RequestBody lastName = RequestBody.create( MediaType.parse( "multipart/form-data" ), etLastname.getText( ).toString( ).trim( ) );
        RequestBody email = RequestBody.create( MediaType.parse( "multipart/form-data" ), etEmail.getText( ).toString( ).trim( ) );
        RequestBody mobileNumber = RequestBody.create( MediaType.parse( "multipart/form-data" ), etMobilenumber.getText( ).toString( ).trim( ) );
        RequestBody password = RequestBody.create( MediaType.parse( "multipart/form-data" ), etPassword.getText( ).toString( ) );
        RequestBody isEdit = RequestBody.create( MediaType.parse( "multipart/form-data" ), "0" );
        RequestBody userId = RequestBody.create( MediaType.parse( "multipart/form-data" ), "0" );
        Call <LoginMaster> registerApiCall = ApiHandler.getApiService( ).registerApi( deviceAccess,
                deviceToken, firstName, lastName, email, mobileNumber, password, isEdit, userId, body );
        apiManager.makeApiRequest( registerApiCall, API_Codes.register );
    }


    @OnClick(R.id.image_picker_signup)
    public void onUploadClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                imagePicker( );
            } else {
                ActivityCompat.requestPermissions( Signup_Email_Activity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            imagePicker( );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults != null && grantResults.length > 0) {
                boolean isGranted = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        isGranted = false;
                        break;
                    }
                }
                if (isGranted) {
                    imagePicker( );
                }
            }
        }
    }

    private void setupCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper( this, new CameraIntentHelperCallback( ) {
            @Override
            public void onPhotoUriFound(Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                List <String> imagesList = new ArrayList <>( );
                imagesList.add( photoUri.getPath( ) );

                Intent intent = new Intent( new Intent( getApplicationContext( ), CropImageActivity.class ) );
                intent.putExtra( CommonKeyword.RESULT, ( Serializable ) imagesList );
                startActivityForResult( intent, CommonKeyword.REQUEST_CODE_CAMERA );
            }

            @Override
            public void deletePhotoWithUri(Uri photoUri) {

            }

            @Override
            public void onSdCardNotMounted() {

            }

            @Override
            public void onCanceled() {

            }

            @Override
            public void onCouldNotTakePhoto() {

            }

            @Override
            public void onPhotoUriNotFound() {

            }

            @Override
            public void logException(Exception e) {
                Log.e( "log_tag", "log Exception : " + e.getMessage( ) );
            }
        } );
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState( savedInstanceState );
        mCameraIntentHelper.onSaveInstanceState( savedInstanceState );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState( savedInstanceState );
        mCameraIntentHelper.onRestoreInstanceState( savedInstanceState );
    }

    private void captureImageCamera() {
        try {
            if (mCameraIntentHelper != null) {
                mCameraIntentHelper.startCameraIntent( );
            }
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCameraIntentHelper.onActivityResult( requestCode, resultCode, data );
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == CommonKeyword.RESULT_CODE_GALLERY) {
            if (requestCode == CommonKeyword.REQUEST_CODE_GALLERY) {
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );
                }
                CommonUtils.setImageUsingGlide( this, usertImage, ivProfilepic, progressbarProfile );
            }
        } else if (resultCode == CommonKeyword.RESULT_CODE_CROP_IMAGE) {
            if (requestCode == CommonKeyword.REQUEST_CODE_CAMERA) {
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );
                }
                CommonUtils.setImageUsingGlide( this, usertImage, ivProfilepic, progressbarProfile );
            }
        }
    }

    private void imagePicker() {
        final Dialog dialog = new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setCancelable( true );
        dialog.setContentView( R.layout.dialog_image_picker );
        dialog.getWindow( ).setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        dialog.getWindow( ).setGravity( Gravity.CENTER );
        dialog.show( );

        ImageButton btnCamera = ( ImageButton ) dialog.findViewById( R.id.btn_camera_picker );
        ImageButton btnGallery = ( ImageButton ) dialog.findViewById( R.id.btn_gallery_picker );

        btnCamera.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                dialog.dismiss( );
                captureImageCamera( );
            }
        } );

        btnGallery.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                dialog.dismiss( );
                Intent intent = new Intent( Signup_Email_Activity.this, GalleryActivity.class );
                intent.putExtra( CommonKeyword.MAX_IMAGES, 1 );
                startActivityForResult( intent, CommonKeyword.REQUEST_CODE_GALLERY );


            }
        } );
    }


    @Override
    public void isError(String errorCode, int apiCode) {
        progressDialog.dismiss( );
        if (apiCode == API_Codes.register) {
            CommonUtils.makeLongToast( activity, errorCode );
        }

    }

    @Override
    public void isSuccess(Object response, int apiCode) {
        if (apiCode == API_Codes.sendotp) {
            progressDialog.dismiss( );
            SendOtpMaster sendOtpMaster = ( SendOtpMaster ) response;
            if (sendOtpMaster != null) {
                if (sendOtpMaster.getSuccess( ) == 1) {
                    otpId = sendOtpMaster.getSendOtpDetails( ).getId( );
                    Log.e( "fill", "required" );
                    registerApiCall( );
                } else {
                    CommonUtils.makeLongToast( activity, sendOtpMaster.getMessage( ) + "" );
                }
            }
        } else if (apiCode == API_Codes.register) {
            progressDialog.dismiss( );
            LoginMaster registerMaster = ( LoginMaster ) response;
            Log.e( "abc", "success" + registerMaster );
            if (registerMaster != null) {
                Log.e( "fill", "required" );
                CommonUtils.makeLongToast( activity, registerMaster.getMessage( ) + "" );
                if (registerMaster.getSuccess( ) == 1) {
                    Log.e( "JSON RESPONSE: ", new Gson( ).toJson( registerMaster ).toString( ) );

                    String register_response = new Gson( ).toJson( registerMaster.getLoginDetails( ) ).toString( );
                    sharedPref.storeRegisterDetails( register_response );


                    Log.e( "abc", "success" );
                    Intent intent = new Intent( Signup_Email_Activity.this, OTP_Activity.class );
                    intent.putExtra( API_Params.id, otpId );
                    startActivity( intent );
                }
            }
        }
    }
}
