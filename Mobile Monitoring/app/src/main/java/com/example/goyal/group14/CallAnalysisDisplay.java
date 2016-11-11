package com.example.goyal.group14;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.widget.TableRow.LayoutParams;
import android.view.Gravity;

public class CallAnalysisDisplay extends AppCompatActivity {

    TableLayout table_layout;
    public  List<CallLogCLS> CallLogListReturned;
    DBHelper db;
    Map<String,String> contactMap = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Call Records Analysis");
        setContentView(R.layout.activity_call_analysis_display);
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

        db= new DBHelper(this);
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if(!phoneNumber.contains("+")){
                phoneNumber="+"+phoneNumber;
            }
            contactMap.put(phoneNumber,name);
        }
        phones.close();

    }


    public void getAllCallsRetrieve(View view){
        CallLogListReturned= db.getAllCallLogs();
        BuildTable();
    }

    public void getMostFrequentCallsRetrieve(View view){
        CallLogListReturned= db.getMostFrequestCalls();
        BuildFrequentCallsTable();
    }

    public void CountedCallsRetrieve(View view){
        EditText txt = (EditText) findViewById(R.id.CallCountTXT);
        String count = String.valueOf(txt.getText());
        if(txt.getText()==null || txt.getText().length()==0){
            txt.setError("Please enter a valid Count!");
            return;
        }
      CallLogListReturned= db.getCountedCallLogs(count);
        BuildTable();
    }



    private void BuildFrequentCallsTable() {
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        ScrollView scrollViewObj = (ScrollView) findViewById(R.id.scrolviewID);
        int rows = table_layout.getChildCount();
        table_layout.removeViews(0, rows);
        TableRow rowHeader = new TableRow(this);
        rowHeader.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        TextView tv1Header = new TextView(this);
        tv1Header.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        tv1Header.setGravity(Gravity.CENTER);
        tv1Header.setTextSize(17);
        tv1Header.setPadding(0, 5, 0, 5);
        tv1Header.setText("Contact No.");

        TextView tv3Header = new TextView(this);
        tv3Header.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        tv3Header.setGravity(Gravity.CENTER);
        tv3Header.setTextSize(17);
        tv3Header.setPadding(0, 5, 0, 5);
        tv3Header.setText("Frequency");

        TextView tv4Header = new TextView(this);
        tv4Header.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        tv4Header.setGravity(Gravity.CENTER);
        tv4Header.setTextSize(17);
        tv4Header.setPadding(0, 5, 0, 5);
        tv4Header.setText(String.valueOf("Total Duration"));

        rowHeader.setBackgroundColor(Color.LTGRAY);
        rowHeader.addView(tv1Header);
        rowHeader.addView(tv3Header);
        rowHeader.addView(tv4Header);
        table_layout.addView(rowHeader);


        CallLogCLS CallLogObj;
        for (int i = 0; i < CallLogListReturned.size(); i++) {
            CallLogObj= CallLogListReturned.get(i);
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            TextView tv1 = new TextView(this);
            tv1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv1.setTextSize(15);
            tv1.setPadding(0, 5, 0, 5);
            if(contactMap.get(CallLogObj.getContactno())!=null){
                tv1.setText(contactMap.get(CallLogObj.getContactno()));
            }
           else{
                tv1.setText(CallLogObj.getContactno());
            }
          //

            TextView tv3 = new TextView(this);
            tv3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(15);
            tv3.setPadding(0, 5, 0, 5);
            CallLogObj.setTmDate(CallLogObj.getTmDate().replace(" ", "\n"));
            tv3.setText(String.valueOf(CallLogObj.getTmDate()));

            TextView tv4 = new TextView(this);
            tv4.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextSize(15);
            tv4.setPadding(0, 5, 0, 5);
            Integer totalSecs = Integer.parseInt(CallLogObj.getDuration());
            Integer hours = totalSecs / 3600;
            Integer minutes = (totalSecs % 3600) / 60;
            Integer seconds = totalSecs % 60;
            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            tv4.setText(String.valueOf(timeString));
            if(CallLogObj.getBlocked()) {
                row.setBackgroundColor(Color.RED);
            }
            row.addView(tv1);
            row.addView(tv3);
            row.addView(tv4);
            table_layout.addView(row);
        }

    }



    private void BuildTable() {

        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        ScrollView scrollViewObj = (ScrollView) findViewById(R.id.scrolviewID);


        int rows = table_layout.getChildCount();
              table_layout.removeViews(0,rows);

        TableRow rowHeader = new TableRow(this);
        rowHeader.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        TextView tv1Header = new TextView(this);
        tv1Header.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        tv1Header.setGravity(Gravity.CENTER);
        tv1Header.setTextSize(17);
        tv1Header.setPadding(0, 5, 0, 5);
        tv1Header.setText("Contact No.");

        TextView tv3Header = new TextView(this);
        tv3Header.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        tv3Header.setGravity(Gravity.CENTER);
        tv3Header.setTextSize(17);
        tv3Header.setPadding(0, 5, 0, 5);
        tv3Header.setText("Date/Time");

        TextView tv4Header = new TextView(this);
        tv4Header.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        tv4Header.setGravity(Gravity.CENTER);
        tv4Header.setTextSize(17);
        tv4Header.setPadding(0, 5, 0, 5);
        tv4Header.setText(String.valueOf("Duration"));


        ImageView imgHeader = new ImageView(this);
        imgHeader.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        imgHeader.setPadding(0, 5, 0, 5);
        imgHeader.getLayoutParams().height = 50;
        imgHeader.getLayoutParams().width = 2;
        imgHeader.setImageResource(R.drawable.call);
        rowHeader.setBackgroundColor(Color.LTGRAY);

        rowHeader.addView(tv1Header);
        rowHeader.addView(imgHeader);
        rowHeader.addView(tv3Header);
        rowHeader.addView(tv4Header);
        table_layout.addView(rowHeader);

        CallLogCLS CallLogObj;
        for (int i = 0; i < CallLogListReturned.size(); i++) {
            CallLogObj= CallLogListReturned.get(i);
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            TextView tv1 = new TextView(this);
            tv1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv1.setTextSize(15);
            tv1.setPadding(0, 5, 0, 5);
            if(contactMap.get(CallLogObj.getContactno())!=null){
                tv1.setText(contactMap.get(CallLogObj.getContactno()));
            }
            else {
                tv1.setText(CallLogObj.getContactno());
            }
            TextView tv3 = new TextView(this);
            tv3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(15);
            tv3.setPadding(0, 5, 0, 5);
            CallLogObj.setTmDate(CallLogObj.getTmDate().replace(" ", "\n"));
            tv3.setText(String.valueOf(CallLogObj.getTmDate()));

            TextView tv4 = new TextView(this);
            tv4.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextSize(15);
            tv4.setPadding(0, 5, 0, 5);
            Integer totalSecs = Integer.parseInt(CallLogObj.getDuration());
            Integer hours = totalSecs / 3600;
            Integer minutes = (totalSecs % 3600) / 60;
            Integer seconds = totalSecs % 60;
            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            tv4.setText(String.valueOf(timeString));

            ImageView img = new ImageView(this);
            img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            img.setPadding(0, 5, 0, 5);
            img.getLayoutParams().height = 40;
            img.getLayoutParams().width = 2;
            switch (CallLogObj.getType()) {
                case "OUTGOING":
                    img.setImageResource(R.drawable.outgoing);
                    break;
                case "INCOMING":
                    img.setImageResource(R.drawable.incoming);
                    break;
                case "MISSED":
                    img.setImageResource(R.drawable.missed);
                    break;
            }
            if(CallLogObj.getBlocked()) {
                row.setBackgroundColor(Color.RED);
            }

            row.addView(tv1);
            row.addView(img);
            row.addView(tv3);
            row.addView(tv4);
            table_layout.addView(row);

        }

    }

}
