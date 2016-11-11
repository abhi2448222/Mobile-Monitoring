package com.example.goyal.group14;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    boolean firstTimeLogin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sign UP");
        setContentView(R.layout.activity_signup);
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
    public void signup_screen(View view) {
        DBHelper db = new DBHelper(this);
        EditText login = (EditText) findViewById(R.id.email_signup);
        EditText password = (EditText) findViewById(R.id.password_signup);
        EditText confirmPass=(EditText)findViewById(R.id.confirm_password_signup);
        Log.d("password", String.valueOf(password.getText()));
        Log.d("Confrpassword", String.valueOf(confirmPass.getText()));
        if(login.getText().length()<=0){
            login.setError("Please enter UserID");
        }
        else if(password.getText().length()<=0){
            password.setError("Please enter password");
        }
        else if(confirmPass.getText().length()<=0){
            confirmPass.setError("Please re enter password");
        }

        else if(confirmPass.getText().toString().equals(password.getText().toString())) {
            db.adduser(login.getText().toString(), password.getText().toString());
            Log.d("setting flagTrue", "50");
            SharedPreferences signUPVariables = getSharedPreferences("signUPVariables", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = signUPVariables.edit();
            editor.putBoolean("signUPVariablesFlag", true);
            editor.commit();
            firstTimeLogin=true;
            Toast.makeText(Signup.this,"UserName and Password stored in the database ",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(Signup.this,"Password Doesnt match",Toast.LENGTH_LONG).show();
        }

    }

    public boolean setupFlag(){
        if (firstTimeLogin){
            return true;
        }
        else{
            return false;
        }
    }
}
