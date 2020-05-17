package com.laacompany.bluejackkost.ObjectClass;

import android.util.Log;

public class Booking {

    private String bookingId, userId, bHouseId, bookingDate;

    public Booking(String bookingId, String userId, String bHouseId, String bookingDate) {
        Log.d("12345", "set" + bookingDate);
        this.bookingId = bookingId;
        this.userId = userId;
        this.bHouseId = bHouseId;
        this.bookingDate = bookingDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getbHouseId() {
        return bHouseId;
    }

    public void setbHouseId(String bHouseId) {
        this.bHouseId = bHouseId;
    }

    public String getBookingDate() {
        Log.d("12345", bookingDate);
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}
