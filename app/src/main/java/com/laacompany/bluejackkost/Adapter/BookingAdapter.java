package com.laacompany.bluejackkost.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laacompany.bluejackkost.BookingActivity;
import com.laacompany.bluejackkost.Handle.Handler;
import com.laacompany.bluejackkost.ObjectClass.BHouse;
import com.laacompany.bluejackkost.ObjectClass.Booking;
import com.laacompany.bluejackkost.R;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder>{

    private Context mContext;
    private ArrayList<Booking> mBookings;


    public BookingAdapter(Context context, ArrayList<Booking> bookings){
        mContext = context;
        mBookings = bookings;

    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingHolder(LayoutInflater.from(mContext), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {
        holder.bind(mBookings.get(position));
    }

    @Override
    public int getItemCount() {
        return mBookings.size();
    }

    public void setBookings(ArrayList<Booking> bookings){
        mBookings = bookings;
    }


    public class BookingHolder extends RecyclerView.ViewHolder{

        private ImageView mIVPreview;
        private TextView mTVBookId, mTVName, mTVFacility, mTVPrice, mTVDate;
        private Button mBTDelete;

        public BookingHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.item_booking,parent,false));

            mIVPreview = itemView.findViewById(R.id.id_item_book_imv_preview);
            mTVBookId = itemView.findViewById(R.id.id_item_book_tv_id);
            mTVName = itemView.findViewById(R.id.id_item_book_tv_name);
            mTVFacility = itemView.findViewById(R.id.id_item_book_tv_facility);
            mTVPrice = itemView.findViewById(R.id.id_item_book_tv_price);
            mTVDate = itemView.findViewById(R.id.id_item_book_tv_date);
            mBTDelete = itemView.findViewById(R.id.id_item_book_btn_delete);


        }

        public void bind(final Booking booking){

            int pos = Integer.parseInt(booking.getbHouseId())-1;
            BHouse bHouse = Handler.sBHouses.get(pos);
            Glide.with(mContext)
                    .load(bHouse.getImageURL())
                    .centerCrop()
                    .into(mIVPreview);

            String price = "Rp. " + bHouse.getPrice() + ",00";
            String facilities = "Facilities :\n" + Handler.getSplit(bHouse.getFacility());
            String bookno = "Booking no : " + booking.getBookingId();
            String bookdate = booking.getBookingDate();
            mTVBookId.setText(bookno);
            mTVName.setText(bHouse.getName());
            mTVFacility.setText(facilities);
            mTVPrice.setText(price);
            mTVDate.setText(bookdate);

            mBTDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("Cancel Booking");
                    alertDialog.setMessage("Are you sure you want to cancel this booking?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Proceed",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Handler.removeBooking(booking.getBookingId());
                                    Handler.sCurrentBookings.remove(booking);
                                    notifyDataSetChanged();
                                    if (getItemCount() == 0) BookingActivity.refreshLayout();
                                }
                            });
                    alertDialog.show();
                }
            });
        }



    }

}
