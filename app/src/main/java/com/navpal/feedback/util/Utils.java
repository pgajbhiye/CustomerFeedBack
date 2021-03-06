package com.navpal.feedback.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.navpal.feedback.helpers.UserDetails;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Author Pallavi
 */
public class Utils {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static Pattern pattern;

    static{
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

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
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        String prettyDate = format.format(date);
        return prettyDate;

    }

    public static boolean isValidEmail(String emailId){
          return pattern.matcher(emailId).matches();

    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static void saveUser(Context inContext, UserDetails userDetails){
        SharedPreferences.Editor editor = inContext.getSharedPreferences(Config.APP_PREFS_NAME, inContext.MODE_PRIVATE).edit();
        editor.putString("name", userDetails.getName());
        editor.putString("emailId", userDetails.getEmailId());
        editor.putBoolean("firstTime", userDetails.isFirstTime());
        editor.commit();
    }

    public static UserDetails getUser(Context inContext){
        SharedPreferences prefs = inContext.getSharedPreferences(Config.APP_PREFS_NAME, inContext.MODE_PRIVATE);
        UserDetails userDetails = new UserDetails();
        userDetails.setName(prefs.getString("name", null));
        userDetails.setEmailId(prefs.getString("emailId", null));
        userDetails.setFirstTime(prefs.getBoolean("firstTime", true));
        return userDetails;
    }
}
