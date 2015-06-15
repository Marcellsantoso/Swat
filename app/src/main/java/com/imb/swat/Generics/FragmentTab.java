package com.imb.swat.generics;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.imb.swat.R;
import com.imb.swat.adapter.TabsAdapter;
import com.imb.swat.views.CustomViewPager;

import java.util.Locale;

import roboguice.inject.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTab extends BaseFragment {
    @InjectView(R.id.vpTab)
    private CustomViewPager vp;
    @InjectView(android.R.id.tabhost)
    private TabHost         tabHost;
    private TabsAdapter     tabsAdapter;

    @Override
    public int layout() {
        return R.layout.fragment_tab;
    }

    @Override
    public int menuLayout() {
        return 0;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        setupTab(view, savedInstanceState);
    }
    
    public void setupTab(View view, Bundle savedInstance) {
        tabsAdapter = new TabsAdapter(this, tabHost, vp) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
                Intent                i   = new Intent("TAG_REFRESH");
                lbm.sendBroadcast(i);
            }
        };

        tabHost.setup();
        tabsAdapter.addTab(tabHost.newTabSpec(getHomeTab().tab1Text()).setIndicator(getHomeTab().tab1Text()),
                           getHomeTab().tab1(), null);
        tabsAdapter.addTab(tabHost.newTabSpec(getHomeTab().tab2Text()).setIndicator(getHomeTab().tab2Text()),
                           getHomeTab().tab2(), null);
        tabsAdapter.addTab(tabHost.newTabSpec(getHomeTab().tab3Text()).setIndicator(getHomeTab().tab3Text()),
                           getHomeTab().tab3(), null);

        adjustTabHost(tabHost, getHomeTab().tab(), getHomeTab().textColor());

        if (savedInstance != null) {
            tabHost.setCurrentTabByTag(savedInstance.getString("tab"));
        }
    }

    public void adjustTabHost(
            TabHost mTabHost, int drawableResId,
            int textColorResId) {
        if (mTabHost == null || getActivity() == null) {
            return;
        }
        try {

            for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                Drawable d = getActivity().getResources().getDrawable(drawableResId);

                int sdk = android.os.Build.VERSION.SDK_INT;
                ViewGroup tab = null;
                TextView t = null;
                try {
                    tab = (ViewGroup) mTabHost.getTabWidget().getChildAt(i);
                    t = (TextView) tab.findViewById(android.R.id.title);
                    t.setTextColor(getActivity().getResources().getColor(
                            textColorResId));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (tab == null) {
                    return;
                }

                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    tab.setBackgroundDrawable(d); // unselected
                } else {
                    tab.setBackground(d); // unselected
                }

                if (sdk < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    // only for android older than ICS
                    // manually adjust

                    try {
                        // TextView tv = (TextView) v.getChildAt(1);
                        TextView tv = t;
                        tv.setText(tv.getText().toString()
                                     .toUpperCase(Locale.ENGLISH));
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.CENTER_IN_PARENT);
                        tv.setLayoutParams(params);
                        tv.setTextColor(Color.WHITE);
                        tv.setGravity(Gravity.CENTER);
                    } catch (Resources.NotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (ClassCastException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        } catch (Resources.NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
