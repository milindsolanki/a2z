package com.a2z.deliver.activities.idProof;

import android.Manifest;
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
import com.a2z.deliver.databinding.ActivityIdProofBinding;
import com.a2z.deliver.models.GetImageDetails;
import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.idProof.UploadIdProofImage;
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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class IdProofActivity extends BaseApp implements IdProofView, View.OnClickListener {

    ActivityIdProofBinding binding;
    @Inject
    Service service;
    GetImageDetails getImageDetails ;
    IdProofPresenter presenter;
    String usertImage = "";
    String[] cameraPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int REQUEST_PERMISSION_CAMERA = 1;
    CameraIntentHelper mCameraIntentHelper;
    int whichImage;
    SharedPref sharedPref;
    LoginDetails loginDetails;
    String idProofImage = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getDeps( ).injectIdProof( this );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_id_proof );
        binding.setClickListener( this );
        binding.layoutHeader.setClickListener( this );
        binding.layoutHeader.tvHomeUpdate.setVisibility( View.GONE );
        init( );
    }

    private void init() {
        binding.layoutHeader.tvHeaderText.setText( getResources( ).getString( R.string.idproof ) );
        binding.layoutHeader.ivHomeFilter.setImageResource( R.drawable.ic_back_30 );
        binding.layoutHeader.tvHomeUpdate.setText( getResources( ).getString( R.string.update ) );
        presenter = new IdProofPresenter( this, service, this );
        mCameraIntentHelper = presenter.getmCameraIntentHelper( );
        sharedPref = SharedPref.getInstance( this );
        loginDetails = sharedPref.getLoginDetailsModel( );
        getImageAPI( );
         getImageDetails =  new GetImageDetails( );
    }

    private void getImageAPI() {
        presenter.getImageApi( getUploadImagePrams( ) );
    }

    private JsonObject getUploadImagePrams() {
        JsonObject gsonObject = new JsonObject( );
        try {

            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.userId, loginDetails.getUserId( ) );
            jsonObject.put( API_Params.flag, "4" );

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
                    presenter.uploadImage( whichImage );
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
                Log.e( "Tag", "insert resultCode requestCode  GALLERY" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );

                }
                if (usertImage != null) {
                    binding.layoutHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvFirstUpload.setText( getResources( ).getString( R.string.edit ) );
                    binding.layoutHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( this, usertImage, binding.ivFirstUploadimage, null );
                }
            }
        } else if (resultCode == CommonKeyword.RESULT_CODE_CROP_IMAGE) {
            if (requestCode == CommonKeyword.REQUEST_CODE_CAMERA) {
                Log.e( "Tag", "insert resultCode requestCode  CAMERA" );
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );
                    sharedPref.storeIdProofDetailsList( usertImage );
                    binding.layoutHeader.tvHomeUpdate.getResources( ).getColor( R.color.colorPrimary );
                }
                if (usertImage != null) {
                    binding.layoutHeader.tvHomeUpdate.setVisibility( View.VISIBLE );
                    binding.tvFirstUpload.setText( getResources( ).getString( R.string.edit ) );
                    binding.layoutHeader.tvHomeUpdate.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
                    CommonUtils.setImageUsingGlide( this, usertImage, binding.ivFirstUploadimage, null );
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.layoutHeader.ivHomeFilter) {
            finish( );
        } else if (v == binding.tvFirstUpload) {
            FirstImageUpload( );
        } else if (v == binding.layoutHeader.tvHomeUpdate) {
            updateImage( );
            binding.layoutHeader.tvHomeUpdate.setVisibility( View.GONE );
        }
    }

    private void updateImage() {

        if(idProofImage == null)
        {
            Log.e( "userImage", " Null" );
            MultipartBody.Part body = ApiManager.getMultipartImageBody( usertImage, API_Params.image1 );
            RequestBody isUpdate = RequestBody.create( MediaType.parse( "multipart/form-data" ), "1" );
            RequestBody isUpdateImage = RequestBody.create( MediaType.parse( "multipart/form-data" ), "0" );
            RequestBody userId = RequestBody.create( MediaType.parse( "multipart/form-data" ), loginDetails.getUserId( ) );
            presenter.getUpdateIdProofImage( isUpdate, userId, isUpdateImage, body );
        }else {
            Log.e( "userImage", "not Null" );
            MultipartBody.Part body = ApiManager.getMultipartImageBody( usertImage, API_Params.image1 );
            RequestBody isUpdate = RequestBody.create( MediaType.parse( "multipart/form-data" ), "1" );
            RequestBody isUpdateImage = RequestBody.create( MediaType.parse( "multipart/form-data" ), "1" );
            RequestBody userId = RequestBody.create( MediaType.parse( "multipart/form-data" ), loginDetails.getUserId( ) );
            presenter.getUpdateIdProofImage( isUpdate, userId, isUpdateImage, body );
        }
    }


    private void FirstImageUpload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                presenter.uploadImage( whichImage );
            } else {
                ActivityCompat.requestPermissions( IdProofActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            presenter.uploadImage( whichImage );
        }

    }


    @Override
    public void showWait() {
        CommonUtils.setProgressDialog( this, getResources( ).getString( R.string.app_name ), getResources( ).getString( R.string.imageupdatemsg ) );
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
    public void getIdProofSuccess(UploadIdProofImage uploadIdProofImage) {
        if (uploadIdProofImage != null) {

            if (uploadIdProofImage.getSuccess( ) == 1) {
                Log.e( "JSON RESPONSE: ", new Gson( ).toJson( uploadIdProofImage ).toString( ) );
                Toast.makeText( getApplicationContext( ), uploadIdProofImage.getMessage( ), Toast.LENGTH_SHORT ).show( );

            } else if (uploadIdProofImage.getSuccess( ) == 0) {
                Log.e("success","=0");
                Toast.makeText( getApplicationContext( ), uploadIdProofImage.getMessage( ), Toast.LENGTH_SHORT ).show( );
            }
        }

    }

    @Override
    public void getIdProofImage(GetImageMaster getImageMaster) {
        if (getImageMaster != null) {
            if (getImageMaster.getSuccess( ) == 1) {
                Log.e( "getSuccess", " 1 " );
                Log.e( "JSON RESPONSE: ", new Gson( ).toJson( getImageMaster ).toString( ) );
                Glide.with( binding.ivFirstUploadimage.getContext() )
                        .load( getImageMaster.getGetImageDetails().get( 0 ).getImageUrl() )
                        .into( binding.ivFirstUploadimage );
                idProofImage=getImageMaster.getGetImageDetails().get( 0 ).getImageUrl();
                binding.tvFirstUpload.setText( R.string.edit );
            } else if (getImageMaster.getSuccess( ) == 0) {
                Log.e( "getSuccess", "0" );
            }
        }
    }


}
