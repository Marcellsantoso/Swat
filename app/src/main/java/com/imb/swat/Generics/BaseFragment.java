package com.imb.swat.generics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.imb.swat.R;
import com.imb.swat.helper.Preference;

import java.lang.reflect.Field;

import roboguice.fragment.RoboFragment;

/**
 * Created by marcelsantoso on 5/31/15.
 */
public abstract class BaseFragment extends RoboFragment implements View.OnClickListener,
                                                                   AdapterView.OnItemClickListener,
                                                                   View.OnLongClickListener,
                                                                   AbsListView.OnScrollListener,
                                                                   ViewTreeObserver.OnScrollChangedListener,
                                                                   View.OnTouchListener,
                                                                   SwipeRefreshLayout.OnRefreshListener {
    private static final Field            sChildFragmentManagerField;

    // ================================================================================
    // Utilities
    // ================================================================================
    // To prevent error in implementing nested fragment
    static {
        Field f = null;
        try {
            f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            // Error getting mChildFragmentManager field
            e.printStackTrace();
        }
        sChildFragmentManagerField = f;
    }

    private              ScrollView       sv;
    private              ViewTreeObserver observer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (layout() > 0)
            return inflater.inflate(layout(), container, false);
        else return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (sChildFragmentManagerField != null) {
            try {
                sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
                // Error setting mChildFragmentManager field
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int resLayout = menuLayout();
        if (resLayout > 0)
            inflater.inflate(menuLayout(), menu);

        if (BaseActivity.isDebugging() && refreshPage())
            inflater.inflate(R.menu.refresh_white, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            onRefreshPage();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view, savedInstanceState);
    }

    // ================================================================================
    // Config
    // ================================================================================
    public abstract int layout();

    public abstract int menuLayout();

    public abstract void setView(View view, Bundle savedInstanceState);

    public boolean refreshPage() {
        return false;
    }

    public void onRefreshPage() {
    }

    // ================================================================================
    // Fragment
    // ================================================================================
    public void clearFragment() {
        if (getHome() != null)
            getHome().clearFragment();
    }

    public void setFragment(Fragment frag) {
        if (getHome() != null)
            getHome().setFragment(frag);
    }

    public void setFragment(Fragment frag, int resParent) {
        if (getHome() != null)
            getHome().setFragment(resParent, frag);
    }

    public BaseActivity getHome() {
        return (BaseActivity) getActivity();
    }

    public void log(String text) {
        if (BaseActivity.isDebugging())
            Log.d(BaseActivity.log(), text);
    }

    public void onBackPressed() {
        if (getHome() != null)
            getHome().onBackPressed();
    }

    public void setToolbarOpacity(int opacity) {
        // Max opacity will be 255, as per android's code
        if (opacity > 255)
            opacity = 255;
        Toolbar toolbar = getHome().toolbar();
        if (toolbar != null)
            toolbar.getBackground().setAlpha(opacity);
    }

    public void setScrollListener(ScrollView sv) {
        this.sv = sv;

        sv.setOnTouchListener(this);
    }

    public Preference getPref() {
        return getHome().getPref();
    }

    // ================================================================================
    // Listeners
    // ================================================================================
    @Override
    public void onRefresh() {
    }

    @Override
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        View v = view.getChildAt(0);
        int scrollY = (v == null) ? 0 : -v.getTop() + view.getFirstVisiblePosition()
                * v.getHeight();

        setToolbarOpacity(scrollY);
    }

    @Override
    public void onScrollChanged() {
        setToolbarOpacity(sv.getScrollY());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (observer == null) {
            observer = sv.getViewTreeObserver();
            observer.addOnScrollChangedListener(this);
        } else if (!observer.isAlive()) {
            observer.removeOnScrollChangedListener(this);
            observer = sv.getViewTreeObserver();
            observer.addOnScrollChangedListener(this);
        }

        return false;
    }
}

