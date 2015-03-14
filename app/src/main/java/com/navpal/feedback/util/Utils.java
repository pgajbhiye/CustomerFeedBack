package com.navpal.feedback.util;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author Pallavi
 */
public class Utils {


    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }

    public static String trimStatus(String status){
        if(status!=null && status.length()>0){
            return String.valueOf(status.charAt(0)).toUpperCase();
         }
        return "";
    }

    public static  String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yy");
        String prettyDate = format.format(date);
        return prettyDate;

    }
}
