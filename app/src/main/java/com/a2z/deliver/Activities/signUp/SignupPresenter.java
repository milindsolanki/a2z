package com.a2z.deliver.activities.signUp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.a2z.deliver.models.login.LoginMaster;
import com.a2z.deliver.models.otp.SendOtpMaster;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
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


public class SignupPresenter {

    private final Service service;
    private final SignupActivity view;
    private CompositeSubscription subscriptions;
    private Activity activity;
    CameraIntentHelper mCameraIntentHelper;

    public SignupPresenter(Activity activity, Service service, SignupActivity view) {
        this.view = view;
        this.activity = activity;
        this.service = service;
        this.subscriptions = new CompositeSubscription( );
    }

    public boolean isvalidation(String fname, String lname, String phone, String email, String password,
                                     String repassword,String   usertImage, EditText etFirstname, EditText etLastname,
                                     EditText etMobilenumber, EditText etEmail, EditText etPassword, EditText etConfirmpassword) {
        if (CommonUtils.isEmpty( fname )) {
            CommonUtils.setEditTextError( etFirstname, activity.getResources( ).getString( R.string.enterfname ) );
            return false;
        } else if (CommonUtils.isEmpty( lname )) {
            CommonUtils.setEditTextError( etLastname, activity.getResources( ).getString( R.string.enterlname ) );
            return false;
        } else if (CommonUtils.isEmpty( phone )) {
            CommonUtils.setEditTextError( etMobilenumber, activity.getResources( ).getString( R.string.entermobno ) );
            return false;
        } else if (phone.length( ) < 10) {
            CommonUtils.setEditTextError( etMobilenumber, activity.getResources( ).getString( R.string.validtendigit ) );
            return false;
        } else if (CommonUtils.isEmpty( email )) {
            CommonUtils.setEditTextError( etEmail, activity.getResources( ).getString( R.string.enteremailid ) );
            return false;
        } else if (!CommonUtils.checkEmail( email )) {
            CommonUtils.setEditTextError( etEmail, activity.getResources( ).getString( R.string.validemail ) );
            return false;
        } else if (CommonUtils.isEmpty( password )) {
            CommonUtils.setEditTextError( etPassword, activity.getResources( ).getString( R.string.enterpassword ) );
            return false;
        } else if (CommonUtils.isEmpty( repassword )) {
            CommonUtils.setEditTextError( etConfirmpassword, activity.getResources( ).getString( R.string.entercpassword ) );
            return false;
        } else if (password.length( ) < 8) {
            CommonUtils.setEditTextError( etPassword, activity.getResources( ).getString( R.string.minpassword ) );
            return false;
        } else if (repassword.length( ) < 8) {
            CommonUtils.setEditTextError( etConfirmpassword, activity.getResources( ).getString( R.string.minpassword ) );
            return false;
        } else if (!password.equals( repassword )) {
            CommonUtils.makeLongToast( activity, "Password didn't match" );
            return false;
        }else if (CommonUtils.isEmpty( usertImage )) {
            CommonUtils.makeToast( activity, "Please Select Image" );
            return false;
        }
        return true;
    }

    public void getSignAPI(@Part(API_Params.deviceAccess) RequestBody deviceAccess,
                           @Part(API_Params.deviceToken) RequestBody deviceToken,
                           @Part(API_Params.firstName) RequestBody firstName,
                           @Part(API_Params.lastName) RequestBody lastName,
                           @Part(API_Params.email) RequestBody email,
                           @Part(API_Params.mobileNumber) RequestBody mobileNumber,
                           @Part(API_Params.password) RequestBody password,
                           @Part(API_Params.isEdit) RequestBody isEdit,
                           @Part(API_Params.userId) RequestBody userId,
                           @Part MultipartBody.Part image) {
        view.showWait( );
        Subscription subscription = service.getSignup( deviceAccess, deviceToken, firstName,
                lastName, email, mobileNumber, password, isEdit, userId, image, new Service.GetSignUpCallback( ) {


                    @Override
                    public void onSuccess(LoginMaster loginMaster) {
                        view.removeWait();
                        view.getSignUpSuccess( loginMaster );

                    }

                    @Override
                    public void onError(NetworkError networkError) {
                        view.removeWait( );
                        view.onFailure( networkError.getAppErrorMessage( ) );
                    }

                } );

        subscriptions.add( subscription );
    }

    public void getSendOtpAPI(JsonObject jsonObject) {
        view.showWait();
        Subscription subscription = service.getSendOtp(jsonObject, new Service.GetSendOtpCallback() {


            @Override
            public void onSuccess(SendOtpMaster sendOtpMaster) {
                view.removeWait();
                view.getSendOtpSuccess( sendOtpMaster );

            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe( );
    }

    public CameraIntentHelper getCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper( activity, new CameraIntentHelperCallback( ) {
            @Override
            public void onPhotoUriFound(int requestCode, Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                List<String> imagesList = new ArrayList<>( );
                imagesList.add( photoUri.getPath( ) );

                Intent intent = new Intent( new Intent( activity, CropImageActivity.class ) );
                intent.putExtra( CommonKeyword.RESULT, (Serializable) imagesList );
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

    public void imagePicker(final int whichImage) {
        final Dialog dialog = new Dialog( activity );
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
                captureImageCamera(whichImage);
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

    public void captureImageCamera(int whichImage) {
        try {
            if (mCameraIntentHelper != null) {
                mCameraIntentHelper.startCameraIntent(whichImage);
            } else {
                Log.e("tag", "camera null");
            }
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }

}
