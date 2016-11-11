package com.example.goyal.group14;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class notificationContact extends AppCompatActivity {

    DBHelper db;
    String regexStr = "^\\+[0-9]{10,11}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Notication Contact");
        setContentView(R.layout.activity_notification_contact);
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
        EditText txt = (EditText) findViewById(R.id.contact);

    }


    public void registeredNotificationNo(View view){
        db = new DBHelper(this);
        EditText txt = (EditText) findViewById(R.id.contact);
        String contact = String.valueOf(txt.getText());

        if(contact.matches(regexStr)==false || contact.length()!=12) {
            txt.setError("Please enter a valid contact No");
            return;
        }
        if(!contact.contains("+")){
            contact="+"+contact;
        }
        db.addNotificationContact(contact);
        Toast myToast = Toast.makeText(
                getApplicationContext(),
                contact+" is added for notifications.",
                Toast.LENGTH_LONG);
        myToast.show();
        //TableLayout table_layout = (TableLayout) findViewById(R.id.imgTable);
        //int rows = table_layout.getChildCount();
        //table_layout.removeViews(0, rows);
        //TableRow rowHeader = new TableRow(this);
        //rowHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
           //     TableRow.LayoutParams.WRAP_CONTENT));
        //ImageView imgHeader = new ImageView(this);
        //imgHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
          //      TableRow.LayoutParams.WRAP_CONTENT));
       // imgHeader.getLayoutParams().height = 60;
        //imgHeader.setImageResource(R.drawable.tick);
        //rowHeader.addView(imgHeader);
        //table_layout.addView(rowHeader);
    }

}
