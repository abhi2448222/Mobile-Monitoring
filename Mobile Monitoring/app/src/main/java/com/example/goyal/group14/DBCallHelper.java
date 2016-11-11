package com.example.goyal.group14;

/**
 * Created by goyal on 4/20/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lenovo on 4/12/2016.
 */
public class DBCallHelper extends SQLiteOpenHelper {
    public static final String databaseName="BlockedNumbers.db";
    public static final String col_1="ID";
    public static final String col_2="NUMBER";

    public DBCallHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BLOCKEDNOS (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,NUMBER VARCHAR )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS BLOCKEDNOS");
        this.onCreate(db);

    }

    public boolean insertBlockednos(String number){
        long res=0;
        SQLiteDatabase dbs=this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(col_2, number);
        dbs.beginTransaction();
        try {
            res = dbs.insert("BLOCKEDNOS", null, content);
            dbs.setTransactionSuccessful();


        }
        catch (Exception e){
            Log.d("EXCEPTION CAUGHT", "50");
        }

        finally {
            dbs.endTransaction();
        }
        dbs.close();

        if (res == -1)
            return false;
        else
            return true;
    }

    public Cursor displayBlockedNos(){

        SQLiteDatabase dbs=this.getWritableDatabase();
        String query = "SELECT  * FROM BLOCKEDNOS";
        Cursor ret = dbs.rawQuery(query, null);
        return ret;
    }


}
