package com.example.goyal.group14;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by goyal on 4/12/2016.
 */
public class readingLastCall {
    CallLogCLS callObj;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

 /*   public  readingLastCall(){
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CALL_LOG);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_CALL_LOG},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("4444444=", "3333");
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    getCallDetails();
                } else {
                    // Permission Denied
                   // Toast.makeText(readingLastCall.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                        //    .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private List<CallLogCLS> getCallDetails() {



        Log.d("###","we are good");
        List<CallLogCLS> CallLogList = new ArrayList<>();
        callObj = new CallLogCLS();
        callObj.setContactno("phNumber");
        callObj.setTmDate("callDate");
        callObj.setType("dir");
        CallLogList.add(callObj);



        StringBuffer sb = new StringBuffer();
        try (Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null)) {

            Log.d("###", "1111111111111");
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            Log.d("###", "22222222222222222");
            sb.append("Call Log :");
            while (managedCursor.moveToNext()) {
                callObj = new CallLogCLS();
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String dir = null;


                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }

                callObj.setContactno(phNumber);
                callObj.setTmDate(callDate);
                callObj.setType(dir);

                CallLogList.add(callObj);
                sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                        + dir + " \nCall Date:--- " + callDayTime
                        + " \nCall duration in sec :--- " + callDuration);
                sb.append("\n----------------------------------");


                Log.d("###", "333333333333333");

                Log.d("@@@@@@@@@@@@@@@@@@@", phNumber + "--" + callDayTime + "--" + callDuration + "---" + dir);
                Log.d("###", "5555555555555");
            }
        }

        return CallLogList;
        //Read more: http://www.androidhub4you.com/2013/09/android-call-history-example-how-to-get.html#ixzz454YIk13A

    }
*/

}
