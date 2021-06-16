package com.example.apcentece.util;

import android.content.Context;
import android.content.Intent;

import java.util.Locale;

public class GenericUtils {
    public static boolean isNullOrEmpty(String str){
        if(str == null || str.equals("") || str.length() == 0){
            return true;
        }
        return false;
    }
    public static String getCountry(){
        Locale locale = Locale.getDefault();
        return locale.getCountry().toLowerCase();
    }
    public static void share(Context ctx, String url){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
        ctx.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
