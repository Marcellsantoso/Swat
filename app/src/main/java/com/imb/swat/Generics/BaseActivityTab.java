package com.imb.swat.generics;

import android.graphics.Color;
import android.support.v4.app.Fragment;

import com.imb.swat.R;

import java.util.List;

public abstract class BaseActivityTab extends BaseActivity {
    @Override
    public Fragment fragmentDefault() {
        return new FragmentTab();
    }

    public abstract String url();

    public Class<?> tab1() {
        return FragmentList.class;
    }

    public Class<?> tab2() {
        return FragmentRecent.class;
    }

    public Class<?> tab3() {
        return FragmentFav.class;
    }

    public String tab1Text() {
        return "List";
    }

    public String tab2Text() {
        return "Recent";
    }

    public String tab3Text() {
        return "Favourites";
    }

    public int tab() {
        return R.drawable.tab_indicator_swat;
    }

    public int tabColorBg() {
        return Color.TRANSPARENT;
    }

    public int tabColorText() {
        return Color.WHITE;
    }

    @Override
    public void onBackPressed() {
        List<Fragment> list = getSupportFragmentManager().getFragments();
        Fragment       frag = list.get(list.size() - 1);
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1 || frag instanceof FragmentTab) {
            this.finish();
        } else
            super.onBackPressed();
    }
}
