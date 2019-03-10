package com.wipro.screeningtask.exercise.pojo;


import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wipro.screeningtask.R;

public class ExerciseListItem {

    private String title;
    private String description;
    private String imageHref;

    @BindingAdapter({"android:source"})
    public static void loadImage(ImageView view, String url) {

        if (url == null || url.isEmpty()) {
            view.setImageResource(R.drawable.placeholder_iamge);
        } else {
            Picasso.get().load(url).placeholder(R.drawable.placeholder_iamge).into(view);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }
}
