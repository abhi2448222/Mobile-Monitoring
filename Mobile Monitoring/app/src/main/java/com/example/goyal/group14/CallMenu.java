package com.example.goyal.group14;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

public class CallMenu extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Call Menu");
        setContentView(R.layout.activity_call_menu);
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

        int hasWriteContactsPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_CALL_LOG},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }

        int hasSMSWriteContactsPermission = ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);
        if (hasSMSWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }

        int hasContactsWriteContactsPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (hasContactsWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(CallMenu.this, "Permissions Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void previousCallLogsActivity(View view) {
        Intent intent = new Intent(this, CallFirst.class);
        startActivity(intent);
    }

    public void callAnalysis(View view) {
        Intent intent = new Intent(this, CallAnalysisDisplay.class);
        startActivity(intent);
    }

    public void addNotificationContact(View view) {
        Intent intent = new Intent(this, notificationContact.class);
        startActivity(intent);
    }

    public void doCalls(View view)
    {
        Intent intent = new Intent(this, CallLogsScreen.class);
        startActivity(intent);
    }
}
