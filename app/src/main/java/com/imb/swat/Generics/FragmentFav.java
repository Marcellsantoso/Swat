package com.imb.swat.generics;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.imb.swat.adapter.AdapterList;
import com.imb.swat.helper.Preference;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFav extends BaseFragmentTab {
    AdapterList adapter;

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        ld.hide();
        sr.setEnabled(false);

        adapter = new AdapterList(getActivity()) {
            @Override
            public void reload() {
                adapter.convertWithFilter(getPref().getString(Preference.LIST_DATA),
                                          getPref().getString(Preference.LIST_FAV),
                                          getPref().getString(Preference.LIST_FAV));
            }
        };
        adapter.reload();

        super.setView(view, savedInstanceState);
    }

    @Override
    public AdapterList adapter() {
        return adapter;
    }
}
