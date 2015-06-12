package com.imb.swat.generics;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.imb.swat.Bean.BeanImb;
import com.imb.swat.R;
import com.imb.swat.adapter.AdapterList;
import com.imb.swat.helper.HelperList;
import com.imb.swat.helper.Preference;
import com.imb.swat.helper.UIHelper;
import com.imb.swat.http.HTTPImbLd;
import com.imb.swat.views.LoadingCompound;

import org.json.JSONObject;

import roboguice.inject.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentList extends BaseFragment {
    @InjectView(R.id.ld)
    LoadingCompound    ld;
    @InjectView(R.id.lv)
    ListView           lv;
    @InjectView(R.id.sr)
    SwipeRefreshLayout sr;
    AdapterList adapter;

    @Override
    public int layout() {
        return R.layout.fragment_list;
    }

    @Override
    public int menuLayout() {
        return 0;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        if (adapter == null) {
            adapter = new AdapterList(getActivity());
            loadItems();
        } else {
            ld.hide();
        }

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        sr.setOnRefreshListener(this);
        UIHelper.setRefreshColor(sr);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        loadItems();
    }

    private void loadItems() {
        new HTTPImbLd(this, ld) {
            @Override
            public void onSuccess(JSONObject j) {
                sr.setRefreshing(false);
                getPref().setString(Preference.LIST_DATA, j.toString());
                adapter.clear();
                adapter.convert(j.toString());
            }

            @Override
            public void onFail(int code, String message) {
                super.onFail(code, message);
                sr.setRefreshing(false);
            }

            @Override
            public String url() {
                return ((BaseActivityTab) getActivity()).url();
            }
        }.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        //        if (HelperList.addToFav(getPref(), ((BeanImb) adapter.getItem(position)).getId())) {
        //            Toast.makeText(getActivity(), "Added to favourite", Toast.LENGTH_SHORT).show();
        //        } else Toast.makeText(getActivity(), "Removed from favourite", Toast.LENGTH_SHORT).show();
        HelperList.addToRecent(getPref(), ((BeanImb) adapter.getItem(position)).getId());
    }
}
