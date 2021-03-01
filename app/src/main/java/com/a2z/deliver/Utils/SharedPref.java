package com.a2z.deliver.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.a2z.deliver.models.DriverDetails;
import com.a2z.deliver.models.drivingLicence.DrivingLicenceMaster;
import com.a2z.deliver.models.login.LoginDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SharedPref {

    public Activity mActivity = null;
    private static final String COMPARESESSION_PREFENCE_NAME = "a2z_app";
    private static final String DEVICE_TOKEN = "device_token";
    public static final String LOGIN_DETAILS = "login_details";
    public static final String REGISTER_DETAILS = "register_details";
    public static final String DISTANCE_UNIT = "distance_unit";
    public static final String ADDRESS = "address";
    public static final String EDITADDRESS = "editaddressdetailslist";
    public static final String FILTERS = "filters";
    public static final String IDPROOF = "idproof";
    public static final String DRIVING = "driving";
    //public static final String DRIVINGBACK = "drivingback";

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static SharedPref sharedPreference = null;

    @SuppressLint("CommitPrefEdits")
    private SharedPref(Context activity) {
        sharedPref = activity.getSharedPreferences(
                COMPARESESSION_PREFENCE_NAME, 0);
        editor = sharedPref.edit();

    }

    public static SharedPref getInstance(Context context){
        if(sharedPreference == null)
        {
            sharedPreference = new SharedPref(context);
        }
        return sharedPreference;
    }

    @SuppressLint("CommitPrefEdits")
    public SharedPref(Activity mfragmentactivity) {
        sharedPref = mfragmentactivity.getSharedPreferences(
                COMPARESESSION_PREFENCE_NAME, 0);
        editor = sharedPref.edit();

    }


    public void deleteAllSharedPrefs() {
        editor.clear();
        editor.commit();
    }

    // Device token
    public String getDeviceToken() {
        return sharedPref.getString(DEVICE_TOKEN, null);
    }

    public void storeDeviceToken(String menu_content) {
        try {
            editor.putString(DEVICE_TOKEN, menu_content);
            editor.commit();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetDeviceToken() {
        try {
            editor.putString(DEVICE_TOKEN, null);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getRegisterDetails(){
        return sharedPref.getString(  LOGIN_DETAILS,null);
    }
    public void storeRegisterDetails(String register){

        try {
            editor.putString(LOGIN_DETAILS, register);
            editor.commit();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getLoginDetails() {
        return sharedPref.getString(LOGIN_DETAILS, null);
    }

    public void storeLoginDetails(String login) {
        try {
            editor.putString(LOGIN_DETAILS, login);
            editor.commit();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetLoginDetails() {
        try {
            editor.putString(LOGIN_DETAILS, null);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDistanceUnit() {
        return sharedPref.getString( DISTANCE_UNIT, "km" );
    }

    public void storeDistanceUnit(String unit) {
        try {
            editor.putString(DISTANCE_UNIT, unit);
            editor.commit();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilters() {
        return sharedPref.getString( FILTERS, null);
    }

    public void storeFilters(String isFilter) {
        try {
            editor.putString(FILTERS, isFilter);
            editor.commit();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEditAddressDetailsList() {
        return sharedPref.getString(EDITADDRESS, null);
    }

    public void storeEditAddressDetailsList(String editaddress) {
        try {
            editor.putString(EDITADDRESS, editaddress);
            editor.commit();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LoginDetails getLoginDetailsModel() {
        LoginDetails loginDetails;
        String response = getLoginDetails();
        if (!CommonUtils.isEmpty( response )) {
            final Gson gson = new Gson();
            Type collectionType = new TypeToken<LoginDetails>() {
            }.getType();
            loginDetails = gson.fromJson( response, collectionType );
            return loginDetails;
        }
        return null;
    }

    public String getIdProofDetailsList() {
        return sharedPref.getString(IDPROOF, null);
    }

    public void storeIdProofDetailsList(String image) {
        try {
            editor.putString(IDPROOF, image);
            editor.commit();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getDriverDetails() {
        return sharedPref.getString(DRIVING, null);
    }

    public void storeDriverDetails(String login) {
        try {
            editor.putString(DRIVING, login);
            editor.commit();
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public DriverDetails getDriverDetailsModel(){
        DriverDetails driverDetails;
        String response=getDriverDetails();
        if (!CommonUtils.isEmpty( response )) {
            final Gson gson = new Gson();
            Type collectionType = new TypeToken<DriverDetails>() {
            }.getType();
            driverDetails = gson.fromJson( response, collectionType );
            return driverDetails;
        }
        return null;
    }

}
