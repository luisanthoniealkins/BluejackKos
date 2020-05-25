package com.laacompany.bluejackkost.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "userBase.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DBSchema.UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DBSchema.UserTable.Cols.USER_ID + ", " +
                DBSchema.UserTable.Cols.USERNAME + ", " +
                DBSchema.UserTable.Cols.PASSWORD + ", " +
                DBSchema.UserTable.Cols.PHONE + ", " +
                DBSchema.UserTable.Cols.DOB + ", " +
                DBSchema.UserTable.Cols.GENDER +
                ")");

        db.execSQL("create table " + DBSchema.BookingTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DBSchema.BookingTable.Cols.BOOKING_ID + ", " +
                DBSchema.BookingTable.Cols.USER_ID + ", " +
                DBSchema.BookingTable.Cols.BHOUSE_ID + ", " +
                DBSchema.BookingTable.Cols.BOOKING_DATE +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DBSchema.BookingTable.NAME);
        onCreate(db);
    }

}
