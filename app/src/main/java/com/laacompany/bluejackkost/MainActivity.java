package com.laacompany.bluejackkost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.laacompany.bluejackkost.Adapter.BoardingHouseAdapter;
import com.laacompany.bluejackkost.Handle.Handler;
import com.laacompany.bluejackkost.ObjectClass.BHouse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTVLoading;
    private RecyclerView mRVBoardingHouse;

    private static final String JSON_LINK =  "https://bit.ly/2zd4uhX";
    private boolean canShowBooking = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_menu_boarding_list:
                if (canShowBooking) startActivity(BookingActivity.newIntent(this));
                else Toast.makeText(this, "Please Wait..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_menu_logout:

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Log out");
                alertDialog.setMessage("Are you sure you want to logout?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Logout",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getSharedPreferences(Handler.SP_USER,MODE_PRIVATE).edit();
                                editor.remove(Handler.SP_KEY_ID);
                                editor.apply();
                                Handler.sCurrentUser = "%empty.value%";
                                startActivity(LoginActivity.newIntent(getApplicationContext()));
                            }
                        });
                alertDialog.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        mTVLoading = findViewById(R.id.id_main_tv_loading);
        mRVBoardingHouse = findViewById(R.id.id_main_rv_boarding_house);

        mRVBoardingHouse.setLayoutManager(new LinearLayoutManager(this));
        Handler.init(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();



        String sp_value = getSharedPreferences(Handler.SP_USER, MODE_PRIVATE).getString(Handler.SP_KEY_ID, "empty");
        if (sp_value.equals("empty")){
            startActivity(LoginActivity.newIntent(this));
        } else {
            Handler.sCurrentUser = sp_value;
            Handler.init_bookings();
        }


        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Handler.sCurrentUser.equals("%empty.value%")) {
            finish();
        } else {
            Handler.init_bookings();
        }
    }

    private void hideLoading(){
        mTVLoading.setVisibility(View.GONE);
        canShowBooking = true;
    }


    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(JSON_LINK)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(myResponse);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                int price = jsonObject.getInt("price");
                                String facilities = jsonObject.getString("facilities");
                                String address = jsonObject.getString("address");
                                String image = jsonObject.getString("image");
                                double latitude = jsonObject.getDouble("LAT");
                                double longitude = jsonObject.getDouble("LNG");
                                Handler.sBHouses.add(new BHouse(id,image,name,facilities,price,address,latitude,longitude));
                            }

                            BoardingHouseAdapter BHouseAdapter = new BoardingHouseAdapter(getApplicationContext(), Handler.sBHouses);
                            mRVBoardingHouse.setAdapter(BHouseAdapter);
                            hideLoading();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("halo", "error bang");
                        }
                    }
                });
            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Exit Application");
        alertDialog.setMessage("Are you sure you want to quit?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.show();
    }
}
