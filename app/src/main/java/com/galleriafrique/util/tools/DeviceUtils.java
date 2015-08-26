package com.galleriafrique.util.tools;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.galleriafrique.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osifo on 8/23/15.
 */
public class DeviceUtils {

    public static void vibrateDevice(Context context, int duration) {
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(duration);
    }

    public static void toSettings(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                context.startActivity(new Intent(
                                        Settings.ACTION_SETTINGS)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        try {
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(final Activity activity, final View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }, 100);
    }

    public static void showKeyboard(final Activity activity, final View view) {
        view.requestFocus();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 100);
    }

    public static String getPhoneContacts(Activity activity) {
        List<String> phoneContacts = new ArrayList<String>();

        ContentResolver contentResolver = activity.getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                null, null);
        // use the cursor to access the contacts

        while (cursor.moveToNext()) {

            if (cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)) != null) {

                String phoneNumber = CommonUtils.formatPhoneNumber(cursor
                        .getString(cursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                phoneContacts.add(phoneNumber);

                CommonUtils.log(phoneNumber);
            }
        }

        cursor.close();

        String myContacts = "[";

        int counter = 0;
        for (String number : phoneContacts) {
            if (counter == (phoneContacts.size() - 1)) {
                myContacts += "\""+number+"\"";
            } else {
                myContacts += "\""+number+"\",";
            }
            counter++;
        }

        myContacts += "]";

        return  myContacts;
    }

    public static int getScreenWidth(Activity activity) {
        return activity.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        return activity.getResources().getDisplayMetrics().heightPixels;
    }
}