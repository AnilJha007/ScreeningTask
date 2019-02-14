package com.wipro.screeningtask.exercise.pojo;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wipro.screeningtask.R;

public class Exercise {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("imageHref")
    private String imageHref;

    @BindingAdapter({"android:imageHref"})
    public static void loadImage(ImageView view, String url) {

        if (url == null || url.isEmpty()) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            Picasso.get().load(url).placeholder(R.drawable.ic_launcher_background).into(view);
        }
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageHref() {
        return imageHref;
    }
}
