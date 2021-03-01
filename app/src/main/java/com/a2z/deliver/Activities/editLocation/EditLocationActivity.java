package com.a2z.deliver.activities.editLocation;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityEditLocationBinding;
import com.a2z.deliver.models.chooseLocation.AddressDetail;
import com.a2z.deliver.models.chooseLocation.EditLocationMaster;
import com.a2z.deliver.models.login.LoginDetails;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.Gson;

import javax.inject.Inject;

public class EditLocationActivity extends BaseApp implements View.OnClickListener, EditLocationView {

    String fromScreen = "";
    String isedit = "";
    Activity activity;
    AddressDetail addressDetail;
    SharedPref sharedPref;
    LoginDetails loginDetails;
    String addressID = "";
    String addresstype = "";
    ActivityEditLocationBinding binding;
    EditLocationPresenter presenter;
    @Inject
    Service service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().injectEditLocation(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_location);
        binding.setClickListener(this);
        binding.layoutHeader.setClickListener( this );
        init();
    }

    private void init() {
        activity = this;
        presenter = new EditLocationPresenter(activity, service, this, binding);
        addressDetail = (AddressDetail) getIntent().getSerializableExtra(API_Params.RESULT);
        sharedPref = SharedPref.getInstance(EditLocationActivity.this);
        loginDetails = sharedPref.getLoginDetailsModel();
        binding.layoutHeader.tvHeaderText.setText(R.string.editaddress);
        fromScreen = getIntent().getExtras().getString(API_Params.FROM_SCREEN);
        isedit = getIntent().getExtras().getString(API_Params.IS_EDIT);
        addresstype = getIntent().getExtras().getString(API_Params.ADDRESS_TYPE);
        addressID = getIntent().getExtras().getString(API_Params.ADDRESS_ID);

        String data = getIntent().getExtras().getString(API_Params.DATA);
        binding.etEditaddress.setText(data);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.layoutHeader.ivLoginBack) {
            finish();
        } else if (view == binding.btnAddAddress) {
            if (addressDetail != null)
                presenter.editAddress(addressDetail, isedit, addresstype, addressID);
        }
    }

    @Override
    public void showWait() {
        CommonUtils.setProgressDialog( this,getResources().getString( R.string.app_name ),getResources().getString( R.string.toastedtilocation ) );
    }

    @Override
    public void removeWait() {
        CommonUtils.hideProgressDialog();
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getEditAddress(EditLocationMaster editLocationMaster) {
        if (editLocationMaster != null) {
            Log.e("JSON RESPONSE: ", new Gson().toJson(editLocationMaster).toString());
            if (editLocationMaster != null) {
                AddressDetail addressDetail = editLocationMaster.getAddressDetail();
                if (editLocationMaster.getSuccess() == 1) {
                    if (addressDetail != null) {
                        Toast.makeText(getApplicationContext(), "Address updated successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra(API_Params.RESULT, addressDetail);
                        intent.putExtra(API_Params.IS_EDIT, isedit);

                        if (fromScreen.equals(API_Params.PICKUP_SCREEN)) {
                            setResult(API_Params.RESULT_PICKUP_ADDRESS, intent);
                        } else if (fromScreen.equals(API_Params.DELIVERY_SCREEN)) {
                            setResult(API_Params.RESULT_DELIVERY_ADDRESS, intent);
                        }
                        finish();
                    }

                } else if (editLocationMaster.getSuccess() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Address !", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
