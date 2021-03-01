package com.a2z.deliver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.a2z.deliver.Fragments.MoreFragment;
import com.a2z.deliver.Models.Login.LoginDetails;
import com.a2z.deliver.Models.Login.LoginMaster;
import com.a2z.deliver.Models.MessageModel;
import com.a2z.deliver.R;
import com.a2z.deliver.Utils.CommonUtils;
import com.a2z.deliver.Utils.SharedPref;
import com.a2z.deliver.Views.CustomEditText;
import com.a2z.deliver.Views.CustomTextview;
import com.a2z.deliver.WebService.API_Codes;
import com.a2z.deliver.WebService.API_Params;
import com.a2z.deliver.WebService.ApiHandler;
import com.a2z.deliver.WebService.ApiManager;
import com.a2z.deliver.WebService.ApiResponseInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.a2z.deliver.WebService.APIUrl.changePassword;

public class ChangePasswordActivity extends AppCompatActivity implements ApiResponseInterface {

    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.et_newpwd)
    CustomEditText etNewpwd;
    @BindView(R.id.et_confirmpwd)
    CustomEditText etConfirmpwd;
    @BindView(R.id.btn_save_password)
    Button btnSavePassword;
    ApiManager apiManager;
    SharedPref sharedPref;
    LoginDetails loginDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_change_password );
        ButterKnife.bind( this );
        tvHeaderText.setText( R.string.changepassword );

        apiManager = new ApiManager( this, this );

        sharedPref = new SharedPref( this );
        String response = sharedPref.getLoginDetails();
        final Gson gson = new Gson();
        Type collectionType = new TypeToken<LoginDetails>() {
        }.getType();
        loginDetails = gson.fromJson( response, collectionType );
    }


    @OnClick(R.id.btn_save_password)
    public void onSaveClicked() {
        String newpassword = etNewpwd.getText().toString().trim();
        String confirmpassowrd = etConfirmpwd.getText().toString().trim();

        if (TextUtils.isEmpty( newpassword )) {
            CommonUtils.setEditTextError( etNewpwd, getResources().getString( R.string.enterpassword ) );
        } else if (TextUtils.isEmpty( confirmpassowrd )) {
            CommonUtils.setEditTextError( etConfirmpwd, getResources().getString( R.string.enterpassword ) );
        } else if (newpassword.length() < 8) {
            CommonUtils.setEditTextError( etNewpwd, getResources().getString( R.string.minpassword ) );
        } else if (confirmpassowrd.length() < 8) {
            CommonUtils.setEditTextError( etConfirmpwd, getResources().getString( R.string.minpassword ) );
        } else if (!newpassword.equals( confirmpassowrd )) {
            CommonUtils.makeToast( getApplicationContext(), getResources().getString( R.string.validcpassword ) );
        } else {
            Call<MessageModel> messageModelCall = ApiHandler.getApiService().changePassword( changePasswordParams() );
            apiManager.makeApiRequest( messageModelCall, API_Codes.changepassword );
        }

    }

    private JsonObject changePasswordParams() {
        JsonObject gsonObject = new JsonObject();
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put( API_Params.userId, loginDetails.getUserId() );
            jsonObject.put( API_Params.email, loginDetails.getEmail() );
            jsonObject.put( API_Params.password, etConfirmpwd.getText().toString().trim() );

            JsonParser parser = new JsonParser();
            gsonObject = (JsonObject) parser.parse( jsonObject.toString() );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gsonObject;
    }

    @OnClick(R.id.iv_login_back)
    public void onBackClicked() {
    }

    @Override
    public void isError(String errorCode, int apiCode) {
        CommonUtils.makeToast( getApplicationContext(), "Network Error!!" );
    }

    @Override
    public void isSuccess(Object response, int apiCode) {
        if (apiCode == API_Codes.changepassword) {
            MessageModel messageModel = (MessageModel) response;

            Log.e( "JSON RESPONSE: ", new Gson( ).toJson( messageModel ).toString( ) );

            if (messageModel.getSuccess() == 1) {
                Toast.makeText( getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent( ChangePasswordActivity.this, HomeActivity.class );
                startActivity( intent );
            } else if (messageModel.getSuccess() == 0) {
                Toast.makeText( getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        }
    }
}
