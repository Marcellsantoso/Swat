package com.imb.swat.generics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.imb.swat.Bean.BeanImb;
import com.imb.swat.R;
import com.imb.swat.adapter.AdapterList;
import com.imb.swat.helper.HelperList;
import com.imb.swat.views.LoadingCompound;

import roboguice.inject.InjectView;

public abstract class BaseFragmentTab extends BaseFragment implements AdapterView.OnItemLongClickListener {
    @InjectView(R.id.ld)
    LoadingCompound    ld;
    @InjectView(R.id.lv)
    ListView           lv;
    @InjectView(R.id.sr)
    SwipeRefreshLayout sr;
    MyReceiver r;

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
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setAdapter(adapter());
    }

    public abstract AdapterList adapter();

    @Override
    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r,
                                                                          new IntentFilter("TAG_REFRESH"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    public void refresh() {
        adapter().reload();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        HelperList.addToRecent(getPref(), ((BeanImb) adapter().getItem(position)).getId());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final BeanImb bean = (BeanImb) adapter().getItem(position);
        CharSequence  options[];
        if (!bean.isFav())
            options = new CharSequence[]{"Add to favourite"};
        else
            options = new CharSequence[]{"Remove from favourite"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HelperList.addToFav(adapter(), bean.getId());
            }
        });
        builder.show();

        return true;
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    }
}
