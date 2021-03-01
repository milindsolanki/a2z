package com.a2z.deliver.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.a2z.deliver.Models.ChooseLocation.AddressDetail;
import com.a2z.deliver.R;
import com.a2z.deliver.Views.CustomEditText;
import com.a2z.deliver.Views.CustomTextview;
import com.a2z.deliver.WebService.API_Params;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelper;
import com.harmis.imagepicker.CameraUtils.CameraIntentHelperCallback;
import com.harmis.imagepicker.activities.CropImageActivity;
import com.harmis.imagepicker.activities.GalleryActivity;
import com.harmis.imagepicker.model.Images;
import com.harmis.imagepicker.utils.CommonKeyword;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendActivity extends AppCompatActivity {

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.tv_applyFilter)
    CustomTextview tvApplyFilter;
    @BindView(R.id.scroll_send)
    ScrollView scrollSend;
    @BindView(R.id.iv_send_small)
    ImageView ivSendSmall;
    @BindView(R.id.tv_send_small)
    CustomTextview tvSendSmall;
    @BindView(R.id.iv_send_medium)
    ImageView ivSendMedium;
    @BindView(R.id.tv_send_medium)
    CustomTextview tvSendMedium;
    @BindView(R.id.iv_send_large)
    ImageView ivSendLarge;
    @BindView(R.id.tv_send_large)
    CustomTextview tvSendLarge;
    @BindView(R.id.iv_send_xLarge)
    ImageView ivSendXLarge;
    @BindView(R.id.tv_send_xLarge)
    CustomTextview tvSendXLarge;
    @BindView(R.id.iv_send_huge)
    ImageView ivSendHuge;
    @BindView(R.id.tv_send_huge)
    CustomTextview tvSendHuge;
    @BindView(R.id.iv_send_pet)
    ImageView ivSendPet;
    @BindView(R.id.tv_send_pet)
    CustomTextview tvSendPet;
    @BindView(R.id.tv_send_icontxt)
    TextView tvSendIcontxt;
    @BindView(R.id.btn_send_viewsummary)
    Button btnSendViewsummary;

    public static final int REQUEST_PICKUP_ADDRESS = 101;
    public static final int REQUEST_DELIVERY_ADDRESS = 102;



    String size = "small,medium,large,x-large,huge,pet";
    @BindView(R.id.iv_send_uploadCamera)
    ImageView ivSendUploadCamera;

    String usertImage = "";
    String[] cameraPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final int REQUEST_PERMISSION_CAMERA = 1;

    CameraIntentHelper mCameraIntentHelper;
    @BindView(R.id.iv_send_uploadImage)
    ImageView ivSendUploadImage;
    @BindView(R.id.rb_send_myself)
    RadioButton rbSendMyself;
    @BindView(R.id.rb_send_someone)
    RadioButton rbSendSomeone;
    @BindView(R.id.et_send_namelocation)
    CustomEditText etSendNamelocation;
    @BindView(R.id.et_send_M_Nolocation)
    CustomEditText etSendMNolocation;
    @BindView(R.id.et_send_emaillocation)
    CustomEditText etSendEmaillocation;
    @BindView(R.id.rb_send_Iwill)
    RadioButton rbSendIwill;
    @BindView(R.id.rb_send_someElse)
    RadioButton rbSendSomeElse;
    @BindView(R.id.et_send_nameDelivery)
    CustomEditText etSendNameDelivery;
    @BindView(R.id.et_send_mNoDelivery)
    CustomEditText etSendMNoDelivery;
    @BindView(R.id.et_send_emailDelivery)
    CustomEditText etSendEmailDelivery;
    @BindView(R.id.tv_send_picupAddress)
    CustomTextview tvSendPicupAddress;
    @BindView(R.id.ll_send_picupAddress)
    LinearLayout llSendPicupAddress;
    @BindView(R.id.tv_send_deliveryAddress)
    CustomTextview tvSendDeliveryAddress;
    @BindView(R.id.ll_send_deliveryAddress)
    LinearLayout llSendDeliveryAddress;
    @BindView(R.id.et_send_date_time)
    CustomEditText etSendDateTime;

    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;

    boolean select = true;
    @BindView(R.id.layout_send_icon)
    LinearLayout layoutSendIcon;
    @BindView(R.id.et_send_itemname)
    CustomEditText etSendItemname;
    @BindView(R.id.et_send_itemvalue)
    CustomEditText etSendItemvalue;
    @BindView(R.id.et_send_lenght)
    CustomEditText etSendLenght;
    @BindView(R.id.et_send_width)
    CustomEditText etSendWidth;
    @BindView(R.id.et_send_height)
    CustomEditText etSendHeight;
    String string = "";
    @BindView(R.id.et_send_gross)
    CustomEditText etSendGross;
    @BindView(R.id.et_send_extra)
    CustomEditText etsendextra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_send );
        ButterKnife.bind( this );
        tvHeaderText.setText( R.string.sendSomething );
        setupCameraIntentHelper( );

        scrollSend.setOnTouchListener( new View.OnTouchListener( ) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = ( InputMethodManager ) getSystemService( INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow( getCurrentFocus( ).getWindowToken( ), 0 );
                return false;
            }
        } );


    }


    private void selectSizes(String size) {

        if (size.equals( "small" )) {
            /*ivSendSmall.isSelected( );*/
            ivSendSmall.setImageResource( R.drawable.ic_keyblue );
            tvSendIcontxt.setText( R.string.small_text );
            tvSendSmall.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
            ivSendMedium.setImageResource( R.drawable.ic_boxblack );
            tvSendMedium.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendLarge.setImageResource( R.drawable.ic_tvblack );
            tvSendLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendXLarge.setImageResource( R.drawable.ic_cycleblack );
            tvSendXLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendHuge.setImageResource( R.drawable.ic_sofablack );
            tvSendHuge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendPet.setImageResource( R.drawable.ic_petblack );
            tvSendPet.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );

        } else if (size.equals( "medium" )) {
            ivSendSmall.setImageResource( R.drawable.ic_keyblack );
            tvSendSmall.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendMedium.setImageResource( R.drawable.ic_boxblue );
            tvSendMedium.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
            tvSendIcontxt.setText( R.string.medium_text );
            ivSendLarge.setImageResource( R.drawable.ic_tvblack );
            tvSendLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendXLarge.setImageResource( R.drawable.ic_cycleblack );
            tvSendXLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendHuge.setImageResource( R.drawable.ic_sofablack );
            tvSendHuge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendPet.setImageResource( R.drawable.ic_petblack );
            tvSendPet.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );

        } else if (size.equals( "large" )) {
            ivSendSmall.setImageResource( R.drawable.ic_keyblack );
            tvSendSmall.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendMedium.setImageResource( R.drawable.ic_boxblack );
            tvSendMedium.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendLarge.setImageResource( R.drawable.ic_tvblue );
            tvSendIcontxt.setText( R.string.large_text );
            tvSendLarge.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
            ivSendXLarge.setImageResource( R.drawable.ic_cycleblack );
            tvSendXLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendHuge.setImageResource( R.drawable.ic_sofablack );
            tvSendHuge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendPet.setImageResource( R.drawable.ic_petblack );
            tvSendPet.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );

        } else if (size.equals( "x-large" )) {
            ivSendSmall.setImageResource( R.drawable.ic_keyblack );
            tvSendSmall.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendMedium.setImageResource( R.drawable.ic_boxblack );
            tvSendMedium.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendLarge.setImageResource( R.drawable.ic_tvblack );
            tvSendLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendXLarge.setImageResource( R.drawable.ic_cycleblue );
            tvSendXLarge.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
            tvSendIcontxt.setText( R.string.xlarge_text );
            ivSendHuge.setImageResource( R.drawable.ic_sofablack );
            tvSendHuge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendPet.setImageResource( R.drawable.ic_petblack );
            tvSendPet.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );

        } else if (size.equals( "huge" )) {
            ivSendSmall.setImageResource( R.drawable.ic_keyblack );
            tvSendSmall.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendMedium.setImageResource( R.drawable.ic_boxblack );
            tvSendMedium.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendLarge.setImageResource( R.drawable.ic_tvblack );
            tvSendLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendXLarge.setImageResource( R.drawable.ic_cycleblack );
            tvSendXLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendHuge.setImageResource( R.drawable.ic_sofablue );
            tvSendHuge.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );
            tvSendIcontxt.setText( R.string.huge_text );
            ivSendPet.setImageResource( R.drawable.ic_petblack );
            tvSendPet.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );

        } else if (size.equals( "pet" )) {
            ivSendSmall.setImageResource( R.drawable.ic_keyblack );
            tvSendSmall.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendMedium.setImageResource( R.drawable.ic_boxblack );
            tvSendMedium.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendLarge.setImageResource( R.drawable.ic_tvblack );
            tvSendLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendXLarge.setImageResource( R.drawable.ic_cycleblack );
            tvSendXLarge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendHuge.setImageResource( R.drawable.ic_sofablack );
            tvSendHuge.setTextColor( getResources( ).getColor( R.color.color_c1c1c1 ) );
            ivSendPet.setImageResource( R.drawable.ic_petblue );
            tvSendIcontxt.setText( R.string.pet_text );
            tvSendPet.setTextColor( getResources( ).getColor( R.color.colorPrimary ) );

        }
    }


    @OnClick({R.id.iv_send_small, R.id.iv_send_medium, R.id.iv_send_large, R.id.iv_send_xLarge, R.id.iv_send_huge, R.id.iv_send_pet})
    public void onViewClicked(View view) {
        switch (view.getId( )) {
            case R.id.iv_send_small:
                size = "small";
                selectSizes( size );

                break;

            case R.id.iv_send_medium:
                size = "medium";
                selectSizes( size );
                break;
            case R.id.iv_send_large:
                size = "large";
                selectSizes( size );
                break;
            case R.id.iv_send_xLarge:
                size = "x-large";
                selectSizes( size );
                break;
            case R.id.iv_send_huge:
                size = "huge";
                selectSizes( size );
                break;
            case R.id.iv_send_pet:
                size = "pet";
                selectSizes( size );
                break;
        }
    }


    @OnClick(R.id.iv_login_back)
    public void onbackClicked() {
       finish();

    }


    @OnClick(R.id.iv_send_uploadCamera)
    public void onUploadImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                uploadImage( );
            } else {
                ActivityCompat.requestPermissions( SendActivity.this, cameraPermissions, REQUEST_PERMISSION_CAMERA );
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            uploadImage( );
        }

    }

    private void uploadImage() {

        final Dialog dialog = new Dialog( this );
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
                Toast.makeText( getApplicationContext( ), "Camera", Toast.LENGTH_SHORT ).show( );
                captureImageCamera( );

            }
        } );

        btnGallery.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                dialog.dismiss( );
                Intent intent = new Intent( SendActivity.this, GalleryActivity.class );
                intent.putExtra( CommonKeyword.MAX_IMAGES, 1 );
                startActivityForResult( intent, CommonKeyword.REQUEST_CODE_GALLERY );
            }
        } );
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
                Glide.with( this )
                        .load( usertImage )
                        .apply( new RequestOptions( )
                                .placeholder( R.drawable.ic_error_image )
                                .error( R.drawable.ic_error_image ) )
                        .into( ivSendUploadImage );
            }
        } else if (resultCode == CommonKeyword.RESULT_CODE_CROP_IMAGE) {
            if (requestCode == CommonKeyword.REQUEST_CODE_CAMERA) {
                List <Images> images = ( List <Images> ) data.getSerializableExtra( CommonKeyword.RESULT );
                if (images != null && images.size( ) > 0) {
                    usertImage = images.get( 0 ).getImageUrl( );
                }
                Glide.with( this )
                        .load( usertImage )
                        .apply( new RequestOptions( )
                                .placeholder( R.drawable.ic_error_image )
                                .error( R.drawable.ic_error_image ) )
                        .into( ivSendUploadImage );

            }
        } else if (resultCode == API_Params.RESULT_PICKUP_ADDRESS) {
            if (requestCode == REQUEST_PICKUP_ADDRESS) {
                AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                if (addressDetail != null) {
                    tvSendPicupAddress.setText( addressDetail.getFullAddress( ) );
                }
            }
        } else if (resultCode == API_Params.RESULT_DELIVERY_ADDRESS) {
            if (requestCode == REQUEST_DELIVERY_ADDRESS) {
                AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                if (addressDetail != null) {
                    tvSendDeliveryAddress.setText( addressDetail.getFullAddress( ) );
                }
            }
        }
    }

    private void setupCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper( this, new CameraIntentHelperCallback( ) {
            @Override
            public void onPhotoUriFound(Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                List <String> imagesList = new ArrayList <>( );
                imagesList.add( photoUri.getPath( ) );

                Intent intent = new Intent( new Intent( getApplicationContext( ), CropImageActivity.class ) );
                intent.putExtra( CommonKeyword.RESULT, ( Serializable ) imagesList );
                startActivityForResult( intent, CommonKeyword.REQUEST_CODE_CAMERA );
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
            public void onPhotoUriNotFound() {

            }

            @Override
            public void logException(Exception e) {
                Log.e( "log_tag", "log Exception : " + e.getMessage( ) );
            }
        } );
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


    private void captureImageCamera() {
        try {
            if (mCameraIntentHelper != null) {
                mCameraIntentHelper.startCameraIntent( );
            }
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }


    @OnClick({R.id.rb_send_myself, R.id.rb_send_someone, R.id.rb_send_Iwill, R.id.rb_send_someElse})
    public void onRadioButton(View view) {
        switch (view.getId( )) {
            case R.id.rb_send_myself:
                etSendNamelocation.setVisibility( View.GONE );
                etSendMNolocation.setVisibility( View.GONE );
                etSendEmaillocation.setVisibility( View.GONE );
                break;
            case R.id.rb_send_someone:
                etSendNamelocation.setVisibility( View.VISIBLE );
                etSendMNolocation.setVisibility( View.VISIBLE );
                etSendEmaillocation.setVisibility( View.VISIBLE );
                break;
            case R.id.rb_send_Iwill:
                etSendNameDelivery.setVisibility( View.GONE );
                etSendMNoDelivery.setVisibility( View.GONE );
                etSendEmailDelivery.setVisibility( View.GONE );
                break;
            case R.id.rb_send_someElse:
                etSendNameDelivery.setVisibility( View.VISIBLE );
                etSendMNoDelivery.setVisibility( View.VISIBLE );
                etSendEmailDelivery.setVisibility( View.VISIBLE );
                break;
        }
    }

    @OnClick(R.id.ll_send_picupAddress)
    public void onpicupAddress() {
        Intent intent = new Intent( SendActivity.this, ChooseLocationActivity.class );
        intent.putExtra( API_Params.FROM_SCREEN, API_Params.PICKUP_SCREEN );
        startActivityForResult( intent, REQUEST_PICKUP_ADDRESS );
    }

    @OnClick(R.id.ll_send_deliveryAddress)
    public void ondeliveryAddress() {
        Intent intent = new Intent( SendActivity.this, ChooseLocationActivity.class );
        intent.putExtra( API_Params.FROM_SCREEN, API_Params.DELIVERY_SCREEN );
        startActivityForResult( intent, REQUEST_DELIVERY_ADDRESS );
    }


    @OnClick(R.id.et_send_date_time)
    public void onDateTime() {
        datePicker( );

    }

    private void datePicker() {

        // Get Current Date
        final Calendar c = Calendar.getInstance( );
        mYear = c.get( Calendar.YEAR );
        mMonth = c.get( Calendar.MONTH );
        mDay = c.get( Calendar.DAY_OF_MONTH );

        DatePickerDialog datePickerDialog = new DatePickerDialog( this,
                new DatePickerDialog.OnDateSetListener( ) {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        tiemPicker( );
                    }
                }, mYear, mMonth, mDay );
        datePickerDialog.show( );
    }

    private void tiemPicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance( );
        mHour = c.get( Calendar.HOUR );
        mMinute = c.get( Calendar.MINUTE );

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog( this,
                new TimePickerDialog.OnTimeSetListener( ) {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        etSendDateTime.setText( date_time + " " + hourOfDay + ":" + minute );
                    }
                }, mHour, mMinute, false );
        timePickerDialog.show( );

    }

    @OnClick(R.id.btn_send_viewsummary)
    public void onViewSummary() {
        String icontxt = tvSendIcontxt.getText( ).toString( ).trim( );
        String picupaddress = tvSendPicupAddress.getText( ).toString( ).trim( );
        String deliveryaddress = tvSendDeliveryAddress.getText( ).toString( ).trim( );
        String itemname = etSendItemname.getText( ).toString( ).trim( );
        String itemvalue = etSendItemvalue.getText( ).toString( ).trim( );
        String lenght = etSendLenght.getText( ).toString( ).trim( );
        String width = etSendWidth.getText( ).toString( ).trim( );
        String height = etSendHeight.getText( ).toString( ).trim( );
        // String uploadimage=ivSendUploadImage.getDrawable().toString().trim();
        String namelocation = etSendNamelocation.getText( ).toString( ).trim( );
        String mnumberlocation = etSendMNolocation.getText( ).toString( ).trim( );
        String emaillocation = etSendEmaillocation.getText( ).toString( ).trim( );
        String namedelivery = etSendNameDelivery.getText( ).toString( ).trim( );
        String mnumberdelivery = etSendMNoDelivery.getText( ).toString( ).trim( );
        String emaildelivery = etSendEmailDelivery.getText( ).toString( ).trim( );
        String dateandtime = etSendDateTime.getText( ).toString( ).trim( );
        String gross = etSendGross.getText( ).toString( ).trim( );

        if (TextUtils.isEmpty( picupaddress )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.picupaddress ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( deliveryaddress )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.deliveryaddress ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( itemname )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.itemname___ ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( itemvalue )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.itemvalue___ ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( lenght )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.lenghta ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( width )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.width__ ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( height )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.height__ ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( usertImage )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.uploadimage ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( dateandtime )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.selectIcon ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( icontxt )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.dateandtime ), Toast.LENGTH_LONG ).show( );
        } else if (TextUtils.isEmpty( icontxt )) {
            Toast.makeText( SendActivity.this, getResources( ).getString( R.string.gross ), Toast.LENGTH_LONG ).show( );
        }/*else {
            Intent intent  =new Intent( SendActivity.this,LoginActivity.class );

            startActivity( intent );
        }*/

    }

    public void alldata() {

        tvSendPicupAddress.getText( ).toString( ).trim( );
        tvSendDeliveryAddress.getText( ).toString( ).trim( );
        etSendItemname.getText( ).toString( ).trim( );
        etSendGross.getText( ).toString( ).trim( );
        etSendLenght.getText( ).toString( ).trim( );
        etSendWidth.getText( ).toString( ).trim( );
        etSendHeight.getText( ).toString( ).trim( );
        usertImage.toString( ).trim( );
        etSendNamelocation.getText( ).toString( ).trim( );
        etSendMNolocation.getText( ).toString( ).trim( );
        etSendEmaillocation.getText( ).toString( ).trim( );
        etSendNameDelivery.getText( ).toString( ).trim( );
        etSendMNoDelivery.getText( ).toString( ).trim( );
        etSendEmailDelivery.getText( ).toString( ).trim( );
        etSendDateTime.getText( ).toString( ).trim( );
        etsendextra.getText( ).toString( ).trim( );


    }

    @OnClick(R.id.iv_login_back)
    public void onViewClicked() {
        finish();
    }
}
