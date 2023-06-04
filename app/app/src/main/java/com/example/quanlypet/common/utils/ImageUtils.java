package com.example.quanlypet.common.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quanlypet.R;
import com.example.quanlypet.common.DataStatic;

public class ImageUtils {

    public static void loadUrl(Activity context, ImageView imv, String url) {
        if (url != null && !url.equalsIgnoreCase("null") && url.length() > 0) {
            url = String.format("%s"+"static/"+"%s", DataStatic.BASE_URL, url);
            Glide.with(context)
                    .load(url)
                    .error(R.drawable.no_image_available)
                    .thumbnail(0.2f)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imv);
        }else{
            Glide.with(context)
                    .load(R.drawable.no_image)
                    .thumbnail(0.2f)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imv);
        }
    }

    public static void loadUri(Activity context, ImageView imv, Uri uri){
        Glide.with(context)
                .load(uri)
                .into(imv);
    }
}
