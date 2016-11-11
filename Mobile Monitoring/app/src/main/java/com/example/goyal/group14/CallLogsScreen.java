package com.example.goyal.group14;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.regex.Pattern;
public class CallLogsScreen extends AppCompatActivity {

    EditText input;
    Button shwBtn,disBtn;
    String regexStr = "^\\+[0-9]{10,11}$";
   // String regexStr="^[0-9]{10,13}$";
    DBCallHelper db ;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_logs_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Call Blocking");


        db=new DBCallHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Block A Number");
        builder.setMessage("Please Enter the no to be blocked");

        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //View dialogView = inflater.inflate(R.layout.new_dialog, null);

        input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = input.getText().toString();
                boolean res=db.insertBlockednos(txt);
                if (res=true)
                    Toast.makeText(getApplicationContext(), "NUMBER STORED", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "NUMBER NOT STORED", Toast.LENGTH_LONG).show();

                //if( input.getText().toString().length() == 0 ) {
                //  input.setError("Phone number is required!");
                //((AlertDialog) dialog).getButton(which).setVisibility(View.INVISIBLE);
                //((AlertDialog)dialog).getButton(which).setEnabled(false);
                //   }
                /*if(txt.matches(regexStr)==false)
                {
                    input.setError( "Please Enter a Valid Phone number" );
                    //((AlertDialog)dialog).getButton(which).setEnabled(false);
                }*/

               // Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog ad = builder.create();

        shwBtn = (Button) findViewById(R.id.button4);
        shwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();

                input.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() < 12 || s.toString().matches(regexStr)==false||s.length()>12) {
                            input.setError("Enter a valid phone number along with the country code !");
                            ad.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                        } else {
                            ad.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        }
                    }
                });
//                if (input.getText().toString().length() == 0 /*|| input.getText().toString().matches(regexStr) == false*/) {
//                    input.setError("Phone number is required!");
//                    ad.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false); //BUTTON1 is positive button
//                }
//                else{
//                    ad.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//                }
            }
        });
      /* shwBtn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ad.setOnShowListener(new DialogInterface.OnShowListener() {

                                              @Override
                                              public void onShow(DialogInterface dialog) {
                                                  if (input.getText().toString().length() == 0 || input.getText().toString().matches(regexStr) == false) {
                                                      input.setError("Phone number is required!");
                                                      ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                                  }
                                              }
                                          });

                                          ad.show();



                                          //ad.show();
                                      }
                                  }
        );*/


        viewAll();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

 /*   @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CallLogsScreen Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.misha.group14_project/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CallLogsScreen Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.misha.group14_project/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/

    public void viewAll(){

        disBtn = (Button) findViewById(R.id.button5);
        disBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = db.displayBlockedNos();
                if (res.getCount() == 0) {

                    //display a message
                    dispData("BLOCKED_LIST","NO NUMBERS FOUND");
                    return;
                }

                StringBuffer buff = new StringBuffer();
                while (res.moveToNext()) {

                    if(res.getString(1).equals("") || res.getString(1).equals(null)  ){
                        //hashBlockedNo
                        Log.d("NUM===="+res.getString(1), "50");
                        //Log.d("HERE","50");
                        continue;


                    }

                    buff.append(res.getString(1) + "\n");
                    Log.d("NUM====" + res.getString(1), "50");
                    //Log.d("THERE","50");

                }
                dispData("BLOCKED_LIST",buff.toString());
            }

        });
    }

    public void dispData(String title, String message){
        AlertDialog.Builder builderr = new AlertDialog.Builder(this);
        builderr.setCancelable(true);
        builderr.setTitle(title);
        builderr.setMessage(message);
        builderr.show();

    }

    public void backBtnPress(View v){
        this.onBackPressed();
    }
}