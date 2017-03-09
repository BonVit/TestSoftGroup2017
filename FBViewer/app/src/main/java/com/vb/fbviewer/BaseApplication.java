package com.vb.fbviewer;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by bonar on 3/8/2017.
 */

/**
 * Application.
 * Uses to initialize vk sdk.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
