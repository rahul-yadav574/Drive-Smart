package com.example.brekkishhh.drivesmart.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Brekkishhh on 24-07-2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EmergencyContact.db";

    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbUtils.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addEntryToDb(String contactName,String contactNumber){

        SQLiteDatabase db =  getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Schema.DbEntry.COLUMN_PERSON_NAME,contactName);
        values.put(Schema.DbEntry.COLUMN_PERSON_CONTACT,contactNumber);

        db.insert(Schema.DbEntry.TABLE_NAME,null,values);

    }

    public Map<String,String> retrieveInfoFromDb(){

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {Schema.DbEntry.COLUMN_PERSON_NAME, Schema.DbEntry.COLUMN_PERSON_CONTACT};

        Cursor readCursor = db.query(Schema.DbEntry.TABLE_NAME,
                projection,null,null,null,null,null);

        readCursor.moveToFirst();        //now the cursor points to the first row


        int totalRows = readCursor.getCount();

        Map<String,String> results = new HashMap<>();

        while (totalRows>0){
            totalRows--;

            String contactName = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.COLUMN_PERSON_NAME));
            String contactNumber = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.COLUMN_PERSON_CONTACT));

            results.put(contactNumber,contactName);

        }

        readCursor.close();

        return results;

    }
}
