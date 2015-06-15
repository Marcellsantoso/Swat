package com.imb.swat.generics;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.imb.swat.adapter.AdapterList;
import com.imb.swat.helper.Preference;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRecent extends BaseFragmentTab {
    AdapterList adapter;

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        ld.hide();
        sr.setEnabled(false);
        initList();

        super.setView(view, savedInstanceState);
    }

    @Override
    public AdapterList adapter() {
        return adapter;
    }

    private void initList() {
        adapter = new AdapterList(getActivity()) {
            @Override
            public void reload() {
                adapter.convertWithFilter(getHomeTab().getPref().getString(Preference.LIST_DATA),
                                          getHomeTab().getPref().getString(Preference.LIST_FAV),
                                          getHomeTab().getPref().getString(Preference.LIST_RECENT));
            }
        };
        adapter.reload();
    }

}
