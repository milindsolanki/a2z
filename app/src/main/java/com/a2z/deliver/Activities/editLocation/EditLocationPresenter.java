package com.a2z.deliver.activities.editLocation;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.a2z.deliver.R;
import com.a2z.deliver.databinding.ActivityEditLocationBinding;
import com.a2z.deliver.models.chooseLocation.AddressDetail;
import com.a2z.deliver.models.chooseLocation.EditLocationMaster;
import com.a2z.deliver.models.chooseLocation.GoogleAddress;
import com.a2z.deliver.networking.NetworkError;
import com.a2z.deliver.networking.Service;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.utils.SharedPref;
import com.a2z.deliver.webService.API_Params;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class EditLocationPresenter {

    private final Service service;
    private final EditLocationView view;
    private CompositeSubscription subscriptions;
    private Activity activity;
    ActivityEditLocationBinding binding;

    public EditLocationPresenter(Activity activity, Service service, EditLocationView view, ActivityEditLocationBinding binding) {
        this.activity = activity;
        this.service = service;
        this.view = view;
        this.binding = binding;
        this.subscriptions = new CompositeSubscription();
    }

    public void getEditAddress(JsonObject jsonObject) {
        view.showWait();
        Subscription subscription = service.getEditAddress(jsonObject, new Service.GetEditAddressCallback() {

            @Override
            public void onSuccess(EditLocationMaster editLocationMaster) {
                view.removeWait();
                view.getEditAddress(editLocationMaster);
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
        subscriptions.unsubscribe();
    }

    public void editAddress(AddressDetail addressDetail, String isEdit, String addressType, String addressId) {
        String block = binding.etUnitEditlocation.getText().toString().trim();
        String companyname = binding.etCompanynameEditlocation.getText().toString().trim();
        if (TextUtils.isEmpty(block)) {
            CommonUtils.setEditTextError(binding.etUnitEditlocation, activity.getResources().getString(R.string.block));
        } else {
            getEditAddress(getEditLocationApi(addressDetail, isEdit, addressType, addressId));
        }
    }

    private JsonObject getEditLocationApi(AddressDetail addressDetail, String isEdit, String addressType, String addressId) {
        JsonObject gsonObject = new JsonObject();
        try {

            JSONObject jsonObject1 = new JSONObject();
            GoogleAddress googleAddress = addressDetail.getGoogleAddress();
            Log.e("tag", googleAddress.toString());
            jsonObject1.put(API_Params.userId, SharedPref.getInstance(activity).getLoginDetailsModel().getUserId());
            jsonObject1.put(API_Params.fullAddress, googleAddress.getFullAddress());
            jsonObject1.put(API_Params.notes, binding.etNoteEditlocation.getText().toString().trim());
            jsonObject1.put(API_Params.lat, addressDetail.getLat());
            jsonObject1.put(API_Params.Long, addressDetail.getLong());
            if (isEdit.equals("2")) {
                jsonObject1.put(API_Params.isEdit, "0");
            } else {
                jsonObject1.put(API_Params.isEdit, isEdit);
            }
            jsonObject1.put(API_Params.addressId, addressId);
            jsonObject1.put(API_Params.block, binding.etUnitEditlocation.getText().toString());
            jsonObject1.put(API_Params.companyName, binding.etCompanynameEditlocation.getText().toString());
            jsonObject1.put(API_Params.addressType, addressType);

            JSONObject jsonObject = new JSONObject(googleAddress.toString());

            JSONObject subObj1 = new JSONObject();
            JSONObject subObj2 = new JSONObject();
            JSONObject subObj3 = new JSONObject();
            JSONObject subObj4 = new JSONObject();

            subObj1.put("type", "locality");
            subObj1.put("name", googleAddress.getAcLocality());

            subObj2.put("type", "sublocality");
            subObj2.put("name", googleAddress.getAcSubLocality());

            subObj3.put("type", "administrativeArea");
            subObj3.put("name", googleAddress.getAcAdminArea());

            subObj4.put("type", "subadministrativeArea");
            subObj4.put("name", googleAddress.getAcSubAdminArea());

            JSONArray acArray = new JSONArray();

            acArray.put(subObj1);
            acArray.put(subObj2);
            acArray.put(subObj3);
            acArray.put(subObj4);

            jsonObject.put(API_Params.addressComponents, acArray);
            jsonObject1.put(API_Params.googleAddress, jsonObject);


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObject1.toString());
            Log.e("error", gsonObject.toString());

        } catch (Exception e) {
            Log.e("TAg", "error :" + e.getMessage());
            e.printStackTrace();
        }
        return gsonObject;

    }
}
