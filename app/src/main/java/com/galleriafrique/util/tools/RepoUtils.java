package com.galleriafrique.util.tools;

import android.content.Context;

import com.galleriafrique.Constants;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.network.NetworkHelper;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by osifo on 8/23/15.
 */
public class RepoUtils {
    private static RestAdapter restAdapter;

    public static RestAdapter getAPIRestAdapter(Context context, String endpoint, NetworkHelper networkHelper) {
        if (networkHelper != null && networkHelper.isNetworkConnected(context)) {
            if (restAdapter == null) {
                restAdapter = new RestAdapter.Builder().setEndpoint(endpoint)
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setLog(new AndroidLog("RETROFIT_LOG"))
                        .build();
            }
            return restAdapter;
        } else {
            CommonUtils.toast(context, "Please check your internet connection");
            DeviceUtils.toSettings(context);
            return null;
        }
    }
}
