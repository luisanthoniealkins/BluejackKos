package com.laacompany.bluejackkost;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.laacompany.bluejackkost.Handle.Handler;
import com.laacompany.bluejackkost.ObjectClass.BHouse;
import com.laacompany.bluejackkost.ObjectClass.Booking;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_POS = "position";

    private ImageView mIVPreview;
    private TextView mTVName,mTVFacility,mTVPrice, mTVAddress,mTVLatitude,mTVLongitude;
    private Button mBTNBooking;
    private int pos;
    private BHouse bHouse;

    public static Intent newIntent(Context packageContext, int position){
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_POS,position);
        return intent;
    }

    private void init(){
        mIVPreview = findViewById(R.id.id_detail_imv_preview);
        mTVName = findViewById(R.id.id_detail_tv_name);
        mTVFacility = findViewById(R.id.id_detail_tv_facility);
        mTVPrice = findViewById(R.id.id_detail_tv_price);
        mTVAddress = findViewById(R.id.id_detail_tv_address);
        mTVLatitude = findViewById(R.id.id_detail_tv_latitude);
        mTVLongitude = findViewById(R.id.id_detail_tv_longitude);
        mBTNBooking = findViewById(R.id.id_detail_btn_booking);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        pos = getIntent().getIntExtra(EXTRA_POS,0);
        bHouse = Handler.sBHouses.get(pos);

        Glide.with(this)
                .load(bHouse.getImageURL())
                .centerCrop()
                .into(mIVPreview);

        String facilities = Handler.getSplit(bHouse.getFacility());
        String price = "Rp. " + bHouse.getPrice() + ",00";

        mTVName.setText(bHouse.getName());
        mTVFacility.setText(facilities);
        mTVPrice.setText(price);
        mTVAddress.setText(bHouse.getAddress());
        mTVLatitude.setText(String.valueOf(bHouse.getLatitude()));
        mTVLongitude.setText(String.valueOf(bHouse.getLongitude()));

        for(Booking booking : Handler.sCurrentBookings){
            if (booking.getbHouseId().equals(String.valueOf(pos+1))) mBTNBooking.setEnabled(false);
        }

    }

    public void clickMaps(View view) {
        startActivity(MapsActivity.newIntent(this, bHouse.getLatitude(), bHouse.getLongitude(), bHouse.getName()));
    }

    public void clickBooking(View view) {
        Calendar today = Calendar.getInstance();

        int DD = today.get(Calendar.DAY_OF_MONTH);
        int MM = today.get(Calendar.MONTH);
        int YYYY = today.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(DetailActivity.this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int YYYY, int MM, int DD) {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM YYYY");
                    GregorianCalendar gc = new GregorianCalendar(YYYY,MM,DD);
                    Date date = new Date(gc.getTimeInMillis());
                    String string_date = sdf.format(date);
                    book(string_date);
                }
            }, YYYY, MM, DD);
        datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
        datePickerDialog.show();

    }

    public void book(String date){
        String book_id = Handler.getBookingID(this);
        Handler.insertBooking(new Booking(book_id, Handler.sCurrentUser, String.valueOf(pos+1), date));
        Handler.sCurrentBookings.add(new Booking(book_id, Handler.sCurrentUser, String.valueOf(pos+1), date));
        Toast.makeText(this, "Booking Successful!", Toast.LENGTH_SHORT).show();
        finish();
    }


}
