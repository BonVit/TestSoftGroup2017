package com.vb.fbviewer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.dialogs.VKCaptchaDialog;

import java.util.Arrays;

/**
 * Created by bonar on 3/5/2017.
 */

/**
 * Fragment for logging in.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private static final String[] VK_SCOPE = {VKScope.FRIENDS, VKScope.MESSAGES, VKScope.EMAIL};

    LoginButton mLoginButton;
    Button mVkLoginButton;
    CallbackManager callbackManager;

    /**
     * New instance of LoginFragment.
     * @return
     * @see LoginFragment
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    /**
     * This method performs when on creating view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginButton = (LoginButton) v.findViewById(R.id.login_button);
        mLoginButton.setFragment(this);

        callbackManager = CallbackManager.Factory.create();
        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i(TAG, "OnSuccessLogin");
            }

            @Override
            public void onCancel() {
                // App code
                Log.i(TAG, "OnCancelLogin");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i(TAG, "OnErrorLogin");
            }
        });

        mVkLoginButton = (Button) v.findViewById(R.id.vk_login_button);
        mVkLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(getActivity(), VK_SCOPE);
            }
        });

        return v;
    }

    /**
     * On activity result. Used for getting logging callbacks from fb and vk.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.i(TAG, res.toString());
            }
            @Override
            public void onError(VKError error) {
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
