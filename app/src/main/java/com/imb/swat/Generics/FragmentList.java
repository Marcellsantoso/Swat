package com.imb.swat.generics;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.imb.swat.adapter.AdapterList;
import com.imb.swat.helper.Preference;
import com.imb.swat.helper.UIHelper;
import com.imb.swat.http.HTTPImbLd;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentList extends BaseFragmentTab {
    AdapterList adapter;

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        if (adapter == null) {
            adapter = new AdapterList(getActivity()) {
                @Override
                public void reload() {
                    adapter.convert(getPref().getString(Preference.LIST_DATA),
                                    getPref().getString(Preference.LIST_FAV));

                }
            };
            loadItems();
        } else {
            ld.hide();
        }
        adapter.reload();

        sr.setOnRefreshListener(this);
        UIHelper.setRefreshColor(sr);

        super.setView(view, savedInstanceState);
    }

    @Override
    public AdapterList adapter() {
        return adapter;
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
                adapter.convert(j.toString(), getPref().getString(Preference.LIST_FAV));
            }

            @Override
            public String url() {
                return ((BaseActivityTab) getActivity()).url();
            }

            @Override
            public void onFail(int code, String message) {
                super.onFail(code, message);
                sr.setRefreshing(false);
            }
        }.execute();
    }


}
