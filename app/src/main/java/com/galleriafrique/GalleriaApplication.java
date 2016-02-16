package com.galleriafrique;

import android.app.Application;
import android.content.Context;
import java.io.File;
import com.galleriafrique.Constants;
import com.galleriafrique.util.CommonUtils;
import com.facebook.FacebookSdk;

import java.io.File;

/**
 * Created by osifo on 1/29/16.
 */
public class GalleriaApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "xWSlZ5OFEZp6DH2ump2pNEFud";
    private static final String TWITTER_SECRET = "rTsDI8bwqnMfCkLh9uUUajN24hYE54SvAjUBfh6jPtFq4SqTX1 ";

    private static Context applicationContext;
    private static String directoryPath;
    private File imageDirectory;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        CommonUtils.log("inside application create");
        CommonUtils.log(applicationContext);

        imageDirectory = applicationContext.getDir(Constants.IMAGE_DIRECTORY_NAME, Context.MODE_PRIVATE);

        if (!imageDirectory.exists()) {
            imageDirectory.mkdir();
        }

        directoryPath = imageDirectory.getAbsolutePath();

        //initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static Context getContext() {
        CommonUtils.log(applicationContext);
        return applicationContext;
    }

    public static String getImageDirectory() {
        return directoryPath;
    }
}