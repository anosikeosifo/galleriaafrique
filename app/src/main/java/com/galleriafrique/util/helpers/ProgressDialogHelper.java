package com.galleriafrique.util.helpers;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by osifo on 10/5/15.
 */
public class ProgressDialogHelper {

    private ProgressDialog progressDialog;
    private Context context;

    public ProgressDialogHelper(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        this.context = context;
    }

    public void showProgress(String message) {
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public void dismissProgress() {
        if (progressDialog.isShowing()) {
            try {
                progressDialog.cancel();
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
