package com.a2z.deliver.fragments.homeMap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.a2z.deliver.BaseFragmentApp;
import com.a2z.deliver.R;
import com.a2z.deliver.databinding.FragmentHomeMapBinding;
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

public class HomeMapFragment extends BaseFragmentApp implements LocationListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {
    FragmentHomeMapBinding binding;
    HomeMapFragmentPresenter presenter;
    private GoogleMap mMap;
    private Location mLocation;
    boolean enabled = false;
    Activity activity;
    GoogleApiClient mGoogleApiClient;
    LocationRequest locationRequest;
    String[] locationPermissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private final int REQUEST_PERMISSION_LOCATION = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_home_map, container, false );
        View view = binding.getRoot();
        return view;

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        init();
//        permissionCheck();

    }

    public void init() {
        presenter = new HomeMapFragmentPresenter( getActivity(), binding );
        SupportMapFragment mapFragment = ( SupportMapFragment ) this.getChildFragmentManager().findFragmentById( R.id.homeMap );
        mapFragment.getMapAsync( this );
//        SupportMapFragment supportMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById( R.id.homeMap );
    }

    private void permissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText( getActivity(),"Permissions Granted!", Toast.LENGTH_SHORT ).show();
            } else {
                ActivityCompat.requestPermissions( getActivity(), locationPermissions, REQUEST_PERMISSION_LOCATION);
            }
        } else {
            Toast.makeText( getActivity(),"Permissions Granted Automatically!!", Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
//        mMap = map;
//        map.setMyLocationEnabled( true );

        CameraPosition position = CameraPosition.builder()
                .target( new LatLng( 22.69337, 71.58304 ) )
                .zoom( 5 )
                .bearing( 0 )
                .tilt( 0 )
                .build();
        map.setMapType( GoogleMap.MAP_TYPE_NORMAL );
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled( true );
        map.getUiSettings().setZoomControlsEnabled(true);
        map.animateCamera( CameraUpdateFactory.newCameraPosition( position ),1000, null );
        map.moveCamera( CameraUpdateFactory.newCameraPosition( position ) );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == REQUEST_PERMISSION_LOCATION){
            if (grantResults != null && grantResults.length > 0){
                boolean granted = true;
                for (int i = 0; i < grantResults.length; i ++){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        granted = false;
                        break;
                    }
                    if (granted){
                        Toast.makeText( getActivity(), "Access Granted!", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter.checkPlayServices()){
            LocationManager service = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );
            enabled = service.isProviderEnabled( LocationManager.GPS_PROVIDER );
            if (!enabled){
                presenter.enableLocationDialog();
                return;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION )
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
//                buildGoogleApiClient();
//                 mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                permissionCheck();
            }
        } else {
//            buildGoogleApiClient();
//               mMap.setMyLocationEnabled(true);
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
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval( 1000 );
        locationRequest.setFastestInterval( 1000 );
        locationRequest.setPriority( LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY );
        if (ContextCompat.checkSelfPermission( activity,
                Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates( mGoogleApiClient, locationRequest, this );
            } catch (Exception e) {
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (mLocation == null) {
            mLocation = location;

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
}
