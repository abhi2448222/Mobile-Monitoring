package com.example.goyal.group14;

/**
 * Created by goyal on 4/12/2016.
 * */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MobileDataPro";



    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
          }

    @Override
    public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE callLogs ( id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , contactno VARCHAR NOT NULL , tmdate VARCHAR, blocked BOOL DEFAULT 0, type VARCHAR, duration INTEGER)");
                db.execSQL("CREATE TABLE notification ( id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , contactno VARCHAR NOT NULL )");
               db.execSQL("CREATE TABLE Login ( id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , username VARCHAR NOT NULL, password VARCHAR NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }



    public void adduser(String username,String password){
        CallLogCLS CallLogObj;// = new CallLogCLS();
        Log.d("-------------",username+"---"+password);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password",password);

            db.insert("Login",
                    null,
                    values);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    public Boolean getuser(String username,String password) {
        Log.d("-------------",username+"---"+password);
        List<String> NotificationLIst = new LinkedList<>();
        String query = "SELECT  * FROM Login where username='"+username+"' and password ='"+password+"'";
       Log.d("query=",query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    return true;
                }while (cursor.moveToNext());

            }
            return false;
        }
        else{
            return false;
        }

    }

    public void addNotificationContact(String Contact){
        CallLogCLS CallLogObj;// = new CallLogCLS();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
                values.put("contactno", Contact);

            db.insert("notification",
                        null,
                        values);

        db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
    }




    public List<String> getNotificationContacts() {

        List<String> NotificationLIst = new LinkedList<>();
        String query = "SELECT  * FROM notification";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    NotificationLIst.add(cursor.getString(cursor.getColumnIndex("contactno")));
                }while (cursor.moveToNext());
                return NotificationLIst;
            }
        }
        else{
            return null;
        }
        return null;
    }

    public void addCallLogValues( List<CallLogCLS> CallLogList){
        CallLogCLS CallLogObj;// = new CallLogCLS();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for(Integer i=0; i<CallLogList.size();i++){
                CallLogObj =  CallLogList.get(i);
                ContentValues values = new ContentValues();
                values.put("contactno",CallLogObj.getContactno());
                values.put("tmdate", CallLogObj.getTmDate());
                values.put("type", CallLogObj.getType());
                values.put("duration", CallLogObj.getDuration());
                values.put("blocked", CallLogObj.getBlocked());
                db.insert("callLogs",
                        null,
                        values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
    }


    public List<CallLogCLS> getAllCallLogs() {
        List<CallLogCLS> CallLogList = new LinkedList<CallLogCLS>();
        String query = "SELECT  * FROM callLogs ORDER BY datetime(tmdate) DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    CallLogCLS callLogObj = new CallLogCLS();
                    callLogObj.setContactno(cursor.getString(cursor.getColumnIndex("contactno")));
                    callLogObj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                    callLogObj.setTmDate(cursor.getString(cursor.getColumnIndex("tmdate")));
                    callLogObj.setType(cursor.getString(cursor.getColumnIndex("type")));
                    callLogObj.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
                    Boolean value = (cursor.getInt(cursor.getColumnIndex("blocked"))== 1);
                    callLogObj.setBlocked(value);
                    CallLogList.add(callLogObj);
                } while (cursor.moveToNext());
            }
            return CallLogList;
        }
        else{
            return null;
        }
    }


    public List<CallLogCLS> getCountedCallLogs(String Count) {
        List<CallLogCLS> CallLogList = new LinkedList<CallLogCLS>();
        String query = "SELECT  * FROM callLogs";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM callLogs ORDER BY datetime(tmdate) DESC Limit " + Count, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    CallLogCLS callLogObj = new CallLogCLS();
                    callLogObj.setContactno(cursor.getString(cursor.getColumnIndex("contactno")));
                    callLogObj.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                    callLogObj.setTmDate(cursor.getString(cursor.getColumnIndex("tmdate")));
                    callLogObj.setType(cursor.getString(cursor.getColumnIndex("type")));
                    callLogObj.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
                    Boolean value = (cursor.getInt(cursor.getColumnIndex("blocked"))== 1);
                    callLogObj.setBlocked(value);
                    CallLogList.add(callLogObj);
                } while (cursor.moveToNext());
            }
            return CallLogList;
        }
        else{
            return null;
        }
    }



    public List<CallLogCLS> getMostFrequestCalls() {
        List<CallLogCLS> CallLogList = new LinkedList<CallLogCLS>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *, count(contactno) as countCal,sum(duration) as sumDuration  FROM callLogs GROUP BY contactno ORDER BY countCal DESC", null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    CallLogCLS callLogObj = new CallLogCLS();
                    callLogObj.setContactno(cursor.getString(cursor.getColumnIndex("contactno")));
                    callLogObj.setTmDate(cursor.getString(cursor.getColumnIndex("countCal")));
                    callLogObj.setDuration(cursor.getString(cursor.getColumnIndex("sumDuration")));
                    Boolean value = (cursor.getInt(cursor.getColumnIndex("blocked"))== 1);
                    callLogObj.setBlocked(value);

                    CallLogList.add(callLogObj);
                } while (cursor.moveToNext());
            }
            return CallLogList;
        }
        else{
            return null;
        }
    }





}
