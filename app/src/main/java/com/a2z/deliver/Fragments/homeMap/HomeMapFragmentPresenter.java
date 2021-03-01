package com.a2z.deliver.fragments.homeMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.a2z.deliver.databinding.FragmentHomeMapBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class HomeMapFragmentPresenter {
    Activity activity;
    FragmentHomeMapBinding binding;

    public HomeMapFragmentPresenter(Activity activity, FragmentHomeMapBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable( activity );
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError( resultCode )) {
                GooglePlayServicesUtil.getErrorDialog( resultCode, activity,
                        9000 ).show( );
            } else {
                //finish();
            }
            return false;
        }
        return true;
    }

    public void enableLocationDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder( activity );
        dialog.setMessage( "Location not enabled!" );
        dialog.setPositiveButton( "Open location settings", new DialogInterface.OnClickListener( ) {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                activity.startActivity( myIntent );
            }
        } );
        dialog.setNegativeButton( "Cancel", new DialogInterface.OnClickListener( ) {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
            }
        } );
        dialog.show( );
    }

}
