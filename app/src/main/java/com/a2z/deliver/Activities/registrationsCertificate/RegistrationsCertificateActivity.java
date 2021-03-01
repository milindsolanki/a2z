package com.a2z.deliver.activities.registrationsCertificate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityRegistrationsCertificateBinding;
import com.a2z.deliver.models.GetImageDetails;
import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.drivingLicence.DrivingLicenceMaster;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.webService.ApiManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelper;
import com.harmis.imagepicker.model.Images;
import com.harmis.imagepicker.utils.CommonKeyword;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegistrationsCertificateActivity extends BaseApp implements View.OnClickListener, RegistrationCertificateView {

    ActivityRegistrationsCertificateBinding binding;
    @Inject
    public Service service;
    GetImageDetails getImageDetails;
    RegistrationCertificatePresenter presenter;

    String usertImage = null;
    String usertImage2 = null;
    String[] cameraPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int REQUEST_PERMISSION_CAMERA = 1;
    CameraIntentHelper mCameraIntentHelper;
    int whichImage;

    SharedPref sharedPref;
    LoginDetails loginDetails;
    String frontimage = "";
    String backimage = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps( ).injectRegistrationsCertificate( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_registrations_certificate );
        binding.setClickListener( this );
        binding.layoutHeader.setClickListener( this );
        binding.layoutHeader.tvHomeUpdate.setVisibility( View.GONE );
        init( );

    }

    private void init() {
        presenter = new RegistrationCertificatePresenter( this, service, this );
        binding.layoutHeader.tvHeaderText.setText( R.string.vehicleregistrationcertificate );
        binding.layoutHeader.tvHomeUpdate.setText( R.string.update );
        binding.layoutHeader.ivHomeFilter.setImageResource( R.drawable.ic_back_30 );
        mCameraIntentHelper = presenter.getCameraIntentHelper( );
        sharedPref = SharedPref.getInstance( this );
        loginDetails = sharedPref.getLoginDetailsModel( );
        binding.layoutHeader.tvHomeUpdate.setVisibility( View.GONE );

        getImageAPI( );
    }

    private void getImageAPI() {
        presenter.getImageApi( getImageParams( ) );
    }

    private JsonObject getImageParams() {
        JsonObject gsonObject = new JsonObject( );
        try {
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.userId, loginDetails.getUserId( ) );
            jsonObject.put( API_Params.flag, "2" );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );
        } catch (Exception e) {
            e.printStackTrace( );
        }
        return gsonObject;
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
                    presenter.UploadImage( whichImage );
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
        Log.e( "Tag", "insert" );
        if (resultCode == CommonKeyword.RESULT_CODE_GALLERY) {
            if (requestCode == CommonKeyword.REQUEST_CODE_GALLERY) {
                Log.e( "Tag", "insert resultCode requestCode  GALLERY 1" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );
                }

                if (usertImage != null) {
                    binding.layoutHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvFirstUpload.setText( R.string.edit );
                    binding.layoutHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( this, usertImage, binding.ivFirstUploadimage, null );
                }

            } else if (requestCode == CommonKeyword.SECOND_REQUEST_CODE_GALLERY) {
                Log.e( "Tag", "insert resultCode requestCode  GALLERY 2" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage2 = images.get( 0 ).getImageUrl( );
                }

                if (usertImage2 != null) {
                    binding.layoutHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvSecondUpload.setText( R.string.edit );
                    binding.layoutHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( this, usertImage2, binding.ivSecondUploadimage, null );
                }

            }
        } else if (resultCode == CommonKeyword.RESULT_CODE_CROP_IMAGE) {
            if (requestCode == CommonKeyword.REQUEST_CODE_CAMERA) {
                Log.e( "Tag", "insert resultCode requestCode  CAMERA 1" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );

                }

                if (usertImage != null) {
                    binding.layoutHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvFirstUpload.setText( R.string.edit );

                    CommonUtils.setImageUsingGlide( this, usertImage, binding.ivFirstUploadimage, null );
                }

            } else if (requestCode == CommonKeyword.SECOND_REQUEST_CODE_CAMERA) {
                Log.e( "Tag", "insert resultCode requestCode  CAMERA 2" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage2 = images.get( 0 ).getImageUrl( );

                }

                if (usertImage2 != null) {
                    binding.tvSecondUpload.setText( R.string.edit );
                    binding.layoutHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.layoutHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( this, usertImage2, binding.ivSecondUploadimage, null );
                }
            }
        }
    }

    @OnClick(R.id.iv_home_filter)
    public void onBack() {
        finish( );
    }

    @Override
    public void onClick(View v) {
        if (v == binding.layoutHeader.ivHomeFilter) {
            finish( );
        } else if (v == binding.tvFirstUpload) {
            FirstImageUpload( );

        } else if (v == binding.tvSecondUpload) {
            SecondImageUpload( );
        } else if (v == binding.layoutHeader.tvHomeUpdate) {

            imageUploadApi( );
        }
    }

    private void imageUploadApi() {
        RequestBody isUpdate = RequestBody.create( MediaType.parse( "multipart/form-data" ), "1" );
        RequestBody userId = RequestBody.create( MediaType.parse( "multipart/form-data" ), loginDetails.getUserId( ) );
        if (frontimage != null || backimage != null) {
            Log.e( "front and back", "not null" );

            RequestBody isUpdateImage = RequestBody.create( MediaType.parse( "multipart/form-data" ), "1" );
            MultipartBody.Part body1 = ApiManager.getMultipartImageBody( null, API_Params.imageFront );
            MultipartBody.Part body2 = ApiManager.getMultipartImageBody( usertImage2, API_Params.imageBack );
            presenter.getUploadRCImageAPI( isUpdate, userId, isUpdateImage, body1, body2 );


        } else {
            Log.e( "userimage", "null" );
            RequestBody isUpdateImage = RequestBody.create( MediaType.parse( "multipart/form-data" ), "0" );
            MultipartBody.Part body1 = ApiManager.getMultipartImageBody( null, API_Params.imageFront );
            MultipartBody.Part body2 = ApiManager.getMultipartImageBody( usertImage2, API_Params.imageBack );
            presenter.getUploadRCImageAPI( isUpdate, userId, isUpdateImage, body1, body2 );
        }
    }

    private void FirstImageUpload() {
        whichImage = API_Params.FIRST_IMAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                presenter.UploadImage( whichImage );
            } else {
                ActivityCompat.requestPermissions( RegistrationsCertificateActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            presenter.UploadImage( whichImage );
        }

    }

    private void SecondImageUpload() {
        whichImage = API_Params.SECOND_IMAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                presenter.UploadImage( whichImage );
            } else {
                ActivityCompat.requestPermissions( RegistrationsCertificateActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            presenter.UploadImage( whichImage );
        }
    }


    @Override
    public void showWait() {
        CommonUtils.setProgressDialog( this, getResources( ).getString( R.string.app_name ), getResources( ).getString( R.string.uploading ) );
    }

    @Override
    public void removeWait() {
        CommonUtils.hideProgressDialog( );
    }

    @Override
    public void onFailure(String appErrorMessage) {
        //CommonUtils.makeToast( getApplicationContext( ), appErrorMessage );
        Log.e( "onFailure", "0" );
    }

    @Override
    public void getUpdateDrivingLicence(DrivingLicenceMaster drivingLicenceMaster) {
        if (drivingLicenceMaster.getSuccess( ) == 1) {

            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( drivingLicenceMaster ).toString( ) );
            getImageAPI();
            binding.layoutHeader.tvHomeUpdate.setVisibility( View.GONE );
        } else if (drivingLicenceMaster.getSuccess( ) == 0) {
            Log.e( "getSuccess", "0" );

        }
    }

    @Override
    public void getUpdateDrivingLicenceImage(GetImageMaster getImageMaster) {
        if (getImageMaster != null) {

            if (getImageMaster.getSuccess( ) == 1) {
                Log.e( "getSuccess", "1" );
                Log.e( "JSON RESPONSE: ", new Gson( ).toJson( getImageMaster ).toString( ) );
                CommonUtils.setImageUsingGlide( ( Activity ) binding.ivFirstUploadimage.getContext( ),
                        getImageMaster.getGetImageDetails( ).get( 0 ).getImageUrl( ),
                        binding.ivFirstUploadimage, null );
                CommonUtils.setImageUsingGlide( ( Activity ) binding.ivSecondUploadimage.getContext( ),
                        getImageMaster.getGetImageDetails( ).get( 1 ).getImageUrl( ),
                        binding.ivSecondUploadimage, null );

                frontimage = getImageMaster.getGetImageDetails( ).get( 0 ).getImageUrl( );
                backimage = getImageMaster.getGetImageDetails( ).get( 1 ).getImageUrl( );

                binding.tvFirstUpload.setText( R.string.edit );
                binding.tvSecondUpload.setText( R.string.edit );
            } else if (getImageMaster.getSuccess( ) == 0) {
                Log.e( "getSuccess", "0" );
            }
        }
    }
}
