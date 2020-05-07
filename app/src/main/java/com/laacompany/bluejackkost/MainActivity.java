package com.laacompany.bluejackkost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

    private RecyclerView mRVBoardingHouse;

    private static final String json_link =  "https://bit.ly/2zd4uhX";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        mRVBoardingHouse = findViewById(R.id.id_main_rv_boarding_house);

        mRVBoardingHouse.setLayoutManager(new LinearLayoutManager(this));
        Handler.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();



        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(json_link)
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
                                Log.d("halo", image);
                                Handler.sBHouses.add(new BHouse(id,image,name,facilities,price,address,longitude,latitude));
                            }

                            BoardingHouseAdapter BHouseAdapter = new BoardingHouseAdapter(getApplicationContext(), Handler.sBHouses);
                            mRVBoardingHouse.setAdapter(BHouseAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("halo", "error bang");
                        }
                    }
                });
            }
        });
    }
}
