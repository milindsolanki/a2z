package com.a2z.deliver.activities.setMapLocation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.a2z.deliver.R;
import com.a2z.deliver.activities.editLocation.EditLocationActivity;
import com.a2z.deliver.databinding.ActivitySetMapLocationBinding;
import com.a2z.deliver.models.chooseLocation.AddressDetail;
import com.a2z.deliver.models.chooseLocation.GoogleAddress;
import com.a2z.deliver.utils.CommonUtils;
import com.a2z.deliver.webService.API_Params;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SetMapLocationPresenter {

    private Activity activity;
    ActivitySetMapLocationBinding binding;
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    String mCity, mCountry, mFullAddress, mState, mPlaceId, mPlaceName, mPostalCode;
    String acLocality, acSubLocality, acSubAdminArea, acAdminArea;
    private double mLattitude;
    private double mLongitude;
    private String TAG = SetMapLocationPresenter.class.getSimpleName();
    LatLng latLng1;

    public SetMapLocationPresenter() {

    }

    public SetMapLocationPresenter(Activity activity, ActivitySetMapLocationBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    public void onGooglePlaceClick(GoogleMap mMap, String place) {
        binding.autoCompleteTextView.setText( place );
        CommonUtils.hideKeyboard( activity );
        if (binding.autoCompleteTextView.getText( ).toString( ).length( ) != 0) {
            LatLng latLng = getLocationFromAddress( mMap );
            binding.tvAddress.setText( binding.autoCompleteTextView.getText( ).toString( ).trim( ) );
            binding.tvSearch.setVisibility( View.VISIBLE );
            binding.autoCompleteTextView.setText( "" );
            binding.autoCompleteTextView.setVisibility( View.GONE );
            if (latLng != null)
            //    getLocation(latLng);
            {
                CameraPosition cameraPosition = new CameraPosition.Builder( )
                        .target( latLng ).zoom( 19f ).tilt( 70 ).build( );
                mMap.animateCamera( CameraUpdateFactory
                        .newCameraPosition( cameraPosition ) );
            } else
                Toast.makeText( activity, "Location not found.", Toast.LENGTH_SHORT ).show( );
        }
    }

    public ArrayList autocomplete(String input) {
        ArrayList resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder( );
        try {
            String sb = PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON + "?key=" + API_Params.googleplaceapi_key +
                    "&types=geocode" +
                    // "&components=country:us" +
                    "&input=" + URLEncoder.encode( input, "utf8" );

            Log.e( "TAG Place", sb );
            URL url = new URL( sb );
            conn = ( HttpURLConnection ) url.openConnection( );
            InputStreamReader in = new InputStreamReader( conn.getInputStream( ) );

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read( buff )) != -1) {
                jsonResults.append( buff, 0, read );
            }
        } catch (MalformedURLException e) {
            return resultList;
        } catch (IOException e) {
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect( );
            }
        }

        try {
            JSONObject jsonObj = new JSONObject( jsonResults.toString( ) );
            Log.e( "tag", "json : " + jsonObj.toString( ) );
            JSONArray predsJsonArray = jsonObj.getJSONArray( "predictions" );
            resultList = new ArrayList( predsJsonArray.length( ) );
            for (int i = 0; i < predsJsonArray.length( ); i++) {
                resultList.add( predsJsonArray.getJSONObject( i ).getString( "description" ) );
            }
        } catch (JSONException e) {
            Log.e( "log_tag", "Error : " + e.getMessage( ) );
            //Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }


    public void setResultText(GoogleMap mMap) {
        Log.e(TAG, "set Result");
        final LatLng latLng = mMap.getCameraPosition( ).target;
        final Geocoder geocoder = new Geocoder( activity );
        mLattitude = latLng.latitude;
        mLongitude = latLng.longitude;
        new Thread( new Runnable( ) {

            @Override
            public void run() {

                try {
                    latLng1 = new LatLng( latLng.latitude, latLng.longitude );
                    List <Address> addressList = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 );
                    if (addressList != null && addressList.size( ) > 0) {
                        final String locality = addressList.get( 0 ).getAddressLine( 0 );
                        final String country = addressList.get( 0 ).getCountryName( );

                        mCity = addressList.get( 0 ).getLocality( );
                        mState = addressList.get( 0 ).getAdminArea( );
                        mCountry = addressList.get( 0 ).getCountryName( );
                        mPlaceName = addressList.get( 0 ).getFeatureName( );
                        mPostalCode = addressList.get( 0 ).getPostalCode( );


                        acLocality = addressList.get( 0 ).getLocality( );
                        acSubLocality = addressList.get( 0 ).getSubLocality( );
                        acAdminArea = addressList.get( 0 ).getAdminArea( );
                        acSubAdminArea = addressList.get( 0 ).getSubAdminArea( );

                        if (!locality.isEmpty( ) && !country.isEmpty( )) {
                            Log.e( TAG, locality + "  " + country );
                            activity.runOnUiThread( new Runnable( ) {

                                @Override
                                public void run() {
                                    binding.autoCompleteTextView.setVisibility( View.GONE );
                                    binding.autoCompleteTextView.setText( "" );
                                    binding.tvSearch.setVisibility( View.VISIBLE );
                                    binding.tvAddress.setText( locality + " " + country );
                                }
                            } );
                            Thread.sleep( 200 );
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace( );
                } catch (InterruptedException e) {
                    e.printStackTrace( );
                }
            }
        } ).start( );
    }

    public void onSaveAddressClick(String fromScreen, String addresstype, String isEdit, String addressID) {
        mFullAddress = binding.tvAddress.getText( ).toString( );

        if (mFullAddress.equalsIgnoreCase( activity.getResources( ).getString( R.string.map_drag_info_two ) )) {

        } else {
            AddressDetail addressDetail = new AddressDetail( );
            GoogleAddress googleAddressBean = new GoogleAddress( );

            Log.e( "city", "-" + mCity );
            Log.e( "state", "-" + mState );
            Log.e( "country", "-" + mCountry );
            Log.e( "PlaceId", "-" + mPlaceId );
            Log.e( "PlaceName", "-" + mPlaceName );
            Log.e( "PostalCode", "-" + mPostalCode );
            Log.e( "FullAddress", "-" + mFullAddress );


            if (!CommonUtils.isEmpty( mFullAddress )) {
                googleAddressBean.setFullAddress( mFullAddress );
            } else {
                googleAddressBean.setFullAddress( "" );
            }

            if (!CommonUtils.isEmpty( mPlaceId )) {
                googleAddressBean.setPlaceId( mPlaceId );
            } else {
                googleAddressBean.setPlaceId( "" );
            }

            if (!CommonUtils.isEmpty( mCountry )) {
                googleAddressBean.setCountry( mCountry );
            } else {
                googleAddressBean.setCountry( "" );
            }

            if (!CommonUtils.isEmpty( mState )) {
                googleAddressBean.setState( mState );
            } else {
                googleAddressBean.setState( "" );
            }

            if (!CommonUtils.isEmpty( mCity )) {
                googleAddressBean.setCity( mCity );
            } else {
                googleAddressBean.setCity( "" );
            }

            if (!CommonUtils.isEmpty(acLocality)) {
                googleAddressBean.setAcLocality(acLocality);
            } else {
                googleAddressBean.setAcLocality("");
            }
            if (!CommonUtils.isEmpty(acSubLocality)) {
                googleAddressBean.setAcSubLocality(acSubLocality);
            } else {
                googleAddressBean.setAcSubLocality("");
            }
            if (!CommonUtils.isEmpty(acAdminArea)) {
                googleAddressBean.setAcAdminArea(acAdminArea);
            } else {
                googleAddressBean.setAcAdminArea("");
            }

            if (!CommonUtils.isEmpty(acSubAdminArea)) {
                googleAddressBean.setAcSubAdminArea(acSubAdminArea);
            } else {
                googleAddressBean.setAcSubAdminArea("");
            }

            if (!CommonUtils.isEmpty( mPlaceName )) {
                googleAddressBean.setPlaceName( mPlaceName );
            } else {
                googleAddressBean.setPlaceName( "" );
            }

            if (!CommonUtils.isEmpty( String.valueOf( mLattitude ) )) {
                addressDetail.setLat( String.valueOf( mLattitude ) );
            } else {
                addressDetail.setLat( "" );
            }

            if (!CommonUtils.isEmpty( String.valueOf( mLongitude ) )) {
                addressDetail.setLong( String.valueOf( mLongitude ) );
            } else {
                addressDetail.setLong( "" );
            }
            addressDetail.setGoogleAddress( googleAddressBean );

            Intent intent = new Intent( activity, EditLocationActivity.class );

            intent.putExtra( API_Params.RESULT, addressDetail );
            intent.putExtra( API_Params.FROM_SCREEN, fromScreen );
            intent.putExtra( API_Params.ADDRESS_TYPE,addresstype );
            intent.putExtra( API_Params.IS_EDIT, isEdit );
            intent.putExtra( API_Params.ADDRESS_ID,addressID);
            intent.putExtra( API_Params.DATA,mFullAddress );

            if (fromScreen.equals( API_Params.PICKUP_SCREEN )) {
                activity.startActivityForResult( intent, SetMapLocationActivity.REQUEST_PICKUP_ADDRESS );
            } else if (fromScreen.equals( API_Params.DELIVERY_SCREEN )) {
                activity.startActivityForResult( intent, SetMapLocationActivity.REQUEST_DELIVERY_ADDRESS );
            }
        }
    }

    public LatLng getLocationFromAddress(GoogleMap mMap) {
        Geocoder coder = new Geocoder( activity );
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName( binding.autoCompleteTextView.getText( ).toString( ).trim( ), 5 );
            if (address == null) {
                return null;
            }
            if (address.size( ) > 0) {
                Address location = address.get( 0 );
                location.getLatitude( );
                location.getLongitude( );

                p1 = new LatLng( location.getLatitude( ), location.getLongitude( ) );
                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( p1, 10.0f ) );
            }


        } catch (IOException ex) {

            ex.printStackTrace( );
        }

        return p1;
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
}
