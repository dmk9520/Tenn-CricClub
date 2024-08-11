package com.example.jeet.tenncricclub;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dhruvit Katrodiya on 07-02-2018.
 */

public class Database extends SQLiteOpenHelper {
    private SQLiteDatabase sqlDB;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "user_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user_table (_id integer primary key autoincrement,type text, fname text, lname text, address text, email text, username text, dob text, mobileno number, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDBCon() {
        sqlDB = getWritableDatabase();
    }

    public void closeDBCon() {
        sqlDB.close();
    }

    public long insertIntostu(String sname, double scgpa) {
        ContentValues cv = new ContentValues();
        cv.put("sname", sname);
        cv.put("scgpa", scgpa);
        long _id = sqlDB.insert("student", null, cv);
        return _id;
    }
}
