package com.a2z.deliver.activities.signUp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.a2z.deliver.activities.otpVerification.OTPActivity;
import com.a2z.deliver.BaseApp;
import com.a2z.deliver.models.login.LoginMaster;
import com.a2z.deliver.models.otp.SendOtpMaster;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.webService.ApiManager;
import com.a2z.deliver.databinding.ActivitySignupEmailBinding;
import com.a2z.deliver.networking.Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelper;
import com.harmis.imagepicker.model.Images;
import com.harmis.imagepicker.utils.CommonKeyword;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignupActivity extends BaseApp implements SignupView, View.OnClickListener {
    ActivitySignupEmailBinding binding;
    SignupPresenter presenter;
    String[] cameraPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int REQUEST_PERMISSION_CAMERA = 1;
    String usertImage = null;
    CameraIntentHelper mCameraIntentHelper;
    SharedPref sharedPref;
    ProgressDialog progressDialog;
    Activity activity;
    String otpId = "";
    int whichImage;

    @Inject
    public Service service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps( ).injectSignup( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_signup_email );
        binding.setClickListener( this );
        binding.signupHeader.setClickListener(this);
        init();
    }

    private void init() {
        activity = this;
        presenter = new SignupPresenter( SignupActivity.this, service, this );
        binding.signupHeader.tvHeaderText.setText( R.string.email );
        mCameraIntentHelper = presenter.getCameraIntentHelper();
        sharedPref = SharedPref.getInstance( this );
        progressDialog = new ProgressDialog( activity );
        progressDialog.setMessage( "Signing you in..." );
        progressDialog.setTitle( getResources( ).getString( R.string.app_name ) );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        progressDialog.setCancelable( false );
    }

    private JsonObject getSendOtpParams() {
        JsonObject gsonObject = new JsonObject( );
        try {
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.email, binding.etEmail.getText( ).toString( ).trim( ) );
            jsonObject.put( API_Params.isRegistration, "1" );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (JSONException e) {
            e.printStackTrace( );
        }
        return gsonObject;

    }

    private void getSignUpParams() {
        MultipartBody.Part body = ApiManager.getMultipartImageBody( usertImage, API_Params.profileImage );
        RequestBody deviceAccess = RequestBody.create( MediaType.parse( "multipart/form-data" ), API_Params.DEVICE_ACCESS );
        RequestBody deviceToken = RequestBody.create( MediaType.parse( "multipart/form-data" ), "0" );
        RequestBody firstName = RequestBody.create( MediaType.parse( "multipart/form-data" ), binding.etFirstname.getText( ).toString( ).trim( ) );
        RequestBody lastName = RequestBody.create( MediaType.parse( "multipart/form-data" ), binding.etLastname.getText( ).toString( ).trim( ) );
        RequestBody email = RequestBody.create( MediaType.parse( "multipart/form-data" ), binding.etEmail.getText( ).toString( ).trim( ) );
        RequestBody mobileNumber = RequestBody.create( MediaType.parse( "multipart/form-data" ), binding.etMobilenumber.getText( ).toString( ).trim( ) );
        RequestBody password = RequestBody.create( MediaType.parse( "multipart/form-data" ), binding.etPassword.getText( ).toString( ) );
        RequestBody isEdit = RequestBody.create( MediaType.parse( "multipart/form-data" ), "0" );
        RequestBody userId = RequestBody.create( MediaType.parse( "multipart/form-data" ), "0" );

        presenter.getSignAPI(deviceAccess,deviceToken,firstName,lastName,email,mobileNumber,password,isEdit,userId,body);
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
                    presenter.imagePicker(whichImage);
                }
            }
        }
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
                CommonUtils.setImageUsingGlide( this, usertImage, binding.ivProfilepic, binding.progressbarProfile );
            }
        } else if (resultCode == CommonKeyword.RESULT_CODE_CROP_IMAGE) {
            if (requestCode == CommonKeyword.REQUEST_CODE_CAMERA) {
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );
                }
                CommonUtils.setImageUsingGlide( this, usertImage, binding.ivProfilepic, binding.progressbarProfile );
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.signupHeader.ivLoginBack) {
            finish( );
        } else if (v == binding.btnContinue) {

            String fname = binding.etFirstname.getText( ).toString( ).trim( );
            String lname = binding.etLastname.getText( ).toString( ).trim( );
            String phone = binding.etMobilenumber.getText( ).toString( ).trim( );
            String email = binding.etEmail.getText( ).toString( ).trim( );
            String password = binding.etPassword.getText( ).toString( ).trim( );
            String repassword = binding.etConfirmpassword.getText( ).toString( ).trim( );

            if (presenter.isvalidation( fname, lname, phone, email, password, repassword,usertImage, binding.etFirstname,
                    binding.etLastname, binding.etMobilenumber, binding.etEmail, binding.etPassword, binding.etConfirmpassword )) {
                presenter.getSendOtpAPI( getSendOtpParams( ) );
            }

        } else if (v == binding.imagePickerSignup) {
            whichImage = API_Params.FIRST_IMAGE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                    presenter.imagePicker(whichImage);
                } else {
                    ActivityCompat.requestPermissions( SignupActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                presenter.imagePicker(whichImage);
            }

        }

    }

    @Override
    public void showWait() {
        progressDialog.show( );
    }

    @Override
    public void removeWait() {
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        //CommonUtils.makeToast( getApplicationContext( ), appErrorMessage );
        Toast.makeText( this,"hi",Toast.LENGTH_LONG ).show();
    }

    @Override
    public void getSignUpSuccess(LoginMaster loginMaster) {
        if (loginMaster != null) {
            if (loginMaster.getSuccess() == 1) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), loginMaster.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("JSON RESPONSE: ", new Gson().toJson(loginMaster).toString());

                String register_response = new Gson().toJson(loginMaster.getLoginDetails()).toString();
                sharedPref.storeRegisterDetails(register_response);
                Intent intent = new Intent(SignupActivity.this, OTPActivity.class);
                intent.putExtra(API_Params.id, otpId);
                startActivity(intent);
            } else if (loginMaster.getSuccess() == 0) {
                Toast.makeText(getApplicationContext(), loginMaster.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void getSendOtpSuccess(SendOtpMaster sendOtpMaster) {
        if (sendOtpMaster.getSuccess( ) == 1) {
            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( sendOtpMaster ).toString( ) );

            Toast.makeText( getApplicationContext( ), sendOtpMaster.getMessage( ), Toast.LENGTH_SHORT ).show( );
            otpId = sendOtpMaster.getSendOtpDetails( ).getId( );
            Log.e( "fill", "required" );
            getSignUpParams( );
        } else if (sendOtpMaster.getSuccess( ) == 0) {
            Toast.makeText( getApplicationContext( ), sendOtpMaster.getMessage( ), Toast.LENGTH_SHORT ).show( );
        }
    }
}
