package com.example.goyal.group14;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Collections;
public class DisplayRecentLocations extends AppCompatActivity {

    DBLocation db;
    Geocoder geocoder;
    double latitude; // latitude
    double longitude; // longitude
    boolean firstTimeDataEntry=false;

    public static ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recent_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Recent Locations");
        dispLocations();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void dispLocations() {

        if(isNetworkAvailable()){
           // Toast.makeText(this,"Internet_connection Available",Toast.LENGTH_LONG).show();

            List<String> locations = new ArrayList();

            String oldAddress = "";
            db = new DBLocation(this);
            Cursor res = db.displayLocations();

            if (res.getCount() == 0) {

                //display a message
                Toast.makeText(this,"No recent locations stored",Toast.LENGTH_LONG).show();

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

                        locations.add(buff);
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

                            locations.add(buff);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();


                    }
                }

            }
            Log.d("outside while","50");
            //reversing listview--not tested
            Log.d("reversing locations","50");
            Collections.reverse(locations);

            listView=(ListView)findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.locations , locations);
            Log.d("Display ong","50");
            listView.setAdapter(adapter);
        }
        else {
            Log.d("No internet","50");
            Toast.makeText(this,"Please make sure your internet connection is available for this service",Toast.LENGTH_LONG).show();
        }

    }

    public void clearLocations(View view){
        Cursor res = db.displayLocations();

        if (res.getCount() == 0) {

            //display a message
            Log.d("no locations stored","50");
            Toast.makeText(this,"No recent locations stored",Toast.LENGTH_LONG).show();

            return;
        }

        else{
            Log.d("Calling delete data","50");
            db.deleteAllData();
            Log.d("Delete done", "50");
            Toast.makeText(this,"Recent locations are reset succesfully",Toast.LENGTH_LONG).show();

        }
    }

    public void showOnmap(View view){
        Log.d("showing on map calling","50");
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}