package com.laacompany.bluejackkost.Handle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.laacompany.bluejackkost.Database.DBSchema;
import com.laacompany.bluejackkost.Database.DBSchema.BookingTable;
import com.laacompany.bluejackkost.Database.DBSchema.UserTable;
import com.laacompany.bluejackkost.Database.DatabaseHelper;
import com.laacompany.bluejackkost.ObjectClass.BHouse;
import com.laacompany.bluejackkost.ObjectClass.Booking;
import com.laacompany.bluejackkost.ObjectClass.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Handler {

    private static DatabaseHelper sDatabaseHelper;
    public static ArrayList<BHouse> sBHouses;
    public static ArrayList<User> sUsers;
    public static ArrayList<Booking> sCurrentBookings;
    public static String sCurrentUser;

    public static final String SP_USER = "sharedpreferences_user";
    public static final String SP_KEY_ID = "key_userid";
    public static final String SP_KEY_BOOKING = "key_bookingnumber";

    public static void init(Context context){
        sDatabaseHelper = new DatabaseHelper(context);
        sBHouses = new ArrayList<BHouse>();
        sUsers = getAllUser();
        sCurrentUser = "%empty.value%";
    }

    public static void init_bookings(){
        sCurrentBookings = getCurrentUserBookings();
    }


    public static String getUserID(){
        String user_id = "US";
        int idx = sUsers.size();
        if (idx < 10) user_id += "00"+idx;
        else if (idx < 100) user_id += "0"+idx;
        else user_id += idx;
        return user_id;
    }

    public static String getBookingID(Context context){
        String booking_id = "BK";
        int idx = context.getSharedPreferences(SP_USER,Context.MODE_PRIVATE).getInt(SP_KEY_BOOKING,0);
        context.getSharedPreferences(SP_USER,Context.MODE_PRIVATE).edit().putInt(SP_KEY_BOOKING,idx+1).apply();
        if (idx < 10) booking_id += "00"+idx;
        else if (idx < 100) booking_id += "0"+idx;
        else booking_id += idx;
        return booking_id;
    }


    public static void insertUser(User user){
        SQLiteDatabase db = sDatabaseHelper.getWritableDatabase();
        db.insert(UserTable.NAME, null, getContentValuesUser(user));
        db.close();
    }

    private static ContentValues getContentValuesUser(User user){
        ContentValues values = new ContentValues();
        values.put(UserTable.Cols.USER_ID, user.getId());
        values.put(UserTable.Cols.USERNAME, user.getUsername());
        values.put(UserTable.Cols.PASSWORD, user.getPassword());

        return values;
    }

    public static ArrayList<User> getAllUser(){
        ArrayList<User> users = new ArrayList<User>();

        SQLiteDatabase db = sDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query(UserTable.NAME,null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String userid = cursor.getString(cursor.getColumnIndex(UserTable.Cols.USER_ID));
            String username = cursor.getString(cursor.getColumnIndex(UserTable.Cols.USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(UserTable.Cols.PASSWORD));
            String phone = cursor.getString(cursor.getColumnIndex(UserTable.Cols.PHONE));
            String dob = cursor.getString(cursor.getColumnIndex(UserTable.Cols.DOB));
            String gender = cursor.getString(cursor.getColumnIndex(UserTable.Cols.GENDER));
            users.add(new User(userid,username,password,phone,dob,gender));
        }
        cursor.close();
        return users;
    }


    public static void insertBooking(Booking booking){
        SQLiteDatabase db = sDatabaseHelper.getWritableDatabase();
        db.insert(BookingTable.NAME, null, getContentValuesBooking(booking));
        db.close();
    }

    private static ContentValues getContentValuesBooking(Booking booking){
        ContentValues values = new ContentValues();
        values.put(BookingTable.Cols.BOOKING_ID, booking.getBookingId());
        values.put(BookingTable.Cols.USER_ID, booking.getUserId());
        values.put(BookingTable.Cols.BHOUSE_ID, booking.getbHouseId());
        values.put(BookingTable.Cols.BOOKING_DATE, booking.getbHouseId());

        return values;
    }

    public static void removeBooking(String bookingId){
        SQLiteDatabase db = sDatabaseHelper.getWritableDatabase();
        db.delete(BookingTable.NAME, BookingTable.Cols.BOOKING_ID + "= ?", new String[]{bookingId});
        db.close();
    }

    public static ArrayList<Booking> getAllBooking(){
        ArrayList<Booking> bookings = new ArrayList<>();

        SQLiteDatabase db = sDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query(BookingTable.NAME,null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String bookId = cursor.getString(cursor.getColumnIndex(BookingTable.Cols.BOOKING_ID));
            String userId = cursor.getString(cursor.getColumnIndex(BookingTable.Cols.USER_ID));
            String bHouseId = cursor.getString(cursor.getColumnIndex(BookingTable.Cols.BHOUSE_ID));
            String bookDate = cursor.getString(cursor.getColumnIndex(BookingTable.Cols.BOOKING_DATE));
            bookings.add(new Booking(bookId,userId,bHouseId,bookDate));
        }
        cursor.close();
        return bookings;
    }

    private static ArrayList<Booking> getCurrentUserBookings(){
        ArrayList<Booking> allBookings = getAllBooking(), currentBookings = new ArrayList<>();
        for(Booking booking : allBookings){
            if (booking.getUserId().equals(sCurrentUser)){
                currentBookings.add(booking);
            }
        }
        return currentBookings;
    }



}
