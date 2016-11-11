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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class read_sent extends AppCompatActivity {

    ListView text;
    List<String> sms_body = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sent);
        File file = new File(this.getFilesDir(), "messageHistory.txt");
        String finalLine;
        text = (ListView) findViewById(R.id.listView);
        Log.e("ddd", "Dd2");
        Log.e("ddd", "Dd3");

        int i = 0;
        String msgData;
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput("messageHistory.txt", this.MODE_PRIVATE);
            Log.e("ddd", "Dd4");
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

                    msgData = formatted + "|" + address + "|" + body;
                    sms_body.add(msgData);
                    //Log.e("list",sms_body.get(i));
                    i++;
                    try {
                        outputStream.write(msgData.getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } else {
                Log.e("EmptyInbox", "readInbox: ");
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(sms_body.size());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.locations, sms_body);
            text.setAdapter(adapter);


        } catch (FileNotFoundException e) {
            Log.e("Error opening the file", "XXXXXXXX");
            e.printStackTrace();
        }

    }
}
