package com.laacompany.bluejackkost.Database;

public class DBSchema {

    public static final class UserTable{
        public static final String NAME = "usertable";

        public static final class Cols{
            public static final String USER_ID = "user_id";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }
    }

    public static final class BookingTable{
        public static final String NAME = "bookingtable";

        public static final class Cols{
            public static final String BOOKING_ID = "booking_id";
            public static final String USER_ID = "user_id";
            public static final String BHOUSE_ID = "bhouse_id";
            public static final String BOOKING_DATE = "booking_date";
        }
    }

}
