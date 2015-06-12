package com.imb.swat.test;

import com.imb.swat.generics.BaseActivityTab;

public class MainActivity extends BaseActivityTab {
    @Override
    public String url() {
        return "http://192.168.1.44:8888/test_listing/test.php";
    }
}
