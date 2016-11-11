package com.example.goyal.group14;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class Message_SpecifiedDuration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message__specified_duration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void showtext(View v)
    {
        Intent intent= new Intent(this,showSpecifiedDuration.class);
        startActivity(intent);
    }
    public void UploadtoServer(View v)
    {
        Toast.makeText(getApplicationContext(),"Uploading to Server", Toast.LENGTH_LONG).show();
    }

}
