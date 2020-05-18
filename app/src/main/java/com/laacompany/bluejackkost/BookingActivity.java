package com.laacompany.bluejackkost;

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

    private static TextView mTVNoBookings;
    private RecyclerView mRVBookingList;
    private BookingAdapter bookingAdapter;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, BookingActivity.class);
        return intent;
    }

    private void init() {
        mTVNoBookings = findViewById(R.id.id_booking_tv_no_booking);
        mRVBookingList = findViewById(R.id.id_booking_rv_booking);

        mRVBookingList.setLayoutManager(new LinearLayoutManager(this));
        bookingAdapter = new BookingAdapter(this, Handler.sCurrentBookings);
        mRVBookingList.setAdapter(bookingAdapter);

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
