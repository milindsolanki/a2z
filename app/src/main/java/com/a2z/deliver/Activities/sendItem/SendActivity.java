package com.a2z.deliver.activities.sendItem;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.activities.ItemSummary.ItemSummaryActivity;
import com.a2z.deliver.activities.chooseLocation.ChooseLocationActivity;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.databinding.ActivitySendBinding;
import com.a2z.deliver.models.chooseLocation.AddressDetail;
import com.a2z.deliver.models.estimationRate.GetEstimationRateMaster;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelper;
import com.harmis.imagepicker.model.Images;
import com.harmis.imagepicker.utils.CommonKeyword;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class SendActivity extends BaseApp implements View.OnClickListener, SendView {

    public static final int REQUEST_PICKUP_ADDRESS = 101;
    public static final int REQUEST_DELIVERY_ADDRESS = 102;
    @Inject
    Service service;
    String size = "small,medium,large,x-large,huge,pet";
    String itemsize = "";
    String usertImage = "";
    String text = "";
    String[] cameraPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int REQUEST_PERMISSION_CAMERA = 1;
    CameraIntentHelper mCameraIntentHelper;
    Activity activity;
    ActivitySendBinding binding;
    SendPresenter sendPresenter;
    int whichImage;
    boolean check = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_send );
        binding.homeHeader.tvHeaderText.setText( R.string.sendSomething );
        binding.setClickListener( this );
        binding.homeHeader.setClickListener( this );
        init( );

    }

    private void init() {
        activity = this;
        sendPresenter = new SendPresenter( service, this, binding, ( SendView ) this );
        mCameraIntentHelper = sendPresenter.setupCameraIntentHelper( );
        ProgressBar progressBar = new ProgressBar( this );

        binding.scrollSend.setOnTouchListener( new View.OnTouchListener( ) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = ( InputMethodManager ) getSystemService( INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow( getCurrentFocus( ).getWindowToken( ), 0 );
                return false;
            }
        } );
    }

    @Override
    public void onClick(View v) {
        if (v == binding.homeHeader.ivLoginBack) {
            finish( );
        } else if (v == binding.ivSendUploadCamera) {

            pickImage( );
        } else if (v == binding.btnSendViewsummary) {
            sendPresenter.onSummaryClick( usertImage );
        } else if (v == binding.etSendDateTime) {
            sendPresenter.datePicker( v);
        } else if (v == binding.rbSendMyself || v == binding.rbSendSomeone || v == binding.rbSendIwill || v == binding.rbSendSomeElse) {
            sendPresenter.selectSendSelf( v );
        } else if (v == binding.llSendPicupAddress) {
            startPickupIntent( );
        } else if (v == binding.llSendDeliveryAddress) {
            startDeliveryIntent( );
        } else if (v == binding.rbPickupChooseTime) {
            sendPresenter.getDateTime( v,text );
            Log.e( "TEXT = ",text );
        } else if (v == binding.rbPickupImmediate) {
            sendPresenter.getDateTime( v ,text);
        }else if (v == binding.homeHeader.ivLoginBack) {
            getlogout( getResources().getString( R.string.app_name),getResources().getString( R.string.backtest )  );
        }else {
            if (v == binding.llSendSmall) {
                size = "small";
                itemsize = "Small";
                //sendPresenter.getEstimationRate( getEstimationRateParams( ) );
            } else if (v == binding.llSendMedium) {
                size = "medium";
                itemsize = "Medium";
            } else if (v == binding.llSendLarge) {
                size = "large";
                itemsize = "Large";
            } else if (v == binding.llSendXLarge) {
                size = "x-large";
                itemsize = "X-Large";
            } else if (v == binding.llSendHuge) {
                size = "huge";
                itemsize = "Huge";
            } else if (v == binding.llSendPet) {
                size = "pet";
                itemsize = "Pet";
            }
            sendPresenter.selectSizes( size );
        }
        /*String chooseDate= binding.rbPickupChooseTime.getText().toString().trim();
        Log.e( "TEXT = ",chooseDate );*/
    }


    private JsonObject getEstimationRateParams() {
        JsonObject gsonObject = new JsonObject( );
        try {
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.size, itemsize );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );

        } catch (Exception e) {
            e.printStackTrace( );
        }
        return gsonObject;
    }

    private void startPickupIntent() {
        Intent intent = new Intent( SendActivity.this, ChooseLocationActivity.class );
        intent.putExtra( API_Params.FROM_SCREEN, API_Params.PICKUP_SCREEN );
        startActivityForResult( intent, REQUEST_PICKUP_ADDRESS );
    }

    private void startDeliveryIntent() {
        Intent intent = new Intent( SendActivity.this, ChooseLocationActivity.class );
        intent.putExtra( API_Params.FROM_SCREEN, API_Params.DELIVERY_SCREEN );
        startActivityForResult( intent, REQUEST_DELIVERY_ADDRESS );
    }

    private void pickImage() {
        whichImage = API_Params.FIRST_IMAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                sendPresenter.uploadImage( whichImage );
            } else {
                ActivityCompat.requestPermissions( SendActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            sendPresenter.uploadImage( whichImage );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
                    sendPresenter.uploadImage( whichImage );
                }
            }
        }

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
                    binding.ivSendUploadImage.setVisibility( View.VISIBLE );
                }
                CommonUtils.setImageUsingGlide( activity, usertImage, binding.ivSendUploadImage, null );
            }
        } else if (resultCode == CommonKeyword.RESULT_CODE_CROP_IMAGE) {
            if (requestCode == CommonKeyword.REQUEST_CODE_CAMERA) {

                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );
                    binding.ivSendUploadImage.setVisibility( View.VISIBLE );
                }
                CommonUtils.setImageUsingGlide( activity, usertImage, binding.ivSendUploadImage, null );
            }
        } else if (resultCode == API_Params.RESULT_PICKUP_ADDRESS) {
            if (requestCode == REQUEST_PICKUP_ADDRESS) {
                AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                if (addressDetail != null) {
                    binding.tvSendPicupAddress.setText( addressDetail.getFullAddress( ) );
                }
            }
        } else if (resultCode == API_Params.RESULT_DELIVERY_ADDRESS) {
            if (requestCode == REQUEST_DELIVERY_ADDRESS) {
                AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                if (addressDetail != null) {
                    binding.tvSendDeliveryAddress.setText( addressDetail.getFullAddress( ) );
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
    public void showWait() {

    }

    @Override
    public void removeWait() {

    }

    @Override
    public void onFailure(String appErrorMessage) {
        CommonUtils.makeToast( getApplicationContext( ), appErrorMessage );
    }

    @Override
    public void getEstimationRate(GetEstimationRateMaster getEstimationRateMaster) {
        if (getEstimationRateMaster != null) {
            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( getEstimationRateMaster ).toString( ) );
            if (getEstimationRateMaster.getSuccess( ) == 1) {

                CommonUtils.makeLongToast( getApplicationContext( ), getEstimationRateMaster.getSuccess( ).toString( ) );
            } else if (getEstimationRateMaster.getSuccess( ) == 0) {
                Toast.makeText( getApplicationContext( ), getEstimationRateMaster.getSuccess( ), Toast.LENGTH_SHORT ).show( );
            }
        }
    }
    public void getlogout(String sTitle, String sMessage) {

        final AlertDialog.Builder builder = new AlertDialog.Builder( SendActivity.this );
        builder.setIcon( R.drawable.ic_signout );
        builder.setTitle( sTitle );
        builder.setMessage( sMessage );
        builder.setPositiveButton( R.string.yes, new DialogInterface.OnClickListener( ) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent( SendActivity.this, MainActivity.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
            }
        } );
        builder.setNegativeButton( R.string.no, new DialogInterface.OnClickListener( ) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel( );
            }
        } );
        builder.show( );
    }
}
