package com.a2z.deliver.activities.insurancePolicy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityInsurancePolicyBinding;
import com.a2z.deliver.models.GetImageDetails;
import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.insurancePolicy.InsurancePolicyMaster;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.webService.ApiManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelper;
import com.harmis.imagepicker.model.Images;
import com.harmis.imagepicker.utils.CommonKeyword;

import org.json.JSONObject;

import java.util.EventListener;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InsurancePolicyActivity extends BaseApp implements View.OnClickListener, InsurancePolicyView {
    ActivityInsurancePolicyBinding binding;
    InsurancePolicyPresenter presenter;
    @Inject
    public Service service;
    Activity activity;

    GetImageDetails getImageDetails;
    String usertImage1 = null;
    String usertImage2 = null;
    String usertImage3 = null;
    String usertImage4 = null;
    String image1 = "";
    String image2 = "";
    String image3 = "";
    String image4 = "";

    String[] cameraPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int REQUEST_PERMISSION_CAMERA = 1;
    CameraIntentHelper mCameraIntentHelper;
    int whichImage;
    SharedPref sharedPref;
    LoginDetails loginDetails;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps( ).injectInsurancePolicy( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_insurance_policy );
        binding.setClickListener( this );
        binding.insuranceHeader.setClickListener( this );
        activity = this;
        init( );
    }

    public void init() {
        binding.insuranceHeader.tvHomeUpdate.setVisibility( View.GONE );
        binding.insuranceHeader.tvHeaderText.setText( R.string.incurancepolicyreceipt );
        binding.insuranceHeader.ivHomeFilter.setImageResource( R.drawable.ic_back_30 );
        binding.insuranceHeader.tvHomeUpdate.setText( R.string.update );
        activity = this;
        presenter = new InsurancePolicyPresenter( this, service, this );
        mCameraIntentHelper = presenter.getCameraIntentHelper( );
        sharedPref = SharedPref.getInstance( this );
        loginDetails = sharedPref.getLoginDetailsModel( );
        getImageApi( );
    }

    private void getImageApi() {
        presenter.getImageApi( getImagePrams( ) );
    }

    private JsonObject getImagePrams() {
        JsonObject gsonObject = new JsonObject( );
        try {

            JSONObject jsonObject = new JSONObject( );

            jsonObject.put( API_Params.userId, loginDetails.getUserId( ) );
            jsonObject.put( API_Params.flag, "3" );

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
        Log.e( "Tag", " insert resultCode requestCode  GALLERY" );
        if (resultCode == CommonKeyword.RESULT_CODE_GALLERY) {
            if (requestCode == CommonKeyword.REQUEST_CODE_GALLERY) {
                Log.e( "Tag", "First insert resultCode requestCode  GALLERY" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage1 = images.get( 0 ).getImageUrl( );
                }
                if (usertImage1 != null) {
                    binding.insuranceHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvFirstUpload.setText( R.string.edit );
                    binding.insuranceHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( activity, usertImage1, binding.ivFirstUploadimage, null );
                }

            } else if (requestCode == CommonKeyword.SECOND_REQUEST_CODE_GALLERY) {
                Log.e( "Tag", "Second insert resultCode requestCode  GALLERY" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage2 = images.get( 0 ).getImageUrl( );
                }
                if (usertImage2 != null) {
                    binding.insuranceHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvSecondUpload.setText( R.string.edit );
                    binding.insuranceHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( activity, usertImage2, binding.ivSecondUploadimage, null );
                }

            } else if (requestCode == CommonKeyword.THIRD_REQUEST_CODE_GALLERY) {
                Log.e( "Tag", "Therd insert resultCode requestCode  CAMERA" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage3 = images.get( 0 ).getImageUrl( );
                }
                if (usertImage3 != null) {
                    binding.insuranceHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvSecondUpload.setText( R.string.edit );
                    binding.insuranceHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( activity, usertImage3, binding.ivThirdUploadimage, null );
                }

            } else if (requestCode == CommonKeyword.FORTH_REQUEST_CODE_GALLERY) {
                Log.e( "Tag", "Therd insert resultCode requestCode  CAMERA" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage4 = images.get( 0 ).getImageUrl( );
                }
                if (usertImage4 != null) {
                    binding.insuranceHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvSecondUpload.setText( R.string.edit );
                    binding.insuranceHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( activity, usertImage4, binding.ivForthUploadimage, null );
                }

            }
        } else if (resultCode == CommonKeyword.RESULT_CODE_CROP_IMAGE) {
            if (requestCode == CommonKeyword.REQUEST_CODE_CAMERA) {
                Log.e( "Tag", "First Cam Request" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage1 = images.get( 0 ).getImageUrl( );
                }
                if (usertImage1 != null) {
                    binding.insuranceHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvFirstUpload.setText( R.string.edit );
                    binding.insuranceHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( activity, usertImage1, binding.ivFirstUploadimage, null );
                }

            } else if (requestCode == CommonKeyword.SECOND_REQUEST_CODE_CAMERA) {
                Log.e( "Tag", "Second Cam Request" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage2 = images.get( 0 ).getImageUrl( );
                }
                if (usertImage2 != null) {
                    binding.insuranceHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvSecondUpload.setText( R.string.edit );
                    binding.insuranceHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( activity, usertImage2, binding.ivSecondUploadimage, null );
                }

            } else if (requestCode == CommonKeyword.THIRD_REQUEST_CODE_CAMERA) {
                Log.e( "Tag", "Third Cam Request" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage3 = images.get( 0 ).getImageUrl( );
                }
                if (usertImage3 != null) {
                    binding.insuranceHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvSecondUpload.setText( R.string.edit );
                    binding.insuranceHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( activity, usertImage3, binding.ivThirdUploadimage, null );
                }
            } else if (requestCode == CommonKeyword.FORTH_REQUEST_CODE_CAMERA) {
                Log.e( "Tag", "Forth Cam Request" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage4 = images.get( 0 ).getImageUrl( );
                }
                if (usertImage4 != null) {
                    binding.insuranceHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvSecondUpload.setText( R.string.edit );
                    binding.insuranceHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( activity, usertImage4, binding.ivForthUploadimage, null );
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == binding.tvFirstUpload) {
            uploadFirstImage( );
        } else if (v == binding.tvSecondUpload) {
            uploadSecondImage( );
        } else if (v == binding.tvThirdUpload) {
            uploadThirdImage( );
        } else if (v == binding.tvForthUpload) {
            uploadFourthImage( );
        } else if (v == binding.insuranceHeader.tvHomeUpdate) {
            imageUploadApi( );
        }else if (v==binding.insuranceHeader.ivHomeFilter){
            finish();
        }
    }

    private void imageUploadApi() {
        RequestBody isUpdate = RequestBody.create( MediaType.parse( "multipart/form-data" ), "1" );
        RequestBody userId = RequestBody.create( MediaType.parse( "multipart/form-data" ), loginDetails.getUserId( ) );
        if (image1 != null || image2 != null || image3 != null || image4 != null) {
            RequestBody isUpdateImage = RequestBody.create( MediaType.parse( "multipart/form-data" ), "1" );
            MultipartBody.Part body1 = ApiManager.getMultipartImageBody( usertImage1, API_Params.image1 );
            MultipartBody.Part body2 = ApiManager.getMultipartImageBody( usertImage2, API_Params.image2 );
            MultipartBody.Part body3 = ApiManager.getMultipartImageBody( usertImage3, API_Params.image3 );
            MultipartBody.Part body4 = ApiManager.getMultipartImageBody( usertImage4, API_Params.image4 );
            presenter.getUploadIPImageAPI( isUpdate, userId, isUpdateImage, body1, body2, body3, body4 );
        }else {
            RequestBody isUpdateImage = RequestBody.create( MediaType.parse( "multipart/form-data" ), "0" );
            MultipartBody.Part body1 = ApiManager.getMultipartImageBody( usertImage1, API_Params.image1 );
            MultipartBody.Part body2 = ApiManager.getMultipartImageBody( usertImage2, API_Params.image2 );
            MultipartBody.Part body3 = ApiManager.getMultipartImageBody( usertImage3, API_Params.image3 );
            MultipartBody.Part body4 = ApiManager.getMultipartImageBody( usertImage4, API_Params.image4 );
            presenter.getUploadIPImageAPI( isUpdate, userId, isUpdateImage, body1, body2, body3, body4 );
        }
    }

    private void uploadFirstImage() {
        whichImage = API_Params.FIRST_IMAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                presenter.UploadImage( whichImage );
            } else {
                ActivityCompat.requestPermissions( InsurancePolicyActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            presenter.UploadImage( whichImage );
        }
    }

    private void uploadSecondImage() {
        whichImage = API_Params.SECOND_IMAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                presenter.UploadImage( whichImage );
            } else {
                ActivityCompat.requestPermissions( InsurancePolicyActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            presenter.UploadImage( whichImage );
        }
    }

    private void uploadThirdImage() {
        whichImage = API_Params.THIRD_IMAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                presenter.UploadImage( whichImage );
            } else {
                ActivityCompat.requestPermissions( InsurancePolicyActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            presenter.UploadImage( whichImage );
        }
    }

    private void uploadFourthImage() {
        whichImage = API_Params.FOURTH_IMAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                presenter.UploadImage( whichImage );
            } else {
                ActivityCompat.requestPermissions( InsurancePolicyActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
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
        Log.e( "onFailure", "0" );
    }

    @Override
    public void getInsurancePolicySuccess(InsurancePolicyMaster insurancePolicyMaster) {
        if (insurancePolicyMaster.getSuccess( ) == 1) {

            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( insurancePolicyMaster ).toString( ) );


           /* DriverDetails driverDetails = sharedPref.getDriverDetailsModel( );


            if (driverDetails == null) {
                driverDetails = new DriverDetails( );
            }

            String front = drivingLicenceMaster.getImageFront();
            String back = drivingLicenceMaster.getImageBack();

            driverDetails.setImageFront( front );
            driverDetails.setImageBack( back );
            sharedPref.storeDriverDetails( driverDetails.toString() );
*/
            getImageApi();
            binding.insuranceHeader.tvHomeUpdate.setVisibility( View.GONE );
        } else if (insurancePolicyMaster.getSuccess( ) == 0) {

            Log.e( "getSuccess", "0" );
        }
    }

    @Override
    public void getInsurancePolicyImageSuccess(GetImageMaster getImageMaster) {
        if (getImageMaster != null) {
            if (getImageMaster.getSuccess( ) == 1) {
                Log.e( "getSuccess", "1" );
                Log.e( "JSON RESPONSE: ", new Gson( ).toJson( getImageMaster ).toString( ) );

                CommonUtils.setImageUsingGlide( ( Activity ) binding.ivFirstUploadimage.getContext( ), getImageMaster.getGetImageDetails( ).get( 0 ).getImageUrl( ),
                        binding.ivFirstUploadimage, null );
                CommonUtils.setImageUsingGlide( ( Activity ) binding.ivFirstUploadimage.getContext( ), getImageMaster.getGetImageDetails( ).get( 1 ).getImageUrl( ),
                        binding.ivSecondUploadimage, null );
                CommonUtils.setImageUsingGlide( ( Activity ) binding.ivFirstUploadimage.getContext( ), getImageMaster.getGetImageDetails( ).get( 2 ).getImageUrl( ),
                        binding.ivThirdUploadimage, null );
                CommonUtils.setImageUsingGlide( ( Activity ) binding.ivFirstUploadimage.getContext( ), getImageMaster.getGetImageDetails( ).get( 3 ).getImageUrl( ),
                        binding.ivForthUploadimage, null );
                image1 = getImageMaster.getGetImageDetails( ).get( 0 ).getImageUrl( );
                image2 = getImageMaster.getGetImageDetails( ).get( 1 ).getImageUrl( );
                image3 = getImageMaster.getGetImageDetails( ).get( 2 ).getImageUrl( );
                image4 = getImageMaster.getGetImageDetails( ).get( 3 ).getImageUrl( );

                binding.tvFirstUpload.setText( getResources().getString( R.string.edit ));
                binding.tvSecondUpload.setText( getResources().getString( R.string.edit ) );
                binding.tvThirdUpload.setText(getResources().getString( R.string.edit ) );
                binding.tvForthUpload.setText( getResources().getString( R.string.edit ) );
            } else if (getImageMaster.getSuccess( ) == 0) {
                Log.e( "getSuccess", "0" );

            }
        }
    }
}
