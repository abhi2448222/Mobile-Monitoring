package com.example.goyal.group14;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationTracker extends Service implements LocationListener {

    private Context mContext;
    Geocoder geocoder;
    DBLocation db;

    // flag for GPS status
    public boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 30; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000*60*3 ; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    //public Screen1 screen1 =new Screen1();
    @Override
    public void onCreate() {
        Log.d("herein", "onCreate: ");
        // getLocation();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public LocationTracker(Context context) {
        this.mContext = context;
        Log.d("hereman","50");

        db=new DBLocation(context.getApplicationContext());
        getLocation();
    }

    public LocationTracker(){}


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("Got LOCLAT ONCHNGED", String.valueOf(latitude));
                Log.d("Got LOCLONG ONCHNGW", String.valueOf(longitude));


                //  List<Address> addresses;
                //  geocoder = new Geocoder(mContext, Locale.getDefault());
//                try {
//                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                    String city = addresses.get(0).getLocality();
//                    String state = addresses.get(0).getAdminArea();
//                    String postalCode = addresses.get(0).getPostalCode();
//
//                    Log.d("Address", address);
//                    Log.d("CITY", city);
//                    Log.d("STATE", state);
//                    Log.d("postalCode", postalCode);
//
//                    //Insert DATA IF NEW
//
//                    db.insertLocations(address,city,state,postalCode,latitude,longitude);
                Log.d("Inserting lat long","50");
                db.insertLocations(latitude,longitude);
//
//                    StringBuffer buff = new StringBuffer();
//                    buff.append("ADdress"+address+"\n");
//                    buff.append("CITY"+city+"\n");
//                    buff.append("State"+state+"\n");
//                    buff.append("postalCode"+postalCode+"\n");
//
//
//
//                    Log.d( "buff",String.valueOf(buff));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void getLocation() {
        try {
            Log.d("here","here");
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.v("isGPSEnabled", "=" + isGPSEnabled);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);

            if (isGPSEnabled == false && isNetworkEnabled == false) {
                // no network provider is enabled'"
                Log.d("NO NETWORK OR GPS", "NO Network OR GPS");


                //Activity activity=new Activity();
                //activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    location = null;
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d("Got LOCATION LATITFRmNW", String.valueOf(latitude));
                            Log.d("Got LOCATION LONGIFRmNW", String.valueOf(longitude));
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    location = null;
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
//                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                // TODO: Consider calling
//                                //    ActivityCompat#requestPermissions
//                                // here to request the missing permissions, and then overriding
//                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                //                                          int[] grantResults)
//                                // to handle the case where the user grants the permission. See the documentation
//                                // for ActivityCompat#requestPermissions for more details.
//                                Log.v("NO PERMISIOONS" ,"NO PERMISIOONS");
//                                return;
//                            }
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.d("Got LOCATION LATIT", String.valueOf(latitude));
                                Log.d("Got LOCATION LONGI", String.valueOf(longitude));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.d("Exception", String.valueOf(e));
            e.printStackTrace();
        }

        //return location;
    }



    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }






    //   @Override
    //    public void onDestroy(){
    //        Log.v("SERVICE", "Service killed");
    //        if(locationManager != null) {
    //
    //            locationManager.removeUpdates(mLocationListener);
    //            super.onDestroy();
    //        }
    //  }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}