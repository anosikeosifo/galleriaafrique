package com.galleriafrique.util.tools;

import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by osifo on 10/5/15.
 */
public class Strings {

    public static final String EMPTY = "";

    public static boolean isTextEmpty(EditText editText) {
        return editText.getText().toString().trim().equals("");
    }

    public static String getStringFromView(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getStringFromView(Spinner spinner) {
        return (String) spinner.getSelectedItem();
    }
}
