package com.jvmfrog.components.bottomnavigationview.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.BitmapCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class MenuItemTarget extends CustomTarget<Drawable> {
    private Context context;
    private MenuItem menuItem;

    public MenuItemTarget(Context context, MenuItem menuItem) {
        this.context = context;
        this.menuItem = menuItem;
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        menuItem.setIcon(resource);
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {
        // ignore
    }
}