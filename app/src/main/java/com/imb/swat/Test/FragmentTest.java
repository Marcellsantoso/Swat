package com.imb.swat.test;

import android.os.Bundle;
import android.view.View;

import com.imb.swat.generics.BaseFragment;
import com.imb.swat.R;

/**
 * Created by marcelsantoso on 5/31/15.
 */
public class FragmentTest extends BaseFragment {
    @Override
    public int layout() {
        return R.layout.test_fragment;
    }

    @Override
    public int menuLayout() {
        return 0;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {

    }
}
