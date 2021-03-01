package com.a2z.deliver.activities.drivingLicence;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.a2z.deliver.R;
import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.drivingLicence.DrivingLicenceMaster;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.JsonObject;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelper;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelperCallback;
import com.harmis.imagepicker.activities.CropImageActivity;
import com.harmis.imagepicker.activities.GalleryActivity;
import com.harmis.imagepicker.utils.CommonKeyword;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class DrivingLicencePresenter {
    CameraIntentHelper mCameraIntentHelper;
    Activity activity;
    public final Service service;
    private final DrivingLicenceView view;
    private CompositeSubscription subscriptions;
    private static final String TAG = "log_tag";
    SharedPref sharedPref;

    public DrivingLicencePresenter(Activity activity, Service service, DrivingLicenceView view) {
        this.service = service;
        this.view = view;
        this.activity = activity;
        this.subscriptions = new CompositeSubscription( );
    }


    public CameraIntentHelper getmCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper( activity, new CameraIntentHelperCallback( ) {
            @Override
            public void onPhotoUriFound(int requestCode, Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                Log.e( TAG, "Which Image : " + requestCode );
                List <String> imagesList = new ArrayList <>( );
                imagesList.add( photoUri.getPath( ) );

                Intent intent = new Intent( activity, CropImageActivity.class );
                intent.putExtra( CommonKeyword.RESULT, ( Serializable ) imagesList );
                if (requestCode == API_Params.FIRST_IMAGE) {
                    activity.startActivityForResult( intent, CommonKeyword.REQUEST_CODE_CAMERA );
                } else if (requestCode == API_Params.SECOND_IMAGE) {
                    activity.startActivityForResult( intent, CommonKeyword.SECOND_REQUEST_CODE_CAMERA );
                }
            }

            @Override
            public void deletePhotoWithUri(Uri photoUri) {
                Log.e( TAG, "Delete photo with uri" );
            }

            @Override
            public void onSdCardNotMounted() {
                Log.e( TAG, "onSdCardNotMounted" );
            }

            @Override
            public void onCanceled() {
                Log.e( TAG, "onCanceled" );
            }

            @Override
            public void onCouldNotTakePhoto() {
                Log.e( TAG, "onCouldNotTakePhoto" );
            }

            @Override
            public void onPhotoUriNotFound(int requestCode) {
                Log.e( TAG, "onPhotoUriNotFound " + requestCode );
            }

            @Override
            public void logException(Exception e) {
                Log.e( "log_tag", "log Exception : " + e.getMessage( ) );
            }
        } );
        return mCameraIntentHelper;
    }

    public void uploadImage(final int whichImage) {
        final Dialog dialog = new Dialog( activity );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setCancelable( true );
        dialog.setContentView( R.layout.dialog_image_picker );
        dialog.getWindow( ).setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        dialog.getWindow( ).setGravity( Gravity.CENTER );
        dialog.show( );
        ImageButton btnCamera = dialog.findViewById( R.id.btn_camera_picker );
        ImageButton btnGallery = dialog.findViewById( R.id.btn_gallery_picker );

        btnCamera.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                dialog.dismiss( );
                captureImageCamera( whichImage );
            }
        } );

        btnGallery.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                dialog.dismiss( );
                Intent intent = new Intent( activity, GalleryActivity.class );
                intent.putExtra( CommonKeyword.MAX_IMAGES, 1 );
                if (whichImage == API_Params.FIRST_IMAGE) {
                    activity.startActivityForResult( intent, CommonKeyword.REQUEST_CODE_GALLERY );
                } else if (whichImage == API_Params.SECOND_IMAGE) {
                    activity.startActivityForResult( intent, CommonKeyword.SECOND_REQUEST_CODE_GALLERY );
                }
            }
        } );
    }

    private void captureImageCamera(int whichImage) {
        try {
            if (mCameraIntentHelper != null) {
                mCameraIntentHelper.startCameraIntent( whichImage );
            }
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }

    public void getUploadDrivingLicenceImageAPI(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                                @Part(API_Params.userId) RequestBody userId,
                                                @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                                @Part MultipartBody.Part image1,
                                                @Part MultipartBody.Part image2) {
        view.showWait( );
        Subscription subscription = service.getUploadDrivingLicenceImage( isUpdate, userId, isUpdateImage, image1, image2, new Service.GetUploadDrivingLicenceImageCallback( ) {


            @Override
            public void onSuccess(DrivingLicenceMaster drivingLicenceMaster) {
                view.removeWait( );
                Log.e( "presenter", "onSuccess" );
                view.getUpdateDrivingLicence( drivingLicenceMaster );

            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait( );
                Log.e( "presenter", "OnFailure" );
                view.onFailure( networkError.getAppErrorMessage( ) );
            }

        } );
        subscriptions.add( subscription );
    }

    /*----------------------------------------------------------------------------------------------*/

    public void getImageApi(JsonObject jsonObject) {
        view.showWait( );

        Subscription subscription = service.getImage( jsonObject, new Service.GetImageCallback( ) {
            @Override
            public void onSuccess(GetImageMaster getImageMaster) {
                view.removeWait( );
                Log.e( "success", "onSuccess" );
                view.getDrivingLicenceImage( getImageMaster );
            }

            @Override
            public void onError(NetworkError networkError) {
                Log.e( "success", "network error" );
                view.removeWait( );
                view.onFailure( networkError.getAppErrorMessage( ) );
            }
        } );
        subscriptions.add( subscription );
    }

    public void onStop() {
        subscriptions.unsubscribe( );
    }


}
