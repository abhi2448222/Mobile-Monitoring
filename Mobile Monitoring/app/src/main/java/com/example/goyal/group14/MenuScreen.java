package com.example.goyal.group14;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageApi;

public class MenuScreen extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private MyReceiver receiverClass = null;
    static  int i =0;
    Button start;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menu");
        setContentView(R.layout.activity_menu_screen);
        //start = (Button)findViewById(R.id.service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int hasACCESS_FINE_LOCATIONPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasACCESS_FINE_LOCATIONPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
        int hasACCESS_COARSE_LOCATIONPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasACCESS_COARSE_LOCATIONPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }

        /*start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                receiverClass = new MyReceiver();
                // receiverClass.setMainActivityHandler(this);
                IntentFilter callInterceptorIntentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
                registerReceiver(receiverClass,  callInterceptorIntentFilter);


                Log.e("Calling receiver", "Calling from startServiceNow");
                Toast.makeText(getApplicationContext(), "Starting the broadcast service",Toast.LENGTH_SHORT).show();
                //startService(new Intent(this, MyService.class));
            }
        });*/





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(MenuScreen.this, "Permissions Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void gpsActivity(View view) {
       Intent intent = new Intent(this, Screen1.class);
       startActivity(intent);
    }
    public void messageActivity(View view) {
        Intent intent = new Intent(this, MessageAPI.class);
        startActivity(intent);
        receiverClass = new MyReceiver();
        IntentFilter callInterceptorIntentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiverClass, callInterceptorIntentFilter);
        if(i==0)
            Toast.makeText(getApplicationContext(), "Starting the broadcast service",Toast.LENGTH_SHORT).show();
            i++;

    }
    public void callActivity(View view) {
          Intent intent = new Intent(this, CallMenu.class);
          startActivity(intent);
    }

}
