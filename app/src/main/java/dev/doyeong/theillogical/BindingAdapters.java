package dev.doyeong.theillogical;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindingAdapters {
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String link) {
        if (link == null || link.isEmpty()) return;
        Glide.with(view).load(link).into(view);
    }
    @BindingAdapter("circleImageUrl")
    public static void setCircleImageUrl(ImageView view, String link) {
        if (link == null || link.isEmpty()) return;
        Glide.with(view).load(link).circleCrop().into(view);
    }
}
