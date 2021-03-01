package com.a2z.deliver.activities.insurancePolicy;

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
import android.widget.Toast;

import com.a2z.deliver.R;
import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.drivingLicence.DrivingLicenceMaster;
import com.a2z.deliver.models.insurancePolicy.InsurancePolicyMaster;
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

public class InsurancePolicyPresenter {
    CameraIntentHelper cameraIntentHelper;
    Activity activity;
    public final Service service;
    private final InsurancePolicyView view;
    private CompositeSubscription subscriptions;

    public InsurancePolicyPresenter(Activity activity, Service service, InsurancePolicyView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
        this.activity = activity;
    }


    public CameraIntentHelper getCameraIntentHelper() {
        cameraIntentHelper = new CameraIntentHelper(activity, new CameraIntentHelperCallback() {
            @Override
            public void onPhotoUriFound(int requestCode, Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                List<String> imagesList = new ArrayList<>();
                imagesList.add(photoUri.getPath());

                Intent intent = new Intent(new Intent(activity, CropImageActivity.class));
                intent.putExtra(CommonKeyword.RESULT, (Serializable) imagesList);
                if (requestCode == API_Params.FIRST_IMAGE) {
                    activity.startActivityForResult(intent, CommonKeyword.REQUEST_CODE_CAMERA);
                } else if (requestCode == API_Params.SECOND_IMAGE) {
                    activity.startActivityForResult(intent, CommonKeyword.SECOND_REQUEST_CODE_CAMERA);
                } else if (requestCode == API_Params.THIRD_IMAGE) {
                    activity.startActivityForResult(intent, CommonKeyword.THIRD_REQUEST_CODE_CAMERA);
                } else if (requestCode == API_Params.FOURTH_IMAGE) {
                    activity.startActivityForResult(intent, CommonKeyword.FORTH_REQUEST_CODE_CAMERA);
                }
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
                Log.e("log_tag", "log Exception : " + e.getMessage());
            }
        });
        return cameraIntentHelper;
    }

    public void UploadImage(final int whichImage) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_image_picker);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();

        ImageButton btnCamera = dialog.findViewById(R.id.btn_camera_picker);
        ImageButton btnGallery = dialog.findViewById(R.id.btn_gallery_picker);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(activity, "Camera", Toast.LENGTH_SHORT).show();
                captureImageCamera1(whichImage);

            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(activity, GalleryActivity.class);
                intent.putExtra(CommonKeyword.MAX_IMAGES, 1);
                if (whichImage == API_Params.FIRST_IMAGE) {
                    activity.startActivityForResult(intent, CommonKeyword.REQUEST_CODE_GALLERY);
                } else if (whichImage == API_Params.SECOND_IMAGE) {
                    activity.startActivityForResult(intent, CommonKeyword.SECOND_REQUEST_CODE_GALLERY);
                } else if (whichImage == API_Params.THIRD_IMAGE) {
                    activity.startActivityForResult(intent, CommonKeyword.THIRD_REQUEST_CODE_GALLERY);
                } else if (whichImage == API_Params.FOURTH_IMAGE) {
                    activity.startActivityForResult(intent, CommonKeyword.FORTH_REQUEST_CODE_GALLERY);
                }
            }
        });
    }

    private void captureImageCamera1(int whichImage) {
        try {
            if (cameraIntentHelper != null) {
                cameraIntentHelper.startCameraIntent(whichImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getUploadIPImageAPI(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                    @Part(API_Params.userId) RequestBody userId,
                                    @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                    @Part MultipartBody.Part image1,
                                    @Part MultipartBody.Part image2,
                                    @Part MultipartBody.Part image3,
                                    @Part MultipartBody.Part image4) {
        view.showWait();
        Subscription subscription = service.getUploadInsurancePolicyImage(isUpdate, userId,isUpdateImage, image1, image2,image3,image4, new Service.GetUploadInsurancePolicyImageCallback() {


            @Override
            public void onSuccess(InsurancePolicyMaster insurancePolicyMaster) {
                view.removeWait();
                Log.e("presenter", "onSuccess");
                view.getInsurancePolicySuccess(insurancePolicyMaster);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                Log.e("presenter", "OnFailure");
                view.onFailure(networkError.getAppErrorMessage());
            }

        });
        subscriptions.add(subscription);
    }

    /*----------------------------------------Get Image API--------------------------------------------------*/

    public void getImageApi (JsonObject jsonObject){
        view.showWait();

        Subscription subscription = service.getImage( jsonObject, new Service.GetImageCallback() {
            @Override
            public void onSuccess(GetImageMaster getImageMaster) {
                view.removeWait();
                Log.e( "success","onSuccess" );
                view.getInsurancePolicyImageSuccess( getImageMaster );
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
}
