package com.example.goyal.group14;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class MessageAPI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_api);
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
    }
    public void read_sent(View v)
    {
        Intent intent= new Intent(this,read_sent.class);
        startActivity(intent);
    }

    public void read_inbox(View v)
    {
        Intent intent= new Intent(this,readReceived.class);
        startActivity(intent);

    }

    public void set_content(View v)
    {
        Intent intent= new Intent(this,Messages_addContent.class);
       startActivity(intent);
    }

    public void addBlock(View v)
    {
       Intent intent=new Intent(this,Message_addNo.class);
       startActivity(intent);

    }

    public void showDuration(View v)
    {
        Intent intent=new Intent(this,Message_SpecifiedDuration.class);
        startActivity(intent);


    }

}
