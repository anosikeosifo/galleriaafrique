package com.galleriafrique.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.galleriafrique.Constants;
import com.google.gson.Gson;

/**
 * Created by osifo on 8/21/15.
 */
public class CommonUtils {
    private static Gson gson;

    public static String getSafeString(String string) {
        return string != null ? string.trim() : "";
    }

    public static void log(Object log) {
        Log.i(Constants.TAG, String.valueOf(log));
    }

    public static String formatPhoneNumber(String phone) {

        if (phone != null) {
            phone = phone.replace("+", "00");
            phone = phone.replaceAll("[^\\d]", "");
            phone = phone.replace(" ", "");
        } else {
            phone = "";
        }

        return phone;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static void call(Activity activity, String mobile) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mobile));
            activity.startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sms(Activity activity, String mobile) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("sms:" + mobile));
            activity.startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
