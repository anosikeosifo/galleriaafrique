package com.galleriafrique;

import android.app.Application;
import android.content.Context;
import java.io.File;
import com.galleriafrique.Constants;
import com.galleriafrique.util.CommonUtils;

import java.io.File;

/**
 * Created by osifo on 1/29/16.
 */
public class GalleriaApplication extends Application {
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
    }

    public static Context getContext() {
        CommonUtils.log(applicationContext);
        return applicationContext;
    }

    public static String getImageDirectory() {
        return directoryPath;
    }
}