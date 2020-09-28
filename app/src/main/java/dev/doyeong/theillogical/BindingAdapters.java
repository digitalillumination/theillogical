package dev.doyeong.theillogical;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;

public class BindingAdapters {
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String link) {
        if (link == null || link.isEmpty()) return;
        Glide.with(view).load(link).diskCacheStrategy(DiskCacheStrategy.NONE).signature(new ObjectKey(System.currentTimeMillis())).skipMemoryCache(true).into(view);
    }
    @BindingAdapter("circleImageUrl")
    public static void setCircleImageUrl(ImageView view, String link) {
        if (link == null || link.isEmpty()) return;
        Glide.with(view).load(link).circleCrop().signature(new ObjectKey(System.currentTimeMillis())).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(view);
    }
}
