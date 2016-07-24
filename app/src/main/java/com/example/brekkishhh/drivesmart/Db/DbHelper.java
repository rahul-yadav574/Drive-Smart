package com.example.brekkishhh.drivesmart.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.brekkishhh.drivesmart.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Brekkishhh on 24-07-2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EmergencyContact.db";
    private static final String TAG  = "DbHelper";
    private Context context;

    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbUtils.CREATE_TABLE);
        ContentValues values = new ContentValues();
        values.put(Schema.DbEntry.COLUMN_PERSON_NAME,context.getString(R.string.admin_name));
        values.put(Schema.DbEntry.COLUMN_PERSON_CONTACT,context.getString(R.string.admin_phone));
        db.insert(Schema.DbEntry.TABLE_NAME,null,values);
       // db.insert(context.getString(R.string.admin_name),context.getString(R.string.admin_phone));

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

    public List<String> retrieveInfoFromDb(){

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {Schema.DbEntry.COLUMN_PERSON_NAME, Schema.DbEntry.COLUMN_PERSON_CONTACT};

        Cursor readCursor = db.query(Schema.DbEntry.TABLE_NAME,
                projection,null,null,null,null,null);

        readCursor.moveToFirst();        //now the cursor points to the first row


        int totalRows = readCursor.getCount();

        List<String> results = new ArrayList<>();

        while (totalRows>0){
            totalRows--;
            String contactName = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.COLUMN_PERSON_NAME));
            String contactNumber = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.COLUMN_PERSON_CONTACT));
            String converted = contactName + "\n" + contactNumber;
            results.add(converted);
            readCursor.moveToNext();
        }

        readCursor.close();

        return results;

    }

    public List<User> retrieveListFromDb(){

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {Schema.DbEntry.COLUMN_PERSON_NAME, Schema.DbEntry.COLUMN_PERSON_CONTACT};

        Cursor readCursor = db.query(Schema.DbEntry.TABLE_NAME,
                projection,null,null,null,null,null);

        readCursor.moveToFirst();        //now the cursor points to the first row


        int totalRows = readCursor.getCount();

        List<User> results = new ArrayList<>();

        while (totalRows>0){
            totalRows--;
            String contactName = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.COLUMN_PERSON_NAME));
            String contactNumber = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.COLUMN_PERSON_CONTACT));
            results.add(new User(contactName,contactNumber));
            readCursor.moveToNext();
        }

        readCursor.close();

        return results;

    }

    public class User {
        private String phone;
        private String name;

        public User(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        public String getPhone() {
            return this.phone;
        }

        public String getName() {
            return this.name;
        }
    }
}
