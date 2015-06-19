package com.imb.swat.generics;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.imb.swat.R;
import com.imb.swat.helper.Helper;
import com.imb.swat.helper.Preference;
import com.imb.swat.helper.UIHelper;

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
    private Preference pref;

    public static boolean isDebugging() {
        return BaseConstants.IS_DEBUGGING;
    }

    public static String log() {
        return BaseConstants.LOG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFont();
        setContentView(layout());
        setActionBar();

        if (pref == null) {
            pref = Preference.getInstance(this);
        }

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

    public abstract Fragment fragmentDetails();

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

    public abstract String toolbarTitle();

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

    public int toolbarColorBg() {
        return Color.BLACK;
    }

    public int toolbarColorText() {
        return Color.BLACK;
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(appName());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar().setBackgroundColor(toolbarColorBg());
        toolbar().setTitle(toolbarTitle());
        toolbarShow();
        toolbarOpaque(255);

        // Change actionbar text color
        Spannable text = new SpannableString(toolbar().getTitle());
        text.setSpan(new ForegroundColorSpan(toolbarColorText()), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        toolbar().setTitle(text);
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
    // Shared Preference
    // ================================================================================
    public Preference getPref() {
        return this.pref;
    }

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
        setFragment(this.containerId, frag, 0, 0);
    }

    public void setFragment(Fragment frag, int resParent) {
        setFragment(resParent, frag, 0, 0);
    }

    public void setFragmentAnim(Fragment frag, int resIn, int resOut) {
        setFragment(this.containerId, frag, resIn, resOut);
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
            UIHelper.hideKeyboard(this);
        }
    }

    /**
     * Change to new fragment
     *
     * @param frag
     */
    public void setFragment(int containerId, Fragment frag, int resIn, int resOut) {
        if (containerId > 0) {
            if (resIn == 0 && resOut == 0)
                getSupportFragmentManager().beginTransaction()
                                           .replace(containerId, frag).addToBackStack(null)
                                           .commit();
            else
                getSupportFragmentManager().beginTransaction()
                                           .setCustomAnimations(resIn, resOut)
                                           .replace(containerId, frag).addToBackStack(null)
                                           .commit();


            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // setSupportProgressBarIndeterminateVisibility(false);

            // Hide keyboard by default when changing fragment
            UIHelper.hideKeyboard(this);
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
