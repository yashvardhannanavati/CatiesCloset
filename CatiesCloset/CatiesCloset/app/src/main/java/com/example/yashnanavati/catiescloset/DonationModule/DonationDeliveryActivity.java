package com.example.yashnanavati.catiescloset.DonationModule;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.yashnanavati.catiescloset.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DonationDeliveryActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donation_delivery);
//    }

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    LocationManager locationManager;
    android.location.LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (googleServicesAvailable()) {
            Toast.makeText(this, "Perfect!!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_donation_delivery);
            initMap();
        } else {
            // No Google Maps Layout
            Toast.makeText(this, "Nope not Perfect!!!", Toast.LENGTH_LONG).show();

        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }


    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        LatLng center = new LatLng(42.664376, -71.323779);
        mGoogleMap.addMarker(new MarkerOptions().position(center).icon(bitmapDescriptor).title("Donation Center"));
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(center, 8);
        mGoogleMap.animateCamera(update);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                double lat1 = location.getLatitude();
                double lng1 = location.getLongitude();
                //goToLocationZoom(lat1, lng1, 3);
                mGoogleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        /******************************************/
        try {
            LocationRequest mNewLocationRequest = new LocationRequest();
            mNewLocationRequest.setInterval(10);
            mNewLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mNewLocationRequest.setFastestInterval(10);
            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
            LocationCallback mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    System.out.println(" *** " + locationResult.getLastLocation());
                    System.out.println(" *** " + DateFormat.getTimeInstance().format(new Date()));
                    try {
                        LatLng myLoc = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                        mGoogleMap.addMarker(new MarkerOptions().position(myLoc).title("User Location"));
                        CameraUpdate newupdate = CameraUpdateFactory.newLatLngZoom(myLoc, 10);
                        mGoogleMap.animateCamera(newupdate);
                        System.out.println(" *** " + new Geocoder(getApplicationContext()).getFromLocation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude(), 1).get(0));
                    } catch (SecurityException | IOException ex) {
                        ex.printStackTrace();
                    }
                    super.onLocationResult(locationResult);
                }
            };
            System.out.println(" *** Calling Fused Client...");
            mFusedLocationClient.requestLocationUpdates(mNewLocationRequest,
                    mLocationCallback, Looper.myLooper());
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        /******************************************/

//        if (Build.VERSION.SDK_INT < 23) {
//
//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//
//        } else {
//
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//
//            } else {
//
////                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
////                Location lastKnownLocation = locationManager.requestLocationUpdates(
////                        LocationManager.NETWORK_PROVIDER,
////                        1000,
////                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
////                        //locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////
////                if (lastKnownLocation != null) {
////                    double longitude = lastKnownLocation.getLongitude();
////                    double latitude = lastKnownLocation.getLatitude();
////                    LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
////
////                    mGoogleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
////                    //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation))
////
////                }
//            }
//
//
//        }


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();


    }

//    Location location;
//
//    public Location getLocation() {
//        try {
//            locationManager = (LocationManager) getApplicationContext()
//                    .getSystemService(LOCATION_SERVICE);
//
//            // getting GPS status
//            boolean isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            // getting network status
//            boolean isNetworkEnabled = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            if (!isGPSEnabled && !isNetworkEnabled) {
//                // no network provider is enabled
//            } else {
//                //this.canGetLocation = true;
//                if (isNetworkEnabled) {
//                    locationManager.requestLocationUpdates(
//                            LocationManager.NETWORK_PROVIDER,
//                            1,
//                            1, locationListener);
//                    Log.d("Network", "Network Enabled");
//                    if (locationManager != null) {
//                        location = locationManager
//                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        if (location != null) {
//                            double latitude = location.getLatitude();
//                            double longitude = location.getLongitude();
//                        }
//                    }
//                }
//                // if GPS Enabled get lat/long using GPS Services
//                if (isGPSEnabled) {
//                    if (location == null) {
//                        locationManager.requestLocationUpdates(
//                                LocationManager.GPS_PROVIDER,
//                                1,
//                                1, locationListener);
//                        Log.d("GPS", "GPS Enabled");
//                        if (locationManager != null) {
//                            location = locationManager
//                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                            if (location != null) {
//                                double latitude = location.getLatitude();
//                                double longitude = location.getLongitude();
//                            }
//                        }
//                    }
//                }
//            }
//
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//
//        return location;
//    }
//
//    private LocationListener locationListener = new LocationListener() {
//        public void onLocationChanged(Location location) {
//            updateMyCurrentLoc(location);
//        }
//
//        public void onProviderDisabled(String provider) {
//            updateMyCurrentLoc(null);
//        }
//
//        public void onProviderEnabled(String provider) {
//        }
//
//        public void onStatusChanged(String provider, int status,
//                                    Bundle extras) {
//        }
//    };

    private void updateMyCurrentLoc(Location location) {


        if (location != null) {

            // other codes to get the address and display
            Toast.makeText(getBaseContext(), "provider used : ", Toast.LENGTH_SHORT).show();   //testing purpose
        } else {
            String str = "No location found";
            Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
        }

    }

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);


    }

    Marker marker;

    public void myClick(View view) throws IOException {
        Intent i = new Intent(getApplicationContext(), PickDonActivity.class);
        startActivity(i);

    }

    public void pickUp(View view) throws IOException {
        Intent i = new Intent(getApplicationContext(), DeliveryActivity.class);
        startActivity(i);

    }


    ArrayList<Marker> markers = new ArrayList<Marker>();
    static final int POLYGON_POINTS = 5;
    Polygon shape;

    private void setMarker(String locality, double lat, double lng) {
        if (markers.size() == POLYGON_POINTS) {
            removeEverything();
        }

        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(new LatLng(lat, lng))
                .snippet("I am Here");

        markers.add(mGoogleMap.addMarker(options));

        if (markers.size() == POLYGON_POINTS) {
            drawPolygon();
        }


    }

    private void drawPolygon() {
        PolygonOptions options = new PolygonOptions()
                .fillColor(0x330000FF)
                .strokeWidth(3)
                .strokeColor(Color.RED);

        for (int i = 0; i < POLYGON_POINTS; i++) {
            options.add(markers.get(i).getPosition());
        }
        shape = mGoogleMap.addPolygon(options);

    }

    private void removeEverything() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
        shape.remove();
        shape = null;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mapTypeNone:
//                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
//                break;
//            case R.id.mapTypeNormal:
//                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                break;
//            case R.id.mapTypeSatellite:
//                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                break;
//            case R.id.mapTypeTerrain:
//                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                break;
//            case R.id.mapTypeHybrid:
//                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                break;
//
//            default:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    LocationRequest mLocationRequest;

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Toast.makeText(this, "Cant get current location", Toast.LENGTH_LONG).show();
        } else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            //CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
            //mGoogleMap.animateCamera(update);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}
