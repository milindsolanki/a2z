package com.a2z.deliver.activities.setMapLocation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.databinding.ActivitySetMapLocationBinding;
import com.a2z.deliver.models.chooseLocation.AddressDetail;
import com.a2z.deliver.R;
import com.a2z.deliver.webService.API_Params;
import com.google.android.gms.common.ConnectionResult;
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

public class SetMapLocationActivity extends BaseApp implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener, SetMapLocationView {

    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private String TAG = SetMapLocationActivity.class.getSimpleName();
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation = null;

    String addresstype = "";
    boolean enabled = false;
    Activity activity;
    String fromScreen = "";
    String isedit = "";
    String addressID = "";
    public static final int REQUEST_PICKUP_ADDRESS = 101;
    public static final int REQUEST_DELIVERY_ADDRESS = 102;
    ActivitySetMapLocationBinding binding;
    SetMapLocationPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //setContentView( R.layout.activity_set_map_location );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_set_map_location );
        binding.setClickListener( this );
        binding.layoutHeader.setClickListener( this );
        init();
    }

    private void init() {

        activity = this;
        presenter = new SetMapLocationPresenter( activity, binding );

        configureCameraIdle();
        binding.layoutHeader.tvHeaderText.setText( R.string.setlocation );
        binding.layoutHeader.ivHomeFilter.setImageResource( R.drawable.ic_back_button );
        binding.layoutHeader.ivHomeMap.setImageResource( R.drawable.ic_checkmark );

        addresstype = getIntent().getExtras().getString( API_Params.ADDRESS_TYPE );
        fromScreen = getIntent().getExtras().getString( API_Params.FROM_SCREEN );
        isedit = getIntent().getExtras().getString( API_Params.IS_EDIT );
        addressID = getIntent().getExtras().getString( API_Params.ADDRESS_ID );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        GooglePlacesAutocompleteAdapter googlePlacesAutocompleteAdapter1 = new GooglePlacesAutocompleteAdapter( activity, R.layout.list_places_item, this );
        binding.autoCompleteTextView.setAdapter( googlePlacesAutocompleteAdapter1 );
        Typeface typeface = Typeface.createFromAsset( getAssets(), getResources().getString( R.string.font ) );
        binding.autoCompleteTextView.setTypeface( typeface );
    }

    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                presenter.setResultText( mMap );
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvSearch) {
            binding.tvSearch.setVisibility( View.GONE );
            binding.autoCompleteTextView.setVisibility( View.VISIBLE );
        } else if (v == binding.layoutHeader.ivHomeMap) {
            presenter.onSaveAddressClick( fromScreen, addresstype, isedit, addressID );
        } else if (v == binding.layoutHeader.ivHomeFilter) {
            finish();
        }
    }

    @Override
    public void onGooglePlacesClick(String place) {
        presenter.onGooglePlaceClick( mMap, place );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      /*  // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        mMap.getUiSettings().setCompassEnabled( true );
        mMap.getUiSettings().setZoomControlsEnabled( true );
        mMap.getUiSettings().setMyLocationButtonEnabled( true );
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 14.0f ) );
        mMap.setOnCameraIdleListener( onCameraIdleListener );
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder( activity )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
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
            LatLng latLng = new LatLng( location.getLatitude(), location.getLongitude() );
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position( latLng );
            markerOptions.title( "Current Position" );
            markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_MAGENTA ) );

            //move map camera
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target( latLng ).zoom( 19f ).tilt( 70 ).build();
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
                                buildGoogleApiClient();
                                onMapReady( mMap );
                            }
                            if (mLastLocation != null) {
                                LatLng latLng = new LatLng( mLastLocation.getLatitude(), mLastLocation.getLongitude() );
                                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( latLng, 10.0f ) );
                            }
                            mMap.setMyLocationEnabled( true );
                        }
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText( activity, "permission denied", Toast.LENGTH_LONG ).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN );
        if (presenter.checkPlayServices()) {
            LocationManager service = (LocationManager) activity.getSystemService( LOCATION_SERVICE );
            enabled = service.isProviderEnabled( LocationManager.GPS_PROVIDER );
            if (!enabled) {
                presenter.enableLocationDialog();
                return;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION )
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                // mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            //   mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates( mGoogleApiClient, this );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == API_Params.RESULT_PICKUP_ADDRESS) {
            if (requestCode == REQUEST_PICKUP_ADDRESS) {
                AddressDetail addressDetail = (AddressDetail) data.getSerializableExtra( API_Params.RESULT );
                Intent intent = new Intent();
                intent.putExtra( API_Params.RESULT, addressDetail );
                intent.putExtra( API_Params.IS_EDIT, isedit );
                setResult( API_Params.RESULT_PICKUP_ADDRESS, intent );
                finish();
            }
        } else if (resultCode == API_Params.RESULT_DELIVERY_ADDRESS) {
            if (requestCode == REQUEST_DELIVERY_ADDRESS) {
                AddressDetail addressDetail = (AddressDetail) data.getSerializableExtra( API_Params.RESULT );
                Intent intent = new Intent();
                intent.putExtra( API_Params.RESULT, addressDetail );
                intent.putExtra( API_Params.IS_EDIT, isedit );
                setResult( API_Params.RESULT_DELIVERY_ADDRESS, intent );
                finish();
            }
        }
    }
}
