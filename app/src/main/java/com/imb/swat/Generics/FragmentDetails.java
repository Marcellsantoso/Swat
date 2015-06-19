package com.imb.swat.generics;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.imb.swat.Bean.BeanAttr;
import com.imb.swat.R;
import com.imb.swat.helper.Helper;
import com.imb.swat.helper.HelperList;
import com.imb.swat.views.ImageViewLoader;

import roboguice.inject.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetails extends BaseFragment {
    @InjectView(R.id.imgHeader)
    ImageViewLoader imgHeader;
    @InjectView(R.id.sv)
    ScrollView      sv;
    @InjectView(R.id.llDetails)
    LinearLayout    llDetails;
    @InjectView(R.id.pager)
    ViewPager       vp;
    private MenuItem     menu;
    private PagerAdapter mPagerAdapter;
    private final int TAG_CALL = 1, TAG_EMAIL = 2, TAG_WEB = 3, TAG_MAP = 4;

    @Override
    public int layout() {
        return R.layout.fragment_details;
    }

    @Override
    public int menuLayout() {
        return R.menu.menu_fav;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        if (getData() == null)
            return;

        if (Helper.isEmpty(getData().getImgMultiple())) {
            imgHeader.loadImage(getData().getImg());
            imgHeader.setPopupOnClick(true);
            imgHeader.setVisibility(View.VISIBLE);
            vp.setVisibility(View.GONE);
        } else {
            imgHeader.setVisibility(View.GONE);
            vp.setVisibility(View.VISIBLE);

            mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
            vp.setAdapter(mPagerAdapter);
        }

        setTitle(getData().getName());
        setToolbarOpacity(0);

        generateDetails("Details", getData().getDescLong());
        for (BeanAttr attr : getData().getAttr()) {
            generateDetails(attr.getKey(), attr.getDesc());
        }
        generateContact();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu.getItem(0);
        setMenuIcon();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_fav) {
            HelperList.addToFav(getPref(), getData().getRaw());
            getData().setFav(!getData().isFav());
            setMenuIcon();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setMenuIcon() {
        if (menu == null)
            return;

        if (getData().isFav()) {
            menu.setIcon(R.drawable.ic_fav);
        } else {
            menu.setIcon(R.drawable.ic_fav_unchecked);
        }
    }

    private void generateDetails(String title, String desc) {
        if (Helper.isEmpty(title) || Helper.isEmpty(desc))
            return;

        View v = getActivity().getLayoutInflater().inflate(R.layout.cell_details, null);
        ((TextView) v.findViewById(R.id.tvTitle)).setText(title);
        ((TextView) v.findViewById(R.id.tvDesc)).setText(desc);
        llDetails.addView(v);
    }

    private void generateContact() {
        View        v         = getActivity().getLayoutInflater().inflate(R.layout.cell_map, null);
        TextView    tvAddress = (TextView) v.findViewById(R.id.tvAddress);
        ImageButton btnMap    = (ImageButton) v.findViewById(R.id.btnMap);
        ImageButton btnCall   = (ImageButton) v.findViewById(R.id.btnCall);
        ImageButton btnEmail  = (ImageButton) v.findViewById(R.id.btnEmail);
        ImageButton btnWeb    = (ImageButton) v.findViewById(R.id.btnWeb);
        int         counter   = 0;

        if (getData().getLongitude() == 0 && getData().getLatitude() == 0) {
            btnMap.setVisibility(View.GONE);
            counter++;
        } else {
            btnMap.setVisibility(View.VISIBLE);
            btnMap.setTag(TAG_MAP);
            btnMap.setOnClickListener(this);
        }

        if (Helper.isEmpty(getData().getEmail())) {
            btnEmail.setVisibility(View.GONE);
            counter++;
        } else {
            btnEmail.setVisibility(View.VISIBLE);
            btnEmail.setTag(TAG_EMAIL);
            btnEmail.setOnClickListener(this);
        }

        if (Helper.isEmpty(getData().getPhone())) {
            btnCall.setVisibility(View.GONE);
            counter++;
        } else {
            btnCall.setVisibility(View.VISIBLE);
            btnCall.setTag(TAG_CALL);
            btnCall.setOnClickListener(this);
        }

        if (Helper.isEmpty(getData().getUrl())) {
            btnWeb.setVisibility(View.GONE);
            counter++;
        } else {
            btnWeb.setVisibility(View.VISIBLE);
            btnWeb.setTag(TAG_WEB);
            btnWeb.setOnClickListener(this);
        }

        if (Helper.isEmpty(getData().getAddress())) {
            tvAddress.setVisibility(View.GONE);
            counter++;
        } else {
            tvAddress.setVisibility(View.VISIBLE);
            tvAddress.setText(Helper.capitalizeFirstLetter(Helper.breakLine(getData().getAddress())));
        }

        if (counter < 5)
            llDetails.addView(v, 0);
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

            case TAG_MAP:
                setFragment(new FragmentMap(getData()));
                break;

            default:
                super.onClick(v);
                break;
        }
    }

    // ================================================================================
    // ViewPager
    // ================================================================================
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new FragmentImage(getData().getImgMultiple().split(";")[position]);
        }

        @Override
        public int getCount() {
            return getData().getImgMultiple().split(";").length;
        }
    }
}
