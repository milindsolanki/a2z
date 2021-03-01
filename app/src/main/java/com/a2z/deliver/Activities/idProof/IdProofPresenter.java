package com.a2z.deliver.activities.idProof;

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
import com.a2z.deliver.models.idProof.UploadIdProofImage;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
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

public class IdProofPresenter {

    private final Service service;
    private final IdProofActivity view;
    private CompositeSubscription subscriptions;
    private Activity activity;
    CameraIntentHelper mCameraIntentHelper;

    public IdProofPresenter(Activity activity, Service service, IdProofActivity view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription( );
        this.activity = activity;
    }

    public CameraIntentHelper getmCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper( activity, new CameraIntentHelperCallback( ) {
            @Override
            public void onPhotoUriFound(int requestCode, Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                List <String> imagesList = new ArrayList <>( );
                imagesList.add( photoUri.getPath( ) );

                Intent intent = new Intent( activity, CropImageActivity.class ) ;
                intent.putExtra( CommonKeyword.RESULT, ( Serializable ) imagesList );
                activity.startActivityForResult( intent, CommonKeyword.REQUEST_CODE_CAMERA );
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
            public void onPhotoUriNotFound(int requestCode) {

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
                activity.startActivityForResult( intent, CommonKeyword.REQUEST_CODE_GALLERY );
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

    /*----------------------------------------API Calling----------------------------------------------------*/
    public void getUpdateIdProofImage(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                      @Part(API_Params.userId) RequestBody userId,
                                      @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                      @Part MultipartBody.Part image) {
        view.showWait( );
        Subscription subscription = service.getUpdateIdProofImage( isUpdate, userId,isUpdateImage, image,
                new Service.GetUpdateIdProofCallback( ) {


                    @Override
                    public void onSuccess(UploadIdProofImage uploadIdProofImage) {
                        view.removeWait( );
                        view.getIdProofSuccess( uploadIdProofImage );

                    }

                    @Override
                    public void onError(NetworkError networkError) {
                        view.removeWait( );
                        view.onFailure( networkError.getAppErrorMessage( ) );
                    }

                } );
        subscriptions.add( subscription );
    }
/*----------------------------------------------------------------------------------------------------*/

    public void getImageApi (JsonObject jsonObject){
        view.showWait();

        Subscription subscription = service.getImage( jsonObject, new Service.GetImageCallback() {


            @Override
            public void onSuccess(GetImageMaster getImageMaster) {
                view.removeWait();
                Log.e( "success","onSuccess" );
                view.getIdProofImage( getImageMaster );
            }

            @Override
            public void onError(NetworkError networkError) {
                Log.e( "success","network error" );
                view.removeWait();
                view.onFailure( networkError.getAppErrorMessage() );
            }
        } );
        subscriptions.add( subscription );
    }

    /*----------------------------------------------------------------------------------------------*/


    public void onStop() {
        subscriptions.unsubscribe( );
    }
}
