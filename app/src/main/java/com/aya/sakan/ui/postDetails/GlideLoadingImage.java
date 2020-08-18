package com.aya.sakan.ui.postDetails;

import android.content.Context;
import android.widget.ImageView;

import com.aya.sakan.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import ss.com.bannerslider.ImageLoadingService;

public class GlideLoadingImage implements ImageLoadingService {
    Context context;

    public GlideLoadingImage(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Glide.with(context).load(resource).into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {

        Glide.with(context)
                .load(url).apply(new RequestOptions().placeholder(R.drawable.post_placeholder).error(errorDrawable))
                .into(imageView);

    }
}
