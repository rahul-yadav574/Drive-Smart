package com.example.brekkishhh.drivesmart.Db;

/**
 * Created by Brekkishhh on 24-07-2016.
 */
public class DbUtils {

    private static final String COMMA_SEP = ",";
    private static final String TYPE_TEXT = " TEXT";

    public static final String CREATE_TABLE = "CREATE TABLE "+ Schema.DbEntry.TABLE_NAME+" ("+ Schema.DbEntry._ID+" INTEGER PRIMARY KEY,"
            + Schema.DbEntry.COLUMN_PERSON_NAME+TYPE_TEXT+COMMA_SEP+ Schema.DbEntry.COLUMN_PERSON_CONTACT + TYPE_TEXT + " )";



}
