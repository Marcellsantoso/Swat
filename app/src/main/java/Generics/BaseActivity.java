package Generics;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import swat.imb.com.swat.R;

/**
 * Created by marcelsantoso on 5/31/15.
 */
public abstract class BaseActivity extends RoboActionBarActivity {
    @InjectView(R.id.toolbar)
    private Toolbar toolbar;
    private int containerId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(layout());

        setFragment(defaultFragment());
    }

    // ================================================================================
    // Config
    // ================================================================================
    public abstract Fragment defaultFragment();

    public Fragment splashFragment() {
        return null;
    }

    public int layout() {
        return R.layout.activity_base;
    }

    public int bgRes() {
        return 0;
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

    // ================================================================================
    // Fragment
    // ================================================================================
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
