package com.imb.swat.test;

import android.graphics.Color;

import com.imb.swat.generics.BaseActivityTab;

public class MainActivity extends BaseActivityTab {
    @Override
    public String url() {
        return "http://192.168.1.48:8888/test_listing/test.php";
    }

    @Override
    public int tabColorBg() {
        return Color.parseColor("#09bcd3");
    }

    @Override
    public int tabColorText() {
        return Color.parseColor("#ffffff");
    }

    @Override
    public String toolbarTitle() {
        return "Listing Demo";
    }

    @Override
    public int toolbarColorBg() {
        return Color.parseColor("#09bcd3");
    }

    @Override
    public int toolbarColorText() {
        return Color.parseColor("#ffffff");
    }
}
