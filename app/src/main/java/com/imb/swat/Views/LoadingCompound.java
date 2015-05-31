package com.imb.swat.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imb.swat.R;

/**
 * Created by marcelsantoso on 5/30/15.
 */
public class LoadingCompound
        extends LinearLayout {
    private LinearLayout    loadingContainer;
    private LinearLayout    retryContainer;
    private TextView        tvTitle;
    private TextView        tvMessage;
    private TextView        tvLoading;
    private Button          btnRetry;
    private LoadingListener mStartLoadingListener;
    private String          noNetworkMessage;
    private String          unknownResponseMessage;
    private String          noInternetMessage;
    private int             countTotal = 1, countSuccess;

    /**
     * Listener for retry button onClick Events, and loading is shown
     *
     * @author melvin
     */
    public interface LoadingListener {
        public void onStartLoading();
    }

    public LoadingCompound(Context context) {
        super(context, null);

    }

    public LoadingCompound(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_loading_compound, this, true);

        if (!isInEditMode()) {
            loadingContainer = (LinearLayout) this.findViewById(R.id.loadingContainer);
            retryContainer = (LinearLayout) this.findViewById(R.id.retryContainer);
            tvLoading = (TextView) this.findViewById(R.id.textViewLoadingMessage);
            tvTitle = (TextView) this.findViewById(R.id.textViewLoadingErrorTitle);
            tvMessage = (TextView) this.findViewById(R.id.textViewLoadingErrorMessage);
            btnRetry = (Button) this.findViewById(R.id.buttonRetry);

            if (attrs != null) {
                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.iapps);

                // Set the colors from the attributes
                int loadingMessageColor = a.getColor(
                        R.styleable.iapps_loadingMessageColor, context
                                .getResources().getColor(R.color.DarkGray));
                int errTitleColor = a.getColor(
                        R.styleable.iapps_loadingErrorTitleColor, context
                                .getResources().getColor(R.color.Black));
                int errMessageColor = a.getColor(
                        R.styleable.iapps_loadingErrorMessageColor,
                        context.getResources().getColor(R.color.soft_grey));
                tvLoading.setTextColor(loadingMessageColor);
                tvTitle.setTextColor(errTitleColor);
                tvMessage.setTextColor(errMessageColor);

                // Set the text resources from attributes
                String loadingMessage = a.getString(R.styleable.iapps_loadingMessage);
                if (loadingMessage != null) {
                    tvLoading.setText(loadingMessage);
                }

                String retryButtonText = a.getString(R.styleable.iapps_loadingRetryButtonText);
                if (retryButtonText != null) {
                    btnRetry.setText(retryButtonText);
                }

                noNetworkMessage = a.getString(R.styleable.iapps_loadingErrorNetworkTitle);
                unknownResponseMessage = a.getString(R.styleable.iapps_loadingErrorUnknownResponseMessage);
                noInternetMessage = a.getString(R.styleable.iapps_loadingErrorNoInternetMessage);

                if (noNetworkMessage == null) {
                    noNetworkMessage = "Network error";
                }

                if (noInternetMessage == null) {
                    noInternetMessage = "No internet connection";
                }

                if (unknownResponseMessage == null) {
                    unknownResponseMessage = "Unknown response from server";
                }

                a.recycle();
            }

            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingContainer.setVisibility(View.VISIBLE);
                    retryContainer.setVisibility(View.GONE);

                    if (mStartLoadingListener != null) {
                        showLoading();
                        mStartLoadingListener.onStartLoading();
                    }
                }
            });
        }
    }

    /**
     * Hide the loading compound
     */
    public void hide() {
        countSuccess++;

        // Hide ld if successfully load all api(s)
        // If at least 1 api call is fail, don't hide. BUT, you have to handle this on fail.
        if (countTotal <= countSuccess)
            this.setVisibility(View.GONE);
    }

    public void hideForce() {
        countSuccess = countTotal - 1;
        hide();
    }

    public void setOnStartLoadingListener(LoadingListener l) {
        this.mStartLoadingListener = l;
    }

    public void setNoNetworkMessage(int noNetworkMessage) {
        setNoNetworkMessage(getContext().getString(noNetworkMessage));
    }

    public void setUnknownResponseMessage(int unknownResponseMessage) {
        setUnknownResponseMessage(getContext().getString(unknownResponseMessage));
    }

    public void setNoInternetMessage(int noInternetMessage) {
        setNoInternetMessage(getContext().getString(noInternetMessage));
    }

    public void setNoNetworkMessage(String noNetworkMessage) {
        this.noNetworkMessage = noNetworkMessage;
    }

    public void setUnknownResponseMessage(String unknownResponseMessage) {
        this.unknownResponseMessage = unknownResponseMessage;
    }

    public void setNoInternetMessage(String noInternetMessage) {
        this.noInternetMessage = noInternetMessage;
    }

    public void setRetryButtonTitle(String title) {
        btnRetry.setText(title);
    }

    public void setRetryButtonTitle(int titleResId) {
        btnRetry.setText(titleResId);
    }

    public void addCountSuccess() {
        this.countSuccess++;
    }

    public int getCountSuccess() {
        return this.countSuccess;
    }

    public void setCountTotal(int totalCount) {
        this.countTotal = totalCount;
    }

    public int getCountTotal() {
        return this.countTotal;
    }

    public void showLoading() {
        this.setVisibility(View.VISIBLE);
    }

    public void showError(String title, String message) {
        this.clearAnimation();
        this.setVisibility(View.VISIBLE);
        retryContainer.setVisibility(View.VISIBLE);
        if (mStartLoadingListener != null) {
            btnRetry.setVisibility(View.VISIBLE);
        } else {
            btnRetry.setVisibility(View.GONE);
        }
        loadingContainer.setVisibility(View.GONE);
        if (title != null) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);

        }

        if (message != null) {
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);
        }
    }

    public void showInternetError() {
        this.showError(
                noNetworkMessage,
                noInternetMessage);

    }

    public void showUnknownResponse() {
        this.showError(
                noNetworkMessage,
                unknownResponseMessage);

    }

    public void setRetryEnabled(boolean b) {
        if (b) {
            btnRetry.setVisibility(View.VISIBLE);
        } else {
            btnRetry.setVisibility(View.GONE);
        }

    }

}
