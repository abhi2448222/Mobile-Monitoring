package com.example.goyal.group14;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class showSpecifiedDuration extends AppCompatActivity {


    ListView text1,text2;
    List<String> sms_bodyR = new ArrayList();
    List<String> sms_bodyS = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_specified_duration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        text1 = (ListView) findViewById(R.id.listView3);
        text2 = (ListView) findViewById(R.id.listView4);
        int i = 0;
        String msgData;
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

            if (cursor.moveToFirst()) { // must check the result to prevent exception
                for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
                    msgData = "";
                    long epoch = cursor.getInt(4);
                    String address = cursor.getString(2);
                    String body = cursor.getString(12);
                    Date date = new Date(epoch);
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String formatted = format.format(date);
                    Log.e("epoch to time", String.valueOf(epoch) + formatted);
                    if(formatted.contains("1969"))
                        formatted.replace("1969","2015");
                    if(formatted.contains("1970"))
                        formatted.replace("1970","2016");
                    msgData = formatted + "|" + address + "|" + body;
                    sms_bodyR.add(msgData);
                    //Log.e("list",sms_body.get(i))
                    if(i>10)
                        break;
                    i++;

                }
            } else {
                Log.e("EmptyInbox", "readInbox: ");
            }

            System.out.println(sms_bodyR.size());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.locations, sms_bodyR);
            text1.setAdapter(adapter);


        cursor.close();
        Cursor cursor1 = getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
        i=0;
        if (cursor1.moveToFirst()) { // must check the result to prevent exception
            for (cursor1.moveToLast(); !cursor1.isBeforeFirst(); cursor1.moveToPrevious()) {
                msgData = "";
                long epoch = cursor1.getInt(4);
                String address = cursor1.getString(2);
                String body = cursor1.getString(12);
                Date date = new Date(epoch);
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String formatted = format.format(date);
                Log.e("epoch to time", String.valueOf(epoch) + formatted);

                msgData = formatted + "|" + address + "|" + body;
                sms_bodyS.add(msgData);
                Log.e("list", sms_bodyS.get(i));
                               i++;
                if(i>10)
                    break;

            }
        } else {
            Log.e("EmptyInbox", "readInbox: ");
        }

        System.out.println(sms_bodyS.size());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.locations, sms_bodyS);
        text2.setAdapter(adapter2);


    }

}
