package com.imb.swat.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marcelsantoso on 5/30/15.
 */
public abstract class HTTPImb
        extends HTTPAsyncTask {
    private Fragment       fragment;
    private boolean        displayProgress;
    private ProgressDialog mDialog;
    private             int     page                  = 1;
    // ================================================================================
    // BaseConstants
    // ================================================================================
    public static final String  EXACT                 = "exact";
    public static final String  START                 = "start";
    public static final String  END                   = "end";
    public static final String  ANYWHERE              = "anywhere";
    public static final String  STATUS_CODE           = "status_code";
    public static final String  STATUS_MESSAGE        = "status_message";
    public static final String  SEARCH                = "search";
    public static final String  PAGE                  = "page";
    public static final String  LIMIT                 = "limit";
    public static final String  ERR_TIMEOUT           = "Connection timeout";
    public static final String  ERR_CONNECTION        = "Failed to connect to server";
    public static final String  ERR_UNKNOWN           = "Unknown response from server";
    public static final String  ERR_NETWORK           = "Network error";
    public static final String  LOADING               = "Loading...";
    public static final int     DEFAULT_PAGE          = 1;
    public static final int     DEFAULT_LIMIT         = 10;
    public static final int     CODE_BACKEND_FAIL     = 101;
    public static final int     CODE_EMPTY_RESPONSE   = 102;
    public static final int     CODE_INVALID_RESPONSE = 103;
    private             boolean IS_DEBUGGING          = true;
    private             String  LOG                   = "com.imb.swat.HTTP Log";

    public HTTPImb(Fragment frag, boolean displayProgress) {
        this.fragment = frag;
        this.displayProgress = displayProgress;

    }

    public void setDefaultValue() {
        if (!isEmpty(url()))
            setUrl(url());

        if (!isEmpty(search()))
            setGetParams(
                    SEARCH, search());

        if (page() > 0)
            setGetParams(
                    PAGE, page());

        if (limit() >= 0)
            setGetParams(
                    LIMIT, limit());
    }

    @Override
    protected void onPreExecute() {
        setDefaultValue();

        if (displayProgress) {
            mDialog = ProgressDialog.show(fragment.getActivity(), "", LOADING);
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        if (!isValidResponse(response, fragment))
            return;

        if (mDialog != null)
            mDialog.dismiss();

        JSONObject json;
        json = handleResponse(response, false, fragment.getActivity());

        if (json != null) {
            try {
                if (json.getInt(
                        STATUS_CODE) == 1) {
                    onSuccess(json);
                } else {
                    onFail(CODE_BACKEND_FAIL, json);
                }

            } catch (JSONException e) {
                onFail(CODE_INVALID_RESPONSE, e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Failed to parse JSON
            onFail(CODE_EMPTY_RESPONSE, ERR_UNKNOWN);
        }
    }

    public abstract void onSuccess(JSONObject j);

    public void onFail(int code, JSONObject j) {
        try {
            onFail(code, j.getString(STATUS_MESSAGE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onFail(int code, String message) {
        showAlert(fragment.getActivity(), message);
    }

    public int limit() {
        return DEFAULT_LIMIT;
    }

    public int page() {
        return this.page;
    }

    public void setPage(int page) {
        Log.d("testasd", Integer.toString(page));
        this.page = page;
    }

    public abstract String url();

    public boolean isDebugging() {
        return this.IS_DEBUGGING;
    }

    public String log() {
        return this.LOG;
    }

    public String search() {
        return "";
    }

    private boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    protected AlertDialog showAlert(
            Context context, String message) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        return new AlertDialog.Builder(context).setMessage(message)
                                               .setCancelable(true)
                                               .setNeutralButton(
                                                       android.R.string.ok,
                                                       listener)
                                               .show();
    }

    protected boolean isValidResponse(Response response, final Fragment frag) {
        if (response != null) {
            if ((frag.getActivity() != null
                    && (frag.getActivity()).isFinishing()) || !frag.isVisible())
                return false;

            if (isDebugging())
                Log.d(log(), response.getContent().toString());
        } else
            return false;

        return true;
    }

    protected JSONObject handleResponse(
            Response response, boolean shouldDisplayDialog,
            Context context) {
        if (response != null) {
            JSONObject json = response.getContent();
            if (response.getStatusCode() == STATUS_SUCCESS) {
                return json;
            } else if (response.getStatusCode() == STATUS_TIMEOUT) {
                if (shouldDisplayDialog && context != null) {
                    showAlert(context, ERR_TIMEOUT);
                }
            } else {
                showAlert(context, ERR_CONNECTION);
            }
        } else {
            if (shouldDisplayDialog && context != null) {
                showAlert(context, ERR_NETWORK);
            }
        }

        return null;
    }
}
