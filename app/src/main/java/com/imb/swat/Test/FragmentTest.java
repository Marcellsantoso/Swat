package com.imb.swat.test;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.imb.swat.R;
import com.imb.swat.generics.BaseFragment;
import com.imb.swat.http.HTTPImb;

import org.json.JSONObject;

import roboguice.inject.InjectView;

/**
 * Created by marcelsantoso on 5/31/15.
 */
public class FragmentTest extends BaseFragment {
    @InjectView(R.id.tv)
    private TextView tv;

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
        new HTTPImb(this, true) {
            @Override
            public void onSuccess(JSONObject j) {
                tv.setText(j.toString());
            }

            @Override
            public String url() {
                return "http://ll.indomegabyte.com/StorePortalWeb/StorePortal?cmd=ws&mws=getByPageWhere";
            }
        }.setGetParams("search", "bintaro").execute();
    }
}
