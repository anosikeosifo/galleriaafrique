package com.galleriafrique.util.tools;

import com.galleriafrique.Constants;

/**
 * Created by osifo on 1/27/16.
 */
public class Validation {
    public static boolean isEmail(String text) {
        return Constants.EMAIL_PATTERN.matcher(text).matches();
    }

    public static boolean isStringEmpty(String text) {
        if (text != null && !text.equals("")) { return false; }
        return true;
    }
}