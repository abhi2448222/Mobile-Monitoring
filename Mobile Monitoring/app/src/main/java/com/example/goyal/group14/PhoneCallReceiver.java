package com.example.goyal.group14;

/**
 * Created by goyal on 4/12/2016.
 */
import java.lang.reflect.Method;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;



import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class PhoneCallReceiver extends BroadcastReceiver {
    private static final String TAG = null;
    String currentcallNumber;
    List<String> blockedNo = new ArrayList<String>();
    DBHelper db;
    //static HashMap<String,String> hashBlockedNo = new HashMap();

    @TargetApi(Build.VERSION_CODES.M)
    public void onReceive(Context context, Intent intent) {
        DBCallHelper dbCall = new DBCallHelper(context) ;
        Cursor res = dbCall.displayBlockedNos();
        while (res.moveToNext()) {
            if (res.getString(1).equals("") || res.getString(1).equals(null)) {
                Log.d("NUM====" + res.getString(1), "50");
                continue;
            }
            blockedNo.add(res.getString(1));
        }
        Bundle bundle = intent.getExtras();
        if (null == bundle)
            return;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Bundle b = intent.getExtras();
               currentcallNumber = b.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                    TelephonyManager.EXTRA_STATE_RINGING) || intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                    TelephonyManager.EXTRA_STATE_OFFHOOK) ) {
                for(int i=0;i<blockedNo.size();i++){
                    if (blockedNo.get(i).contains(currentcallNumber)){
                        disconnectCall();
                        int hasSMSWriteContactsPermission = context.checkSelfPermission(Manifest.permission.SEND_SMS);
                        db = new DBHelper(context);
                        List<String> notificationList = db.getNotificationContacts();
                        SmsManager smsManager = SmsManager.getDefault();
                        String smsContent= "CAll Attempt to Blocked:"+currentcallNumber;
                        for(int j=0; j<notificationList.size();j++){
                            smsManager.sendTextMessage(notificationList.get(j), null, smsContent, null, null);
                        }

                    }
                }
            } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                    TelephonyManager.EXTRA_STATE_IDLE)
                    || intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                    TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                List<CallLogCLS> CallLogList = new ArrayList<>();
                int hasWriteContactsPermission = context.checkSelfPermission(Manifest.permission.WRITE_CALL_LOG);
                Cursor cur = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC limit 1;");
               int number = cur.getColumnIndex(CallLog.Calls.NUMBER);
               int type = cur.getColumnIndex(CallLog.Calls.TYPE);
               int date = cur.getColumnIndex(CallLog.Calls.DATE);
               int duration = cur.getColumnIndex(CallLog.Calls.DURATION);
                while (cur.moveToNext()) {
                    CallLogCLS  callObj = new CallLogCLS();
                    String phNumber = cur.getString(number);
                    String callType = cur.getString(type);
                    String callDate = cur.getString(date);
                    Date callDayTime = new Date(Long.valueOf(callDate));
                    String callDuration = cur.getString(duration);
                    String dir = null;
                    int dircode = Integer.parseInt(callType);
                    switch (dircode) {
                        case CallLog.Calls.OUTGOING_TYPE:
                            dir = "OUTGOING";
                            break;

                        case CallLog.Calls.INCOMING_TYPE:
                            dir = "INCOMING";
                            break;

                        case CallLog.Calls.MISSED_TYPE:
                            dir = "MISSED";
                            break;
                    }
                    for(int i=0;i<blockedNo.size();i++){
                        if (blockedNo.get(i).contains(phNumber)){
                            callObj.setBlocked(true);
                            break;
                        }
                        else{
                            callObj.setBlocked(false);
                        }
                    }
                    if(!phNumber.contains("+")){
                        phNumber="+"+phNumber;
                    }
                    callObj.setContactno(phNumber);
                    SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String newDateStr = postFormater.format(callDayTime);
                    Log.d("Insdie FOrmated adte=", newDateStr+"***********"+callDayTime);
                    callObj.setTmDate(newDateStr);
                    callObj.setType(dir);
                    callObj.setDuration(callDuration);
                    CallLogList.add(callObj);
                    db = new DBHelper(context);
                    db.addCallLogValues(CallLogList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,
                    "FATAL ERROR: could not connect to telephony subsystem");
            Log.e(TAG, "Exception object: " + e);
        }
    }


    public void disconnectCall() {
        try {
            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DialerActivity.this",
                    "FATAL ERROR: could not connect to telephony subsystem");
            Log.d("DialerActivity.this", "Exception object: " + e);
        }

    }

}




