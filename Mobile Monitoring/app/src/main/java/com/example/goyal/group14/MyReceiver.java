package com.example.goyal.group14;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
        Log.e("In","receiver constructor");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Toast.makeText(context, "Test message",Toast.LENGTH_SHORT).show();
        // an Intent broadcast.

        Log.e("in receiver", "Calling from receiver class outside action if-clause");
        String action = intent.getAction();

        Log.e("in receiver", "Calling from receiver class after getting intent action");

        if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {

            Log.e("in receiver", "Calling from receiver class inside action if-clause");
            Toast.makeText(context, "Message Received",Toast.LENGTH_SHORT).show();

                }
            }
        }

