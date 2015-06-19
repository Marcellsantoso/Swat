package com.imb.swat.http;

import android.content.Context;

import com.imb.swat.generics.BaseFragment;
import com.imb.swat.views.LoadingCompound;

import org.json.JSONObject;

/**
 * Created by marcelsantoso on 5/30/15.
 */
public abstract class HTTPImbLd extends HTTPImb {
    private LoadingCompound ld;
    private BaseFragment    frag;

    public HTTPImbLd(BaseFragment frag, LoadingCompound ld) {
        super(frag, false);
        this.frag = frag;
        this.ld = ld;

        if (frag.pagination()) {
            setPage(frag.paginationPage());
        }

        // Handle loading compound behavior
        if (frag.pagination() && frag.paginationPage() >= 1) {
            this.ld.hide();
        } else
            this.ld.showLoading();
    }

    @Override
    public void onFail(int code, String message) {
        if (ld == null)
            super.onFail(code, message);
        else {
                ld.showError("", message);
        }
    }

    @Override
    public JSONObject handleResponse(Response response, boolean shouldDisplayDialog, Context context) {
        if (response != null) {
            JSONObject json = response.getContent();
            if (response.getStatusCode() == STATUS_SUCCESS) {
                ld.hide();
                return json;
            } else if (response.getStatusCode() == STATUS_TIMEOUT) {
                if (shouldDisplayDialog && context != null) {
                    showAlert(context, ERR_TIMEOUT);
                } else if (ld != null) {
                    ld.showError("", ERR_TIMEOUT);
                }
            } else {
                if (shouldDisplayDialog && context != null) {
                    showAlert(context, ERR_CONNECTION);
                } else if (ld != null) {
                    ld.showError("", ERR_CONNECTION);
                }
            }
        } else {
            if (shouldDisplayDialog && context != null) {
                showAlert(context, ERR_NETWORK);
            } else if (ld != null) {
                ld.showError("", ERR_NETWORK);
            }
        }

        return null;
    }
}
