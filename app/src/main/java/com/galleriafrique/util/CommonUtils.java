package com.galleriafrique.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.galleriafrique.Constants;
import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.HomeActivity;
import com.galleriafrique.model.post.Post;
import com.google.gson.Gson;

import java.io.File;
import java.util.Date;

import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by osifo on 8/21/15.
 */
public class CommonUtils {
    private static Gson gson;

    public static String getSafeString(String string) {
        return string != null ? string.trim() : "";
    }

    public static String getSafeString(int value) {
        return String.valueOf(value) != null ? String.valueOf(value) : "";
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

    public static TypedString getTypedString(String string) {
        return new TypedString(getSafeString(string));
    }

    public static TypedFile getTypedFile(String string) {
        return new TypedFile("image/ong", new File(getSafeString(string)));
    }

    public static String URLEncode(String string) {
        return string.replace(" ", "%20");
    }

    public static String getTimeline(String time_stamp) {

        String time = "now";

        try {

            final Date createdDate = Constants.DATE_FORMAT.parse(time_stamp);
            final Date currentDate = new Date();

            // in milliseconds
            long diff = currentDate.getTime() - createdDate.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            long diffWeeks = diff / (7 * 24 * 60 * 60 * 1000);

            if (diffWeeks > 0) {
                return diffWeeks + "w";
            } else if (diffDays > 0) {
                return diffDays + "d";
            } else if (diffHours > 0) {
                return diffHours + "h";
            } else if (diffMinutes > 0) {
                return diffMinutes + "m";
            } else if (diffSeconds > 0) {
                return diffSeconds + "s";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
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

    public static void sharePost(HomeActivity activity, Post post) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//        try {
//            activity.startActivity(Intent.createChooser(intent, Constants.SHARE_WITH));
//        } catch (ActivityNotFoundException e) {
//            e.printStackTrace();
//            Toast.makeText(activity, activity.getString(R.string.no_activity_to_share), Toast.LENGTH_LONG).show();
//        }
    }

}
