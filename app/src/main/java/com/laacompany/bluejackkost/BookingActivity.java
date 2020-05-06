package com.laacompany.bluejackkost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class BookingActivity extends AppCompatActivity {

    private  RecyclerView mRVBookingList;

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, BookingActivity.class);
        return intent;
    }

    private void init(){
        mRVBookingList = findViewById(R.id.id_booking_rv_booking);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        init();
    }
}
