package com.a2z.deliver.activities.Profile;

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

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.activities.drivingLicence.DrivingLicenceActivity;
import com.a2z.deliver.activities.idProof.IdProofActivity;
import com.a2z.deliver.activities.insurancePolicy.InsurancePolicyActivity;
import com.a2z.deliver.activities.registrationsCertificate.RegistrationsCertificateActivity;
import com.a2z.deliver.databinding.ActivityProfileBinding;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.models.login.LoginMaster;
import com.a2z.deliver.R;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.webService.ApiManager;
import com.google.gson.Gson;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelper;
import com.harmis.imagepicker.model.Images;
import com.harmis.imagepicker.utils.CommonKeyword;

import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends BaseApp implements ProfileView, View.OnClickListener {

    String usertImage = null;
    String[] cameraPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int REQUEST_PERMISSION_CAMERA = 1;
    CameraIntentHelper mCameraIntentHelper;
    String fromScreen = "";
    public static final int RESULT_IMAGE = 501;
    public static final int REQUEST_IMAGE = 401;

    Activity activity;
    SharedPref sharedPref;
    LoginDetails loginDetails;
    ProgressDialog progressDialog;
    ActivityProfileBinding binding;
    @Inject
    public Service service;
    ProfilePresenter profilePresenter;
    int whichImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps( ).injectProfile( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_profile );
        binding.setClickListener( this );
        binding.profileHeader.setClickListener( this );
        init( );
    }

    private void init() {
        activity = this;
        profilePresenter = new ProfilePresenter( activity, service, this );
        binding.profileHeader.tvHeaderText.setText( R.string.profile );
        //camera
        mCameraIntentHelper = profilePresenter.getmCameraIntentHelper( );
        sharedPref = SharedPref.getInstance( activity );
        loginDetails = sharedPref.getLoginDetailsModel( );
        String usertImage1 = loginDetails.getUserImage( );
        binding.tvProfileUpdateEmailid.setText( loginDetails.getEmail( ) );
        binding.etProfileUpdateFastname.setText( loginDetails.getFirstName( ) );
        binding.etProfileUpdateLastname.setText( loginDetails.getLastName( ) );
        binding.etProfileUpdateMnumber.setText( loginDetails.getMobileNumber( ) );
        CommonUtils.setImageUsingGlide( activity, usertImage1, binding.ivProfileUpdateImage, null );


    }

    public void updateProfile() {
        MultipartBody.Part body = ApiManager.getMultipartImageBody( usertImage, API_Params.profileImage );
        RequestBody deviceAccess = RequestBody.create( MediaType.parse( "multipart/form-data" ), API_Params.DEVICE_ACCESS );
        RequestBody deviceToken = RequestBody.create( MediaType.parse( "multipart/form-data" ), loginDetails.getDeviceToken( ) );
        RequestBody firstName = RequestBody.create( MediaType.parse( "multipart/form-data" ), binding.etProfileUpdateFastname.getText( ).toString( ).trim( ) );
        RequestBody lastName = RequestBody.create( MediaType.parse( "multipart/form-data" ), binding.etProfileUpdateLastname.getText( ).toString( ).trim( ) );
        RequestBody email = RequestBody.create( MediaType.parse( "multipart/form-data" ), loginDetails.getEmail( ) );
        RequestBody mobileNumber = RequestBody.create( MediaType.parse( "multipart/form-data" ), binding.etProfileUpdateMnumber.getText( ).toString( ).trim( ) );
        RequestBody password = RequestBody.create( MediaType.parse( "multipart/form-data" ), "" );
        RequestBody isEdit = RequestBody.create( MediaType.parse( "multipart/form-data" ), "1" );
        RequestBody userId = RequestBody.create( MediaType.parse( "multipart/form-data" ), loginDetails.getUserId( ) );
        profilePresenter.getProfilepAPI( deviceAccess, deviceToken, firstName, lastName, email,
                mobileNumber, password, isEdit, userId, body );
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
                    profilePresenter.uploadImage( whichImage );
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
                CommonUtils.setImageUsingGlide( this, usertImage, binding.ivProfileUpdateImage, null );

            }
        } else if (resultCode == CommonKeyword.RESULT_CODE_CROP_IMAGE) {
            if (requestCode == CommonKeyword.REQUEST_CODE_CAMERA) {

                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );
                }
                CommonUtils.setImageUsingGlide( this, usertImage, binding.ivProfileUpdateImage, null );


            }
        }
    }


    /*-----------------------------------------------------------------------------------------------------------------------------------------*/
    @Override
    public void onClick(View v) {

        if (v == binding.profileHeader.ivLoginBack) {
            finish( );
        } else if (v == binding.tvProfileUpdateDrivinglvc) {

            Intent intent = new Intent( this, DrivingLicenceActivity.class );
            startActivity( intent );

        } else if (v == binding.tvProfileUpdateRegicrif) {
            Intent intent = new Intent( this, RegistrationsCertificateActivity.class );
            startActivity( intent );

        } else if (v == binding.tvProfileUpdateInsurancePolicy) {
            Intent intent = new Intent( this, InsurancePolicyActivity.class );
            startActivity( intent );


        } else if (v == binding.tvProfileUpdateIdproof) {

            Intent intent = new Intent( this, IdProofActivity.class );
            startActivity( intent );
        } else if (v == binding.flProfilePic) {
            uploadProfileImage( );
        } else if (v == binding.btnProfileUpdateSave) {
            String phone = binding.etProfileUpdateMnumber.getText( ).toString( ).trim( );
            if(profilePresenter.isvalidation( phone,binding.etProfileUpdateMnumber )){
            updateProfile( );}
        }

    }

    private void uploadProfileImage() {
        whichImage = API_Params.FIRST_IMAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                profilePresenter.uploadImage( whichImage );
            } else {
                ActivityCompat.requestPermissions( ProfileActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            profilePresenter.uploadImage( whichImage );
        }
    }

    @Override
    public void showWait() {
        CommonUtils.setProgressDialog( activity, getResources( ).getString( R.string.app_name ), getResources( ).getString( R.string.updateprofile ) );
    }

    @Override
    public void removeWait() {
        CommonUtils.hideProgressDialog( );
    }

    @Override
    public void onFailure(String appErrorMessage) {
        CommonUtils.makeToast( getApplicationContext( ), appErrorMessage );
    }

    @Override
    public void getUpdateSuccess(LoginMaster loginMaster) {
        if (loginMaster != null) {
            if (loginMaster.getSuccess( ) == 1) {
                Log.e( "JSON RESPONSE: ", new Gson( ).toJson( loginMaster ).toString( ) );

                String LoginMaster = new Gson( ).toJson( loginMaster.getLoginDetails( ) ).toString( );

                sharedPref.storeLoginDetails( LoginMaster );

                Intent intent = new Intent( );
                intent.putExtra( API_Params.RESULT, loginMaster.getLoginDetails( ) );
                setResult( RESULT_IMAGE, intent );
                finish( );
                CommonUtils.makeLongToast( activity, "Profile Updated Successfully." );

            } else {
                CommonUtils.makeLongToast( activity, loginMaster.getMessage( ) + "" );
            }
        }
    }

}
