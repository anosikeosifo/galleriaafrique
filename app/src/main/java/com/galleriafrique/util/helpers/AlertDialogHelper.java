package com.galleriafrique.util.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by osifo on 1/11/16.
 */
public class AlertDialogHelper {
    private Context context;
    private AlertDialog.Builder alertDialogBuilder;

    public AlertDialogHelper(Context context) {
        this.context = context;
        alertDialogBuilder = new AlertDialog.Builder(context);
    }


    public void showDialog() {
        // set title
        alertDialogBuilder.setTitle("Your Title");

        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
//                        MainActivity.this.finish();
                    }
                })
                        // set dialog message

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

}
