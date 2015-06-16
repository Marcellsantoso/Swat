package com.imb.swat.generics;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.imb.swat.R;
import com.imb.swat.helper.Helper;
import com.imb.swat.views.ImageViewLoader;

import roboguice.inject.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetails extends BaseFragment {
    @InjectView(R.id.btnCall)
    ImageButton     btnCall;
    @InjectView(R.id.btnEmail)
    ImageButton     btnEmail;
    @InjectView(R.id.btnWeb)
    ImageButton     btnWeb;
    @InjectView(R.id.imgHeader)
    ImageViewLoader imgHeader;
    @InjectView(R.id.sv)
    ScrollView      sv;
    private final int TAG_CALL = 1, TAG_EMAIL = 2, TAG_WEB = 3;

    @Override
    public int layout() {
        return R.layout.fragment_details;
    }

    @Override
    public int menuLayout() {
        return 0;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        if (getData() == null)
            return;

        btnCall.setTag(TAG_CALL);
        btnEmail.setTag(TAG_EMAIL);
        btnWeb.setTag(TAG_WEB);

        btnCall.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        btnWeb.setOnClickListener(this);

        imgHeader.loadImage(getData().getImg());
        imgHeader.setPopupOnClick(true);

        setTitle(getData().getName());
        setToolbarOpacity(0);
        setScrollListener(sv);
    }

    @Override
    public void onClick(View v) {
        switch ((Integer) v.getTag()) {
            case TAG_CALL:
                Helper.intentCall(getActivity(), getData().getPhone());
                break;

            case TAG_EMAIL:
                Helper.intentEmail(getActivity(), getData().getEmail(), "");
                break;

            case TAG_WEB:
                setFragment(new FragmentWebview(getData().getName(), getData().getUrl()));
                break;

            default:
                super.onClick(v);
                break;
        }

    }
}
