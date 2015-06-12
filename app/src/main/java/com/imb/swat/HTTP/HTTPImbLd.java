package com.imb.swat.http;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.imb.swat.views.LoadingCompound;

import org.json.JSONObject;

/**
 * Created by marcelsantoso on 5/30/15.
 */
public abstract class HTTPImbLd extends HTTPImb {
    private LoadingCompound ld;

    public HTTPImbLd(Fragment frag, LoadingCompound ld) {
        super(frag, false);
        this.ld = ld;
    }

    @Override
    public void onFail(int code, String message) {
        if (ld == null)
            super.onFail(code, message);
        else
            ld.showError("", message);
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
