package com.imb.libs.generics;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragmentTab extends BaseFragment {
    @Override
    public int layout() {
        return 0;
    }

    @Override
    public int menuLayout() {
        return 0;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {

    }
}
