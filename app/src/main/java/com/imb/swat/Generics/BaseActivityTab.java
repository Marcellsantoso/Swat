package com.imb.swat.generics;

import android.support.v4.app.Fragment;

public abstract class BaseActivityTab extends BaseActivity {
    @Override
    public Fragment fragmentDefault() {
        return new FragmentTab();
    }

    public abstract String url();
}
