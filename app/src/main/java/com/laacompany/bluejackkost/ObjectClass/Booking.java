package com.laacompany.bluejackkost.ObjectClass;

public class Booking {

    private String bookingId, userId, bHouseId, bookingdate;

    public Booking(String bookingId, String userId, String bHouseId, String bookingdate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.bHouseId = bHouseId;
        this.bookingdate = bookingdate;
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

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }
}
