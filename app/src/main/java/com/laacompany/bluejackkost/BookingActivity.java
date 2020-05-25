package com.laacompany.bluejackkost;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.laacompany.bluejackkost.Adapter.BookingAdapter;
import com.laacompany.bluejackkost.Handle.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookingActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static TextView mTVNoBookings;

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, BookingActivity.class);
    }

    private void init() {
        mTVNoBookings = findViewById(R.id.id_booking_tv_no_booking);
        RecyclerView RVBookingList = findViewById(R.id.id_booking_rv_booking);

        RVBookingList.setLayoutManager(new LinearLayoutManager(this));
        BookingAdapter bookingAdapter = new BookingAdapter(this, Handler.sCurrentBookings);
        RVBookingList.setAdapter(bookingAdapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        init();
        refreshLayout();
    }

    public static void refreshLayout() {
        if (Handler.sCurrentBookings.size() == 0) {
            mTVNoBookings.setVisibility(View.VISIBLE);
        } else {
            mTVNoBookings.setVisibility(View.GONE);
        }
    }

}
