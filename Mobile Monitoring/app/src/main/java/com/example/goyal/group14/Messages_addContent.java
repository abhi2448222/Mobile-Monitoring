package com.example.goyal.group14;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Messages_addContent extends AppCompatActivity {

    private MyReceiver receiverClass = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_add_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void addContent(View v)
    {
        EditText et=(EditText)findViewById(R.id.editText2);
        Toast.makeText(this,"Key Word "+et.getText()+" added",
                Toast.LENGTH_SHORT).show();

        et.setText("");
        //receiverClass.setMainActivityHandler(this);

    }

}
