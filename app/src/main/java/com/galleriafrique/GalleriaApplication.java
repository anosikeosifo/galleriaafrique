package com.galleriafrique;

import android.app.Application;
import android.content.Context;

/**
 * Created by osifo on 8/3/15.
 */
public class GalleriaApplication extends Application{
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = this;
    }

    public static Context getContext() {
        return applicationContext;
    }

}
