package com.a2z.deliver.activities.sendItem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivitySendBinding;
import com.a2z.deliver.models.estimationRate.GetEstimationRateMaster;
import com.a2z.deliver.models.forgotPassword.ForgotPasswordDetails;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelper;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelperCallback;
import com.harmis.imagepicker.activities.CropImageActivity;
import com.harmis.imagepicker.activities.GalleryActivity;
import com.harmis.imagepicker.utils.CommonKeyword;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Aprod LLC. on 7/28/2018.
 */
public class SendPresenter {
    private final Service service;
    private CompositeSubscription subscriptions;
    private Activity activity;
    CameraIntentHelper mCameraIntentHelper;
    private ActivitySendBinding binding;
    private SendView view;
    String date_time = "";
    String currentDate="";
    int mYear;
    int mMonth;
    int mDay;
    int mHour;
    int mMinute;
    int value;

    public SendPresenter(Service service, Activity activity, ActivitySendBinding binding, SendView view) {
        this.service = service;
        this.activity = activity;
        this.binding = binding;
        this.view = view;
        this.subscriptions = new CompositeSubscription( );
    }

    public CameraIntentHelper setupCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper( activity, new CameraIntentHelperCallback( ) {
            @Override
            public void onPhotoUriFound(int requestCode, Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                List <String> imagesList = new ArrayList <>( );
                imagesList.add( photoUri.getPath( ) );

                Intent intent = new Intent( new Intent( activity, CropImageActivity.class ) );
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

    public void setDetails() {
        binding.ivSendSmall.setImageResource( R.drawable.ic_keyblack );
        binding.tvSendIcontxt.setText( R.string.small_text );
        binding.tvSendSmall.setTextColor( activity.getResources( ).getColor( R.color.color_c1c1c1 ) );
        binding.ivSendMedium.setImageResource( R.drawable.ic_boxblack );
        binding.tvSendMedium.setTextColor( activity.getResources( ).getColor( R.color.color_c1c1c1 ) );
        binding.ivSendLarge.setImageResource( R.drawable.ic_tvblack );
        binding.tvSendLarge.setTextColor( activity.getResources( ).getColor( R.color.color_c1c1c1 ) );
        binding.ivSendXLarge.setImageResource( R.drawable.ic_cycleblack );
        binding.tvSendXLarge.setTextColor( activity.getResources( ).getColor( R.color.color_c1c1c1 ) );
        binding.ivSendHuge.setImageResource( R.drawable.ic_sofablack );
        binding.tvSendHuge.setTextColor( activity.getResources( ).getColor( R.color.color_c1c1c1 ) );
        binding.ivSendPet.setImageResource( R.drawable.ic_petblack );
        binding.tvSendPet.setTextColor( activity.getResources( ).getColor( R.color.color_c1c1c1 ) );
    }

    public void selectSizes(String size) {

        setDetails( );
        switch (size) {
            case "small":
                binding.ivSendSmall.setImageResource( R.drawable.ic_keyblue );
                binding.tvSendIcontxt.setText( R.string.small_text );
                binding.tvSendSmall.setTextColor( activity.getResources( ).getColor( R.color.colorPrimary ) );
                break;
            case "medium":
                binding.ivSendMedium.setImageResource( R.drawable.ic_boxblue );
                binding.tvSendIcontxt.setText( R.string.medium_text );
                binding.tvSendMedium.setTextColor( activity.getResources( ).getColor( R.color.colorPrimary ) );
                break;
            case "large":
                binding.ivSendLarge.setImageResource( R.drawable.ic_tvblue );
                binding.tvSendIcontxt.setText( R.string.large_text );
                binding.tvSendLarge.setTextColor( activity.getResources( ).getColor( R.color.colorPrimary ) );
                break;
            case "x-large":
                binding.ivSendXLarge.setImageResource( R.drawable.ic_cycleblue );
                binding.tvSendXLarge.setTextColor( activity.getResources( ).getColor( R.color.colorPrimary ) );
                binding.tvSendIcontxt.setText( R.string.xlarge_text );
                break;
            case "huge":
                binding.ivSendHuge.setImageResource( R.drawable.ic_sofablue );
                binding.tvSendHuge.setTextColor( activity.getResources( ).getColor( R.color.colorPrimary ) );
                binding.tvSendIcontxt.setText( R.string.huge_text );
                break;
            case "pet":
                binding.ivSendPet.setImageResource( R.drawable.ic_petblue );
                binding.tvSendIcontxt.setText( R.string.pet_text );
                binding.tvSendPet.setTextColor( activity.getResources( ).getColor( R.color.colorPrimary ) );
                break;
        }
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
                Toast.makeText( activity.getApplicationContext( ), "Camera", Toast.LENGTH_SHORT ).show( );
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

    public void datePicker(final View view1) {

        // Get Current Date
        final Calendar c = Calendar.getInstance( );
        mYear = c.get( Calendar.YEAR );
        mMonth = c.get( Calendar.MONTH );
        mDay = c.get( Calendar.DAY_OF_MONTH );
        value = c.get( Calendar.AM_PM );
        final DatePickerDialog datePickerDialog = new DatePickerDialog( activity,
                new DatePickerDialog.OnDateSetListener( ) {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        timePicker( view1);
                    }
                }, mYear, mMonth, mDay );
        datePickerDialog.show( );
    }

    private void timePicker(final View view1) {
        // Get Current Time
        final Calendar c = Calendar.getInstance( );
        mHour = c.get( Calendar.HOUR );
        mMinute = c.get( Calendar.MINUTE );
        value = c.get( Calendar.AM_PM );
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog( activity,
                new TimePickerDialog.OnTimeSetListener( ) {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        switch (view1.getId()){
                            case R.id.rb_pickup_chooseTime:
                                binding.rbPickupChooseTime.setText( date_time + " " + hourOfDay + ":" + minute );
                                break;
                            case R.id.et_send_date_time:
                                binding.etSendDateTime.setText( date_time + " " + hourOfDay + ":" + minute );
                                break;

                        }

                    }
                }, mHour, mMinute, true );
        timePickerDialog.show( );

    }

    public void selectSendSelf(View view) {
        switch (view.getId( )) {
            case R.id.rb_send_myself:
                binding.etSendNamelocation.setVisibility( View.GONE );
                binding.etSendMNolocation.setVisibility( View.GONE );
                binding.etSendEmaillocation.setVisibility( View.GONE );
                break;
            case R.id.rb_send_someone:
                binding.etSendNamelocation.setVisibility( View.VISIBLE );
                binding.etSendMNolocation.setVisibility( View.VISIBLE );
                binding.etSendEmaillocation.setVisibility( View.VISIBLE );
                break;
            case R.id.rb_send_Iwill:
                binding.etSendNameDelivery.setVisibility( View.GONE );
                binding.etSendMNoDelivery.setVisibility( View.GONE );
                binding.etSendEmailDelivery.setVisibility( View.GONE );
                break;
            case R.id.rb_send_someElse:
                binding.etSendNameDelivery.setVisibility( View.VISIBLE );
                binding.etSendMNoDelivery.setVisibility( View.VISIBLE );
                binding.etSendEmailDelivery.setVisibility( View.VISIBLE );
                break;
        }
    }

    public void onSummaryClick(String usertImage) {
        String icontxt = binding.tvSendIcontxt.getText( ).toString( ).trim( );
        String picupaddress = binding.tvSendPicupAddress.getText( ).toString( ).trim( );
        String deliveryaddress = binding.tvSendDeliveryAddress.getText( ).toString( ).trim( );
        String itemname = binding.etSendItemname.getText( ).toString( ).trim( );
        String itemvalue = binding.etSendItemvalue.getText( ).toString( ).trim( );
        String dateandtime = binding.etSendDateTime.getText( ).toString( ).trim( );
        String radiopickuplocation = binding.rbSendSomeone.getText( ).toString( );

        if (TextUtils.isEmpty( picupaddress )) {
            Toast.makeText( activity, activity.getResources( ).getString( R.string.picupaddress ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( deliveryaddress )) {
            Toast.makeText( activity, activity.getResources( ).getString( R.string.deliveryaddress ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( itemname )) {
            Toast.makeText( activity, activity.getResources( ).getString( R.string.itemname___ ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( itemvalue )) {
            Toast.makeText( activity, activity.getResources( ).getString( R.string.itemvalue___ ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( usertImage )) {
            Toast.makeText( activity, activity.getResources( ).getString( R.string.uploadimage ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( dateandtime )) {
            Toast.makeText( activity, activity.getResources( ).getString( R.string.dateandtime ), Toast.LENGTH_LONG ).show( );
        } else if (binding.rbSendSomeone.isChecked( )) {
            if (TextUtils.isEmpty( binding.etSendNamelocation.getText( ).toString( ) )) {
                CommonUtils.setEditTextError( binding.etSendNamelocation, activity.getResources( ).getString( R.string.name_location ) );
            } else if (TextUtils.isEmpty( binding.etSendEmaillocation.getText( ).toString( ) )) {
                CommonUtils.setEditTextError( binding.etSendEmaillocation, activity.getResources( ).getString( R.string.email_ID ) );
            } else if (TextUtils.isEmpty( binding.etSendMNolocation.getText( ).toString( ) )) {
                CommonUtils.setEditTextError( binding.etSendMNolocation, activity.getResources( ).getString( R.string.mnumber ) );
            }
        } else if (binding.rbSendSomeElse.isChecked( )) {
            if (TextUtils.isEmpty( binding.etSendNamelocation.getText( ).toString( ) )) {
                CommonUtils.setEditTextError( binding.etSendNamelocation, activity.getResources( ).getString( R.string.name_location ) );
            } else if (TextUtils.isEmpty( binding.etSendEmaillocation.getText( ).toString( ) )) {
                CommonUtils.setEditTextError( binding.etSendEmaillocation, activity.getResources( ).getString( R.string.email_ID ) );
            } else if (TextUtils.isEmpty( binding.etSendMNolocation.getText( ).toString( ) )) {
                CommonUtils.setEditTextError( binding.etSendMNolocation, activity.getResources( ).getString( R.string.mnumber ) );
            }
        } else if(binding.rbPickupImmediate.isChecked()){
            currentDate = java.text.DateFormat.getDateTimeInstance( ).format( Calendar.getInstance( ).getTime( ) );
            binding.rbPickupChooseTime.setText( activity.getResources().getString( R.string.dateandtimeselectfrom ) );
        }
    }

    public void getDateTime(View view ,String text){
        if(binding.rbPickupImmediate.isChecked()){
            text = java.text.DateFormat.getDateTimeInstance( ).format( Calendar.getInstance( ).getTime( ) );
            binding.rbPickupChooseTime.setText( activity.getResources().getString( R.string.dateandtimeselectfrom ) );
        }else if(binding.rbPickupChooseTime.isChecked()){
            datePicker(  view );
            text = binding.rbPickupChooseTime.getText().toString().trim();
        }
    }
    /*-----------------------------------------------Get EstimationRate--------------------------------------------*/

    public void getEstimationRate(JsonObject jsonObject) {

        Log.e( "JSON PARAMS =", new Gson( ).toJson( jsonObject ).toString( ) );
        view.showWait( );

        Subscription subscription = service.getEstimationRate( jsonObject, new Service.GetEstimationRateCallBack( ) {
            @Override
            public void onSuccess(GetEstimationRateMaster getEstimationRateMaster) {
                view.removeWait( );
                view.getEstimationRate( getEstimationRateMaster );
            }

            @Override
            public void onError(NetworkError networkError) {
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

