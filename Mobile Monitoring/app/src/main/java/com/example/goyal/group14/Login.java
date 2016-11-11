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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
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
        SharedPreferences signUPVariables = getSharedPreferences("signUPVariables", Context.MODE_PRIVATE);
        boolean signUPVariablesFlag = signUPVariables.getBoolean("signUPVariablesFlag", false);
        if(signUPVariablesFlag){
            Button signup = (Button) findViewById(R.id.signup_Login);
            signup.setVisibility(View.GONE);
        }
    }

    public void loginValidate(View view) {
        Boolean flag=false;
        DBHelper db = new DBHelper(this);
        setTitle("Login");
        EditText login = (EditText) findViewById(R.id.login);
        EditText password = (EditText) findViewById(R.id.password_user);
        if(login.getText().length()<=0){
            login.setError("Please enter UserID");
        }
        else if(password.getText().length()<=0){
            password.setError("Please enter password");
        }
       else{
             flag = db.getuser(login.getText().toString(),password.getText().toString());
            if(flag){
                Intent intent = new Intent(this, MenuScreen.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(Login.this, "The email and password you entered don't match.Please Try again", Toast.LENGTH_LONG).show();

            }
        }




    }

       public void signUP(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
}
