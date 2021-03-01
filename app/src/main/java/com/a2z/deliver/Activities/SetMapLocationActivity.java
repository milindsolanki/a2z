package com.a2z.deliver.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a2z.deliver.Models.ChooseLocation.AddressDetail;
import com.a2z.deliver.Models.ChooseLocation.GoogleAddress;
import com.a2z.deliver.R;
import com.a2z.deliver.Utils.CommonUtils;
import com.a2z.deliver.Views.CustomTextview;
import com.a2z.deliver.WebService.API_Params;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetMapLocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @BindView(R.id.iv_home_filter)
    ImageView ivHomeFilter;
    @BindView(R.id.tv_header_text)
    CustomTextview tvHeaderText;
    @BindView(R.id.iv_home_map)
    ImageView ivHomeMap;
    @BindView(R.id.appbar_map)
    AppBarLayout appbarMap;
    @BindView(R.id.ivMapMarker)
    ImageView ivMapMarker;
    @BindView(R.id.tvSearch)
    CustomTextview tvSearch;
    @BindView(R.id.cv_searchbar)
    CardView cvSearchbar;
    @BindView(R.id.cardview_map)
    LinearLayout cardviewMap;
    @BindView(R.id.tvAddress)
    CustomTextview tvAddress;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;


    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private String TAG = SetMapLocationActivity.class.toString( );
    int REQ_ADDRESS = 123;
    String type;
    AddressDetail addressList;
    LatLng latLng1;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation = null;
    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    String addresstype="";
    String mCity, mCountry, mFullAddress, mState, mPlaceId, mPlaceName, mPostalCode;
    String acLocality, acSubLocality, acSubAdminArea, acAdminArea;
    private double mLattitude;
    private double mLongitude;
    boolean enabled = false;
    Activity activity;
    String fromScreen = "";
    String isedit="";
    String addressID="";
    public static final int REQUEST_PICKUP_ADDRESS = 101;
    public static final int REQUEST_DELIVERY_ADDRESS = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_set_map_location );
        ButterKnife.bind( this );
        activity = this;
        tvHeaderText.setText( R.string.setlocation );
        ivHomeFilter.setImageResource( R.drawable.ic_back_button );
        ivHomeMap.setImageResource( R.drawable.ic_checkmark );

        addresstype=getIntent( ).getExtras( ).getString( API_Params.ADDRESS_TYPE);
        fromScreen = getIntent( ).getExtras( ).getString( API_Params.FROM_SCREEN );
        isedit = getIntent( ).getExtras( ).getString( API_Params.IS_EDIT );
        addressID=getIntent( ).getExtras( ).getString( API_Params.ADDRESS_ID );
        Toast.makeText( this,isedit+addresstype+addressID,Toast.LENGTH_LONG ).show();

        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager( ).findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        configureCameraIdle( );
        GooglePlacesAutocompleteAdapter googlePlacesAutocompleteAdapter1 = new GooglePlacesAutocompleteAdapter( activity, R.layout.list_places_item );
        autoCompleteTextView.setAdapter( googlePlacesAutocompleteAdapter1 );
        Typeface typeface = Typeface.createFromAsset( getAssets( ), getResources( ).getString( R.string.font ) );
        autoCompleteTextView.setTypeface( typeface );
    }

    @OnClick(R.id.tvSearch)
    public void onSearchClick() {
        tvSearch.setVisibility( View.GONE );
        autoCompleteTextView.setVisibility( View.VISIBLE );
    }

    @OnClick(R.id.iv_home_map)
    public void onSaveAddressClick() {
        mFullAddress = tvAddress.getText( ).toString( );

        if (mFullAddress.equalsIgnoreCase( getResources( ).getString( R.string.map_drag_info_two ) )) {

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

            Intent intent = new Intent( SetMapLocationActivity.this, EditLocationActivity.class );

            intent.putExtra( API_Params.RESULT, addressDetail );
            intent.putExtra( API_Params.FROM_SCREEN, fromScreen );
            intent.putExtra( API_Params.ADDRESS_TYPE,addresstype );
            intent.putExtra( API_Params.IS_EDIT,isedit );
            intent.putExtra( API_Params.ADDRESS_ID,addressID);
            intent.putExtra( API_Params.DATA,mFullAddress );

            if (fromScreen.equals( API_Params.PICKUP_SCREEN )) {
                startActivityForResult( intent, REQUEST_PICKUP_ADDRESS );
            } else if (fromScreen.equals( API_Params.DELIVERY_SCREEN )) {
                startActivityForResult( intent, REQUEST_DELIVERY_ADDRESS );
            }
        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder( context );
        List <Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName( strAddress, 5 );
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

    public static ArrayList autocomplete(String input) {
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

    @OnClick(R.id.iv_home_filter)
    public void onViewClicked() {
        finish();
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super( context, textViewResourceId );
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = super.getView( position, convertView, parent );
            final TextView textView = ( TextView ) row.findViewById( R.id.tvPlace );
            textView.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    System.out.println( "Place:- " + textView.getText( ).toString( ) );
                    autoCompleteTextView.setText( textView.getText( ).toString( ).trim( ) );
                    CommonUtils.hideKeyboard( activity );
                    if (autoCompleteTextView.getText( ).toString( ).length( ) != 0) {
                        LatLng latLng = getLocationFromAddress( activity, autoCompleteTextView.getText( ).toString( ).trim( ) );
                        tvAddress.setText( autoCompleteTextView.getText( ).toString( ).trim( ) );
                        tvSearch.setVisibility( View.VISIBLE );
                        autoCompleteTextView.setText( "" );
                        autoCompleteTextView.setVisibility( View.GONE );
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
            } );
            return row;
        }

        @Override
        public int getCount() {
            return resultList.size( );
        }

        @Override
        public String getItem(int index) {
            try {
                return resultList.get( index ).toString( );
            } catch (Exception e) {
                return "";
            }

        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter( ) {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults( );
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete( constraint.toString( ) );

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size( );
                    }
                    if (filterResults != null && filterResults.count > 0) {
                        notifyDataSetChanged( );
                    } else {
                        notifyDataSetInvalidated( );
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged( );
                    } else {
                        //notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

      /*  // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        mMap.getUiSettings( ).setCompassEnabled( true );
        mMap.getUiSettings( ).setZoomControlsEnabled( true );
        mMap.getUiSettings( ).setMyLocationButtonEnabled( true );
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 14.0f ) );
        mMap.setOnCameraIdleListener( onCameraIdleListener );
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder( activity )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build( );
        mGoogleApiClient.connect( );
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest( );
        mLocationRequest.setInterval( 1000 );
        mLocationRequest.setFastestInterval( 1000 );
        mLocationRequest.setPriority( LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY );
        if (ContextCompat.checkSelfPermission( activity,
                Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates( mGoogleApiClient, mLocationRequest, this );
            } catch (Exception e) {
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mLastLocation == null) {
            mLastLocation = location;

            //Place current location marker
            LatLng latLng = new LatLng( location.getLatitude( ), location.getLongitude( ) );
            MarkerOptions markerOptions = new MarkerOptions( );
            markerOptions.position( latLng );
            markerOptions.title( "Current Position" );
            markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_MAGENTA ) );

            //move map camera
            CameraPosition cameraPosition = new CameraPosition.Builder( )
                    .target( latLng ).zoom( 19f ).tilt( 70 ).build( );
            mMap.animateCamera( CameraUpdateFactory
                    .newCameraPosition( cameraPosition ) );
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION )
                    != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions( activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(
                                Manifest.permission.ACCESS_FINE_LOCATION )
                                == PackageManager.PERMISSION_GRANTED) {

                            Log.e( TAG, " Permission allow" );
                            if (mGoogleApiClient == null) {
                                buildGoogleApiClient( );
                                onMapReady( mMap );
                            }
                            if (mLastLocation != null) {
                                LatLng latLng = new LatLng( mLastLocation.getLatitude( ), mLastLocation.getLongitude( ) );
                                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( latLng, 10.0f ) );
                            }
                            mMap.setMyLocationEnabled( true );
                        }
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText( activity, "permission denied", Toast.LENGTH_LONG ).show( );
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private boolean checkPlayServices() {
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

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onResume() {
        super.onResume( );
        activity.getWindow( ).setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN );
        if (checkPlayServices( )) {
            LocationManager service = ( LocationManager ) activity.getSystemService( LOCATION_SERVICE );
            enabled = service.isProviderEnabled( LocationManager.GPS_PROVIDER );
            if (!enabled) {

                AlertDialog.Builder dialog = new AlertDialog.Builder( activity );
                dialog.setMessage( "Location not enabled!" );
                dialog.setPositiveButton( "Open location settings", new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        startActivity( myIntent );
                    }
                } );
                dialog.setNegativeButton( "Cancel", new DialogInterface.OnClickListener( ) {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                    }
                } );
                dialog.show( );
           /* Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);*/
                return;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION )
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient( );
                // mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission( );
            }
        } else {
            buildGoogleApiClient( );
            //   mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause( );

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates( mGoogleApiClient, this );
        }
    }

    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener( ) {
            @Override
            public void onCameraIdle() {
                setResultText( );
            }
        };
    }

    public void setResultText() {
        final LatLng latLng = mMap.getCameraPosition( ).target;
        final Geocoder geocoder = new Geocoder( activity );
        mLattitude = latLng.latitude;
        mLongitude = latLng.longitude;
        new Thread( new Runnable( ) {

            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @RequiresApi(api = Build.VERSION_CODES.DONUT)
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

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                            if (!locality.isEmpty( ) && !country.isEmpty( )) {
                                Log.e( TAG, locality + "  " + country );
                                activity.runOnUiThread( new Runnable( ) {

                                    @Override
                                    public void run() {
                                        autoCompleteTextView.setVisibility( View.GONE );
                                        autoCompleteTextView.setText( "" );
                                        tvSearch.setVisibility( View.VISIBLE );
                                        tvAddress.setText( locality + " " + country );
                                    }
                                } );
                                Thread.sleep( 200 );
                            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == API_Params.RESULT_PICKUP_ADDRESS) {
            if (requestCode == REQUEST_PICKUP_ADDRESS) {
                AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                Intent intent = new Intent( );
                intent.putExtra( API_Params.RESULT, addressDetail );
                setResult( API_Params.RESULT_PICKUP_ADDRESS, intent );
                finish( );
            }
        } else if (resultCode == API_Params.RESULT_DELIVERY_ADDRESS) {
            if (requestCode == REQUEST_DELIVERY_ADDRESS) {
                AddressDetail addressDetail = ( AddressDetail ) data.getSerializableExtra( API_Params.RESULT );
                Intent intent = new Intent( );
                intent.putExtra( API_Params.RESULT, addressDetail );
                setResult( API_Params.RESULT_DELIVERY_ADDRESS, intent );
                finish( );
            }
        }
    }


}
