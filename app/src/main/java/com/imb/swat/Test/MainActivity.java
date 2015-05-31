package com.imb.swat.Test;

import android.support.v4.app.Fragment;

import com.imb.swat.Generics.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    public Fragment defaultFragment() {
        return new FragmentTest();
    }
}
