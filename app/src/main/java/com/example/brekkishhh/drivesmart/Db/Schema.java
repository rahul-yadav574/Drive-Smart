package com.example.brekkishhh.drivesmart.Db;

import android.provider.BaseColumns;

/**
 * Created by Brekkishhh on 24-07-2016.
 */
public class Schema {

    public Schema() {
    }

    public static abstract class DbEntry implements BaseColumns{

        public static final String TABLE_NAME = "emergency_phone";
        public static final String COLUMN_PERSON_NAME = "person_name";
        public static final String COLUMN_PERSON_CONTACT = "person_contact";

    }
}
