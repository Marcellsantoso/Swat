package com.imb.swat.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.imb.swat.R;
import com.imb.swat.generics.BaseConstants;
import com.imb.swat.helper.CircleTransform;
import com.imb.swat.helper.Helper;
import com.imb.swat.helper.RoundedShadowTransform;
import com.imb.swat.helper.UIHelper;
import com.imb.swat.views.photoview.PhotoView;
import com.squareup.picasso.Callback.EmptyCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class ImageViewLoader
        extends RelativeLayout implements View.OnClickListener {
    private ImageView   image;
    private ImageView   imageOverlay;
    private ProgressBar progress;
    @SuppressWarnings("unused")
    private boolean isFade   = true, isSquareToWidth = false,
            isSquareToHeight = false, popup = false;
    private long         delay;
    private int          placeholder;
    private ListenerLoad listenerLoad;
    private String       url;
    private int          resImg;

    public ImageViewLoader(Context context) {
        this(context, null);
    }

    public ImageViewLoader(Context context, AttributeSet attr) {
        super(context, attr);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.image_view_loader, this, true);

        image = (ImageView) this.findViewById(R.id.image);
        imageOverlay = (ImageView) this.findViewById(R.id.imgOverlay);
        progress = (ProgressBar) this.findViewById(R.id.loader);

        // Default scale type
        image.setScaleType(ScaleType.CENTER_CROP);
    }

    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    public void showFail() {
        if (placeholder == 0) {
            image.setScaleType(ScaleType.CENTER);
            image.setImageResource(R.drawable.ic_cross_light);
        }
    }

    // ================================================================================
    // Image Loader Functions
    // ================================================================================
    public void loadImage(String url) {
        this.loadImage(url, 0, false);
    }

    public void loadImage(String url, boolean isCircular) {
        this.loadImage(url, 0, isCircular);
    }

    public void loadImage(String url, int resPlaceHolder, boolean isCircular) {
        if (isCircular)
            this.loadImage(url, resPlaceHolder, new CircleTransform());
        else
            this.loadImage(url, resPlaceHolder, null);
    }

    /**
     * Rounded corner & shadow
     *
     * @param url
     * @param radius
     * @param margin
     */
    public void loadImage(String url, int radius, int margin) {
        this.loadImage(
                url,
                0,
                new RoundedShadowTransform((int) UIHelper
                        .convertDpToPixel(radius, getContext()), margin));
    }

    /**
     * @param url
     * @param resPlaceHolder
     * @param radius
     * @param margin
     */
    public void loadImage(String url, int resPlaceHolder, int radius, int margin) {
        this.loadImage(url, resPlaceHolder, new RoundedShadowTransform(radius, margin));
    }

    public void loadImage(final String url, int resPlaceHolder, Transformation transformation) {
        this.placeholder = resPlaceHolder;
        this.url = url;

        if (Helper.isEmpty(url)) {
            hideProgress();
            showFail();
            return;
        } else {
            showProgress();
        }

        if (this.image != null && this.progress != null) {
            RequestCreator imageLoader = Picasso
                    .with(this.getContext())
                    .load(url);

            if (!isFade) {
                imageLoader.noFade();
            }

            if (resPlaceHolder > 0) {
                imageLoader.placeholder(resPlaceHolder);
            }

            // Rounded image
            if (transformation != null) {
                imageLoader = imageLoader.transform(transformation);
            }

            imageLoader.into(this.image, new EmptyCallback() {
                @Override
                public void onSuccess() {
                    super.onSuccess();

                    if (listenerLoad != null)
                        listenerLoad.onSuccess();

                    hideProgress();
                }

                @Override
                public void onError() {
                    super.onError();
                    showFail();
                    Log.d(BaseConstants.LOG, "Failed to load : " + url);

                    if (listenerLoad != null)
                        listenerLoad.onFail();

                    hideProgress();
                }
            });
        }
    }

    public void loadImage(int resImage) {
        if (resImage != 0) {
            this.resImg = resImage;
            RequestCreator imageLoader = Picasso
                    .with(this.getContext())
                    .load(resImage);
            imageLoader.into(this.image);
        }
        hideProgress();
    }

    // ================================================================================
    // Getter & Setter
    // ================================================================================
    public ImageView getImage() {
        return image;
    }

    public boolean isFade() {
        return isFade;
    }

    public void setFade(boolean isFade) {
        this.isFade = isFade;
    }

    public void hideOverlay() {
        this.imageOverlay.setVisibility(View.GONE);
    }

    public void setImageOverlay(int res) {
        if (res == 0)
            this.imageOverlay.setVisibility(View.GONE);
        else
            this.imageOverlay.setImageResource(res);
    }

    public void setListenerLoad(ListenerLoad listenerLoad) {
        this.listenerLoad = listenerLoad;
    }

    // ================================================================================
    // Popup
    // ================================================================================
    public void setPopupOnClick(boolean popup) {
        this.popup = popup;

        if (popup) {
            this.setOnClickListener(this);
        }
    }

    public void popupImage() {
        final Dialog popup = new Dialog(getContext());
        popup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = ((LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup_image,
                                                                            null);
        popup.setContentView(view);

        PhotoView img = (PhotoView) view.findViewById(R.id.img);
        //        img.setImageDrawable(this.image.getDrawable());
        if (resImg > 0)
            img.setImageResource(resImg);
        else if (!Helper.isEmpty(this.url))
            Picasso.with(getContext()).load(this.url).into(img);

        img.setSquareToWidth(true);
        img.setZoomable(true);
        img.setScaleType(ScaleType.FIT_CENTER);

        // img.set
        // img.setImageOverlay(0);
        // img.hideProgress();

        popup.setCancelable(true);
        popup.show();
    }

    @Override
    public void onClick(View v) {
        popupImage();
    }

    // ================================================================================
    // Make it always square, adjusting to width
    // ================================================================================
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isSquareToWidth) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            // image.getLayoutParams().height = widthMeasureSpec;
        } else if (isSquareToHeight) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            // image.getLayoutParams().width = heightMeasureSpec;
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isSquareToWidth() {
        return isSquareToWidth;
    }

    public boolean isSquareToHeight() {
        return isSquareToHeight;
    }

    public void setSquareToWidth(boolean isSquareToWidth) {
        this.isSquareToWidth = isSquareToWidth;
    }

    public void setSquareToHeight(boolean isSquareToHeight) {
        this.isSquareToHeight = isSquareToHeight;
    }

    // ================================================================================
    // Convert to upload format
    // ================================================================================
    public String getUploadFormat() {
        try {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            if (bitmap != null) {
                showProgress();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                byte[] image = stream.toByteArray();

                hideProgress();
                return Base64.encodeToString(image, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public interface ListenerLoad {
        public void onSuccess();

        public void onFail();
    }


}
