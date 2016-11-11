package com.example.goyal.group14;

/**
 * Created by goyal on 4/20/2016.
 */
/**
 * Created by Lenovo on 4/15/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBLocation extends SQLiteOpenHelper{

    // public static final String databaseName="Location.db";
    public static final String databaseName="Location1.db";
    public static final String col_1="ID";
    //    public static final String col_2="ADDRESS";
//    public static final String col_3="CITY";
//    public static final String col_4="STATE";
//    public static final String col_5="POSTALCODE";
    public static final String col_6="LATITUDE";
    public static final String col_7="LONGITUDE";
    String lastKnownAddress="";
    boolean firstTimeDataEntry=false;


    public DBLocation(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL("CREATE TABLE RECENTLOCATIONS (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,ADDRESS VARCHAR,CITY VARCHAR,STATE VARCHAR,POSTALCODE VARCHAR,LATITUDE DOUBLE,LONGITUDE DOUBLE )");
        db.execSQL("CREATE TABLE RECENTLOCATIONS (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,LATITUDE DOUBLE,LONGITUDE DOUBLE )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS RECENTLOCATIONS");
        this.onCreate(db);

    }

    public void insertLocations (double Latitude, double Longitude){
        long res=0;

        Log.d("Creating table Always", "50");
        SQLiteDatabase dbs = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(col_6, Latitude);
        content.put(col_7, Longitude);
        dbs.beginTransaction();
        try {
            Log.d("Trying to insert", "50");
            res = dbs.insert("RECENTLOCATIONS", null, content);
            Log.d("value of res", String.valueOf(res));
            dbs.setTransactionSuccessful();


        } catch (Exception e) {
            Log.d("EXCEPTION CAUGHT", "50");
        } finally {
            dbs.endTransaction();
        }
        dbs.close();

        Log.d("Setting truee", "50");
        //firstTimeDataEntry=true;



    }

    //    public void insertLocations(String address, String state, String city, String postalCode, double Latitude, double Longitude){
//        long res=0;
//        if(!firstTimeDataEntry) {
//
//            //First time data Entry
//            Log.d("Creating table 1st time", "50");
//            SQLiteDatabase dbs = this.getWritableDatabase();
//            ContentValues content = new ContentValues();
//            content.put(col_2, address);
//            content.put(col_3, state);
//            content.put(col_4, city);
//            content.put(col_5, postalCode);
//            content.put(col_6, Latitude);
//            content.put(col_7, Longitude);
//            dbs.beginTransaction();
//            try {
//                Log.d("Trying to insert", "50");
//                res = dbs.insert("RECENTLOCATIONS", null, content);
//                Log.d("value of res", String.valueOf(res));
//                dbs.setTransactionSuccessful();
//
//
//            } catch (Exception e) {
//                Log.d("EXCEPTION CAUGHT", "50");
//            } finally {
//                dbs.endTransaction();
//            }
//            dbs.close();
//
//            Log.d("Setting truee", "50");
//            firstTimeDataEntry=true;
//            //lastKnownAddress=address;
//
//        }
//        else{
//
//
//            boolean status=checkRecentLocation(address);
//            if(status){
//
//                Log.d("Newa ddress", "50");
//                //New address has came
//                SQLiteDatabase dbs = this.getWritableDatabase();
//                ContentValues content = new ContentValues();
//                content.put(col_2, address);
//                content.put(col_3, state);
//                content.put(col_4, city);
//                content.put(col_5, postalCode);
//                content.put(col_6, Latitude);
//                content.put(col_7, Longitude);
//                dbs.beginTransaction();
//                try {
//                    res = dbs.insert("RECENTLOCATIONS", null, content);
//                    dbs.setTransactionSuccessful();
//
//
//                } catch (Exception e) {
//                    Log.d("EXCEPTION CAUGHT", "50");
//                } finally {
//                    dbs.endTransaction();
//                }
//                dbs.close();
//                Log.d("value of res", String.valueOf(res));
//
//
//            }
//            //Same address no need to store
//            else return;
//
//        }
//    }
    public boolean checkRecentLocation(String address){

        Log.d("Checking Recents", "50");
        String newAddress="";
        SQLiteDatabase dbs=this.getWritableDatabase();
        String query = "SELECT  * FROM RECENTLOCATIONS ";
        Cursor ret = dbs.rawQuery(query, null);

        if(ret.moveToLast()){

            newAddress  =  ret.getString(1);
            Log.d("value of newaddress", newAddress);
        }
        else
        {
            Log.d("movetolastnotfound", "50 ");
        }
        if(address.equals(newAddress)){
            Log.d("Reeturing false", "50");
            return false;

        }
        else{
            // lastKnownAddress=newAddress;
            Log.d("Returning true", "50");
            return true;
        }

    }

    public Cursor displayLocations(){

        SQLiteDatabase dbs=this.getWritableDatabase();
        String query = "SELECT  * FROM RECENTLOCATIONS";
        Cursor ret = dbs.rawQuery(query, null);
        return ret;
    }

    public void deleteAllData(){
        SQLiteDatabase dbs=this.getWritableDatabase();
        Log.d("Delete all dataa","50");
        dbs.execSQL("delete from RECENTLOCATIONS");
    }

}