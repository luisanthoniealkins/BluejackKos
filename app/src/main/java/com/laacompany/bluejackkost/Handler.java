package com.laacompany.bluejackkost;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Handler {

    public static ArrayList<BHouse> sBHouses;

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }



}
