package com.example.goyal.group14;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity  {

    private GoogleMap mMap;
    DBLocation db;
    Geocoder geocoder;
    double latitude; // latitude
    double longitude; // longitude
    boolean firstTimeDataEntry=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMapdone();
            }
        }
    }

    private void setUpMap() {
        // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // mMap.setMyLocationEnabled(true);
    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }

    public void setUpMapdone(){
        Log.d("setupMapcalled", "50");
        //  mMap.addMarker(new MarkerOptions().position(new LatLng(9.931381, 76.269065)).title("here").draggable(true));
        // LatLng value= new LatLng(9.931381,76.269065);
        //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(value,50));


        List<LatLng> locationsValues = new ArrayList();

        String oldAddress = "";
        db = new DBLocation(this);
        Cursor res = db.displayLocations();

        if (res.getCount() == 0) {

            //display a message
            Toast.makeText(this, "No recent locations stored", Toast.LENGTH_LONG).show();

            return;
        }
        while (res.moveToNext()) {

            String latitude1 = res.getString(1);
            String longitude1 = res.getString(2);
            latitude = Double.parseDouble(latitude1);
            longitude = Double.parseDouble(longitude1);
            Log.d("Lat got in displaying",latitude1);
            Log.d("Long got in displaying",longitude1);

            //retrieve all adress city etc and dispaly in listview
            if (!firstTimeDataEntry) {
                Log.d("First time entry","50");
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String postalCode = addresses.get(0).getPostalCode();

                    String buff = address + "\n" + city + "\n" + state + "\n" + postalCode + "\n";

                    oldAddress = address;

                    Log.d("buff===", buff);

                    //add

                    LatLng value= new LatLng(latitude,longitude);
                    locationsValues.add(value);

                    firstTimeDataEntry = true;


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Exception caiught", String.valueOf(e));


                }
            } else {
                Log.d("Not first element","50");
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String postalCode = addresses.get(0).getPostalCode();

                    if (oldAddress.equals(address)) {
                        Log.d("Same address","skipping loop");
                        continue;
                    } else {
                        String buff = address + "\n" + city + "\n" + state + "\n" + postalCode + "\n";

                        oldAddress = address;
                        Log.d("Second buff","50");
                        Log.d("buff===", buff);

                        //add
                        LatLng value= new LatLng(latitude,longitude);
                        locationsValues.add(value);

                    }
                } catch (IOException e) {
                    e.printStackTrace();


                }
            }

        }
        Log.d("outside while","50");

        Log.d("inside for loop","50");

        for(int i=0;i<locationsValues.size();i++){

            Log.d("adding marker","50");
            mMap.addMarker(new MarkerOptions().position(locationsValues.get(i)).title("here").draggable(true));
            Log.d("moving camera", "50");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationsValues.get(i),15));

        }
        //  mMap.addMarker(new MarkerOptions().position(new LatLng(9.931381, 76.269065)).title("here").draggable(true));
        // LatLng value= new LatLng(9.931381,76.269065);
        //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(value,50));


    }
}
