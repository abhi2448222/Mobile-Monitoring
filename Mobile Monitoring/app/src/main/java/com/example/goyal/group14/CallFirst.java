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
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallFirst extends AppCompatActivity  {

    DBHelper db;
    CallLogCLS callObj;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Previoud Call Records");
        setContentView(R.layout.activity_call_first);
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

        db = new DBHelper(this);
        SharedPreferences CallVariables = getSharedPreferences("CallVariables", Context.MODE_PRIVATE);
        boolean getPreviousValuesFlag = CallVariables.getBoolean("getPreviousValuesFlag", false);
        if(getPreviousValuesFlag){

            TextView txt = (TextView) findViewById(R.id.txt);
            txt.setText("Records already added!");
            TableLayout table_layout = (TableLayout) findViewById(R.id.tb);
            int rows = table_layout.getChildCount();
            table_layout.removeViews(0, rows);
            TableRow rowHeader = new TableRow(this);
            rowHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            ImageView imgHeader = new ImageView(this);
            imgHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            imgHeader.getLayoutParams().height = 120;
            imgHeader.setImageResource(R.drawable.tick);
            rowHeader.addView(imgHeader);
            table_layout.addView(rowHeader);// txt.setText(result);
        }
        else{
            new previousCallRecords().execute("");
        }
    }




    private class previousCallRecords extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            List<CallLogCLS> CallLogList = getCallDetails();
            db.addCallLogValues(CallLogList);
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.txt);
            txt.setText("Executed"); // txt.setText(result);
            TableLayout table_layout = (TableLayout) findViewById(R.id.tb);
            int rows = table_layout.getChildCount();
            table_layout.removeViews(0, rows);
            TableRow rowHeader = new TableRow(getBaseContext());
            rowHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            ImageView imgHeader = new ImageView(getBaseContext());
            imgHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            imgHeader.getLayoutParams().height = 120;
            imgHeader.setImageResource(R.drawable.tick);
            rowHeader.addView(imgHeader);
            table_layout.addView(rowHeader);
            SharedPreferences CallVariables = getSharedPreferences("CallVariables", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = CallVariables.edit();
            editor.putBoolean("getPreviousValuesFlag", true);
            editor.commit();
        }
        @Override
        protected void onPreExecute() {}
        @Override
        protected void onProgressUpdate(Void... values) {}
    }



    private List<CallLogCLS> getCallDetails() {
        List<CallLogCLS> CallLogList = new ArrayList<>();
        callObj = new CallLogCLS();


        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
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
            if(!phNumber.contains("+")){
                phNumber="+"+phNumber;
            }
            callObj.setContactno(phNumber);
            SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newDateStr = postFormater.format(callDayTime);
            callObj.setTmDate(newDateStr);
            callObj.setType(dir);
            callObj.setDuration(callDuration);
            CallLogList.add(callObj);
        }
            return CallLogList;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
