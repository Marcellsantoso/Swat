package com.imb.swat.generics;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.imb.swat.R;
import com.imb.swat.adapter.AdapterList;
import com.imb.swat.helper.Preference;

import roboguice.inject.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFav extends BaseFragment {
    @InjectView(R.id.lv)
    ListView lv;
    AdapterList adapter;

    @Override
    public int layout() {
        return R.layout.fragment_fav;
    }

    @Override
    public int menuLayout() {
        return 0;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        adapter = new AdapterList(getActivity());
        adapter.convertWithFilter(getPref().getString(Preference.LIST_DATA), getPref().getString(Preference.LIST_FAV));
        lv.setAdapter(adapter);
    }
}
