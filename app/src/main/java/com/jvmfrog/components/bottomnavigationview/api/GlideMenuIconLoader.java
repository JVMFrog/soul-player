package com.jvmfrog.components.bottomnavigationview.api;

import android.content.Context;
import android.view.MenuItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideMenuIconLoader implements MenuIconLoader {
    private Context context;

    public GlideMenuIconLoader(Context context) {
        this.context = context;
    }

    @Override
    public void loadIcon(MenuItem menuItem, String url) {
        Glide.with(context)
                .asDrawable()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(new MenuItemTarget(context, menuItem));
    }
}