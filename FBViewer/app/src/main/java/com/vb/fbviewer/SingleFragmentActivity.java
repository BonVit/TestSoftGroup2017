package com.vb.fbviewer;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by bonar on 2/17/2017.
 */

/**
 * An abstract class that provides tool for creating activity with 1 fragment.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "SingleFragmentActivity";

    /**
     * A method that provides Fragment to create.
     * @return fragment to create.
     */
    protected abstract Fragment createFragment();

    /**
     * This method will be performed in onCreate.
     */
    protected abstract void onCreateMethod();

    /**
     *
     * @return layout resource with 1 fragment.
     */
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    /**
     *
     * @param savedInstanceState saved instance.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());

        onCreateMethod();

        replaceFragment();
    }

    /**
     * Replace current fragment.
     */
    protected void replaceFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        else
        {
            fragment = createFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}

