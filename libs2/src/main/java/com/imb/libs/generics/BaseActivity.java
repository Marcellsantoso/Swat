package com.imb.libs.generics;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.imb.libs.R;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by marcelsantoso.
 * <p/>
 * 5/31/15
 */
public abstract class BaseActivity extends RoboActionBarActivity {
    @InjectView(R.id.toolbar)
    private Toolbar toolbar;
    private int containerId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFont();
        setContentView(layout());
        setActionBar();

        setContainerId(R.id.fl);
        if (fragmentSplash() != null)
            showSplash();
        else if (fragmentDefault() != null)
            setFragment(fragmentDefault());
    }

    // ================================================================================
    // Config
    // ================================================================================
    public abstract Fragment fragmentDefault();

    public Fragment fragmentSplash() {
        return null;
    }

    public int layout() {
        return R.layout.activity_base;
    }

    public int bgRes() {
        return 0;
    }

    public int theme() {
        return R.style.CustomActionBarTheme;
    }

    public String bgUrl() {
        return "";
    }

    public String fontPath() {
        return "";
    }

    public static boolean isDebugging() {
        return BaseConstants.IS_DEBUGGING;
    }

    public static String log() {
        return BaseConstants.LOG;
    }

    public long splashTimer() {
        return BaseConstants.TIMER_SPLASH;
    }

    public String appName() {
        return getString(R.string.app_name);
    }

    // ================================================================================
    // ActionBar
    // ================================================================================
    public Toolbar toolbar() {
        return this.toolbar;
    }

    public void toolbarTitle(String title) {
        this.toolbar.setTitle(title);
    }

    public void toolbarTitle(int resTitle) {
        this.toolbar.setTitle(resTitle);
    }

    public void toolbarHide() {
        this.toolbar.setVisibility(View.GONE);
    }

    public void toolbarShow() {
        this.toolbar.setVisibility(View.VISIBLE);
    }

    public void toolbarOpaque(int opaque) {
        this.toolbar.setAlpha((float) opaque);
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(appName());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarShow();
        toolbarOpaque(255);
    }

    // ================================================================================
    // Font
    // ================================================================================
    public void setFont() {
        if (!Helper.isEmpty(fontPath()))
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                                  .setDefaultFontPath(fontPath())
                                                  .setFontAttrId(R.attr.fontPath)
                                                  .build());
    }

    // @Override
    //    protected void attachBaseContext(Context newBase) {
    //        if (!Helper.isEmpty(fontPath()))
    //            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    //    }

    // ================================================================================
    // Fragment
    // ================================================================================
    public void showSplash() {
        setFragment(fragmentSplash());
        new CountDownTimer(splashTimer() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                clearFragment();
                setFragment(fragmentDefault());
            }
        }.start();
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public void addFragment(Fragment frag) {
        addFragment(this.containerId, frag);
    }

    public void setFragment(Fragment frag) {
        setFragment(this.containerId, frag);
    }

    /**
     * Add fragment on top of the current one
     *
     * @param frag
     */
    public void addFragment(int containerId, Fragment frag) {
        if (containerId > 0) {
            getSupportFragmentManager().beginTransaction()
                                       .add(containerId, frag).addToBackStack(null).commit();

            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            // Hide keyboard by default
            Helper.hideKeyboard(this);
        }
    }

    /**
     * Change to new fragment
     *
     * @param frag
     */
    public void setFragment(int containerId, Fragment frag) {
        if (containerId > 0) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(containerId, frag).addToBackStack(null)
                                       .commit();

            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // setSupportProgressBarIndeterminateVisibility(false);

            // Hide keyboard by default when changing fragment
            Helper.hideKeyboard(this);
        }
    }

    /**
     * Clear all fragments
     */
    public void clearFragment() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Remove top of fragments
     */
    public void popBackstack() {
        getSupportFragmentManager().popBackStack();
    }

}
