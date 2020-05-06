package com.laacompany.bluejackkost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_POS = "position";

    private ImageView mIVPreview;
    private TextView mTVName,mTVFacility,mTVPrice,mTVDescription,mTVLatitude,mTVLongitude;

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
        mTVDescription = findViewById(R.id.id_detail_tv_description);
        mTVLatitude = findViewById(R.id.id_detail_tv_latitude);
        mTVLongitude = findViewById(R.id.id_detail_tv_longitude);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        int pos = getIntent().getIntExtra(EXTRA_POS,0);
        BHouse bHouse = Handler.sBHouses.get(pos);

        mIVPreview.setImageDrawable(Handler.LoadImageFromWebOperations(bHouse.getImageURL()));
        mTVName.setText(bHouse.getName());
        mTVFacility.setText(bHouse.getFacility());
        mTVPrice.setText(String.valueOf(bHouse.getPrice()));
        mTVDescription.setText(bHouse.getDescription());
        mTVLatitude.setText(String.valueOf(bHouse.getLatitude()));
        mTVLongitude.setText(String.valueOf(bHouse.getLongitude()));
    }

    public void clickMaps(View view) {

    }

    public void clickBooking(View view) {

    }
}
