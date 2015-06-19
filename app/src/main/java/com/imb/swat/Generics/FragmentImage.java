package com.imb.swat.generics;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.imb.swat.R;
import com.imb.swat.views.ImageViewLoader;

import roboguice.inject.InjectView;

/**
 * Created by marcelsantoso.
 * <p/>
 * 6/18/15
 */
public class FragmentImage extends BaseFragment {
    @InjectView(R.id.img)
    ImageViewLoader img;
    String url;

    public FragmentImage(String url) {
        this.url = url;
    }

    @Override
    public int layout() {
        return R.layout.fragment_image;
    }

    @Override
    public int menuLayout() {
        return 0;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        if(this.url.contains("phone:")){
            img.getImage().setScaleType(ImageView.ScaleType.FIT_CENTER);
            this.url = url.replace("phone:","");
        }
        img.loadImage(this.url);
        img.setPopupOnClick(true);
    }
}
