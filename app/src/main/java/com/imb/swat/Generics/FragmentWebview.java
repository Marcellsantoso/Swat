package com.imb.swat.generics;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.imb.swat.R;
import com.imb.swat.helper.Helper;
import com.imb.swat.views.LoadingCompound;

import roboguice.inject.InjectView;

public class FragmentWebview extends BaseFragment {
    @InjectView(R.id.wv)
    private WebView         wv;
    @InjectView(R.id.ld)
    private LoadingCompound ld;
    private String          title = "", url;
    private int resTitle;

    public FragmentWebview(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public FragmentWebview(int resTitle, String url) {
        this.resTitle = resTitle;
        this.url = url;
    }

    @Override
    public int layout() {
        return R.layout.fragment_webview;
    }

    @Override
    public int menuLayout() {
        return R.menu.web;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        if (resTitle > 0) {
            setTitle(resTitle);
        } else {
            setTitle(title);
        }

        loadContent();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    public void loadContent() {
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setPluginState(PluginState.ON);
        wv.getSettings().setAllowFileAccess(true);
        wv.setWebViewClient(new Callback());
        wv.loadUrl(url);
    }

    // ================================================================================
    // Menu
    // ================================================================================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                wv.reload();
                ld.showLoading();
                break;

            case R.id.menu_browser:
                Helper.intentWeb(getActivity(), url);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // ================================================================================
    // Callback Setting
    // ================================================================================
    private class Callback
            extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            ld.showLoading();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            ld.hide();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }
    }

}
