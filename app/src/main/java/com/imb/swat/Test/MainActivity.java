package com.imb.swat.test;

import android.support.v4.app.Fragment;

import com.imb.swat.generics.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    public Fragment fragmentDefault() {
        return new FragmentTest();
    }
}
