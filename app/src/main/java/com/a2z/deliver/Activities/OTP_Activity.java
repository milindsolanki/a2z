package com.a2z.deliver.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a2z.deliver.Models.MessageModel;
import com.a2z.deliver.R;
import com.a2z.deliver.Utils.CommonUtils;
import com.a2z.deliver.Views.CustomEditText;
import com.a2z.deliver.Views.CustomTextview;
import com.a2z.deliver.WebService.API_Codes;
import com.a2z.deliver.WebService.API_Params;
import com.a2z.deliver.WebService.ApiHandler;
import com.a2z.deliver.WebService.ApiManager;
import com.a2z.deliver.WebService.ApiResponseInterface;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class OTP_Activity extends AppCompatActivity implements ApiResponseInterface {

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.et_otp)
    CustomEditText etOtp;
    @BindView(R.id.btn_otp)
    Button btnOtp;
    @BindView(R.id.otp_linearlayout)
    LinearLayout otpLinearlayout;
    ApiManager apiManager;
    String otpId = "";
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_otp );
        ButterKnife.bind( this );
        activity = this;

        apiManager = new ApiManager( activity, this );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            otpId = bundle.getString( API_Params.id );
        }
        tvHeaderText.setText( R.string.verify );
        otpLinearlayout.setOnTouchListener( new View.OnTouchListener( ) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = ( InputMethodManager ) getSystemService( INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow( getCurrentFocus( ).getWindowToken( ), 0 );
                return false;
            }
        } );
    }

    @OnClick(R.id.btn_otp)
    public void onContinueClick() {
        Call<MessageModel> verifyOtpDetailsCall= ApiHandler.getApiService().verifyOtpApi( getverifyOtpApiParams() );
        apiManager.makeApiRequest( verifyOtpDetailsCall, API_Codes.verifyOtp);
    }



    private JsonObject getverifyOtpApiParams() {
        JsonObject gsonObject=new JsonObject();
        try {
            JSONObject jsonObject = new JSONObject( );
            jsonObject.put( API_Params.id,otpId);
            jsonObject.put( API_Params.otp, etOtp.getText().toString().trim() );

            JsonParser parser = new JsonParser( );
            gsonObject = ( JsonObject ) parser.parse( jsonObject.toString( ) );

        } catch (Exception e) {
            e.printStackTrace( );
        }
        return gsonObject;
    }

    @OnClick(R.id.iv_login_back)
    public void onBackClicked() {
        Intent intent = new Intent( OTP_Activity.this, Signup_Email_Activity.class );
        startActivity( intent );
    }


    @Override
    public void isError(String errorCode, int apiCode) {

    }

    @Override
    public void isSuccess(Object response, int apiCode) {
        if (apiCode == API_Codes.verifyOtp) {
            MessageModel messageModel = ( MessageModel ) response;
            if (messageModel != null) {
                if (messageModel.getSuccess( ) == 1) {

                    Intent inten = new Intent( OTP_Activity.this, HomeActivity.class );
                    startActivity( inten );

                } else {
                    CommonUtils.makeLongToast( activity, messageModel.getMessage( ) + "" );
                }
            }
        }
    }
}
