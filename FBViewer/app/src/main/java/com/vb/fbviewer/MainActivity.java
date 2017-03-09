package com.vb.fbviewer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * An applicaion main activity.
 */
public class MainActivity extends SingleFragmentActivity
    implements HomeFragmentVk.Callbacks {
    private static final String TAG = "MainActivity";

    private AccessTokenTracker mAccessTokenTracker;
    private VKAccessTokenTracker mVkAccessTokenTracker;

    /**
     * Specify which fragment create.
     * @return fragment.
     * @see SingleFragmentActivity
     */
    @Override
    protected Fragment createFragment() {
        if(isLoggedFB())
            return HomeFragment.newInstance();
        if(VKSdk.wakeUpSession(getApplicationContext()))
        {
            return HomeFragmentVk.newInstance();
        }
        return LoginFragment.newInstance();
    }

    /**
     * This will be performed in onCreate.
     * Tracks Access token to know if user is logged in.
     * @see SingleFragmentActivity
     */
    @Override
    protected void onCreateMethod() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Fresco.initialize(this);

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                replaceFragment();
                if (newAccessToken == null) {
                    Log.i(TAG, "User Logged Out.");
                }
            }
        };
        mAccessTokenTracker.startTracking();

        mVkAccessTokenTracker = new VKAccessTokenTracker() {
            @Override
            public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
                replaceFragment();
                if (newToken == null) {
                    Log.i(TAG, "User Logged Out.");
                }
            }
        };
        mVkAccessTokenTracker.startTracking();
    }

    /**
     * A callback from HomeFragmentVk. On choose user.
     * @param friend friend user.
     * @see com.vb.fbviewer.HomeFragmentVk.Callbacks
     */
    @Override
    public void onFriendSelected(UserVk friend) {
        Log.d(TAG, "User clicked!");

        Intent intent = MessengerVkActivity.newIntent(this, friend.getID(), friend.getFirstName() + " " + friend.getLastName());
        startActivity(intent);
    }

    /**
     * Is facebook logged in.
     * @return
     */
    private boolean isLoggedFB()
    {
        return AccessToken.getCurrentAccessToken() != null;
    }
}
