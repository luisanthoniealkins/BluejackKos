package com.laacompany.bluejackkost.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laacompany.bluejackkost.DetailActivity;
import com.laacompany.bluejackkost.ObjectClass.BHouse;
import com.laacompany.bluejackkost.R;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BoardingHouseAdapter extends RecyclerView.Adapter<BoardingHouseAdapter.BHouseHolder> {

    private Context mContext;
    private ArrayList<BHouse> mBHouses;

    public BoardingHouseAdapter(Context context, ArrayList<BHouse> bHouses){
        mContext = context;
        mBHouses = bHouses;
    }


    @NonNull
    @Override
    public BHouseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BHouseHolder(LayoutInflater.from(mContext), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BHouseHolder holder, int position) {
        holder.bind(mBHouses.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mBHouses.size();
    }

    public class BHouseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int position;
        private ImageView mIVPreview;
        private TextView mTVName,mTVFacility,mTVPrice;

        public BHouseHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.item_boarding_house,parent,false));

            mIVPreview = itemView.findViewById(R.id.id_item_imv_preview);
            mTVName = itemView.findViewById(R.id.id_item_tv_name);
            mTVFacility = itemView.findViewById(R.id.id_item_tv_facility);
            mTVPrice = itemView.findViewById(R.id.id_item_tv_price);

            itemView.setOnClickListener(this);
        }

        public void bind(BHouse bHouse, int position){
            this.position = position;

            Glide.with(mContext)
                    .load(bHouse.getImageURL())
                    .centerCrop()
                    .into(mIVPreview);

            mTVName.setText(bHouse.getName());
            mTVFacility.setText(bHouse.getFacility());
            mTVPrice.setText(String.valueOf(bHouse.getPrice()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = DetailActivity.newIntent(mContext.getApplicationContext(), position);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }



    }

}
