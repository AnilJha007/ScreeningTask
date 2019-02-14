package com.wipro.screeningtask.exercise.pojo;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

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
            Picasso.get().load(url).noPlaceholder().into(view);
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
