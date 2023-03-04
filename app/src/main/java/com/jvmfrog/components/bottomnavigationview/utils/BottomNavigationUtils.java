package com.jvmfrog.components.bottomnavigationview.utils;

import android.content.Context;
import android.view.MenuItem;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationConfig;
import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationSection;
import com.jvmfrog.components.bottomnavigationview.api.IconSource;

public class BottomNavigationUtils {
    public static void setBottomNavigationSections(BottomNavigationView view, BottomNavigationConfig config) {
        view.getMenu().clear();
        for (int i = 0; i < config.getSectionList().size(); i++) {
            BottomNavigationSection section = config.getSectionList().get(i);
            MenuItem menuItem = view.getMenu().add(0, i, i, section.getTitle());
            switch (section.getIconSource().getClass().getSimpleName()) {
                case "ResourceId":
                    IconSource.ResourceId resourceId = (IconSource.ResourceId) section.getIconSource();
                    menuItem.setIcon(resourceId.getDrawableResourceId());
                    break;
                case "Url":
                    IconSource.Url url = (IconSource.Url) section.getIconSource();
                    config.getLoader().loadIcon(menuItem, url.getUrl());
                    break;
                case "NotDefined":
                default:
                    break;
            }
            menuItem.setOnMenuItemClickListener(item -> {
                config.getOnItemClicked().onItemClick(section);
                return false;
            });
        }
    }

    public static void setBottomNavigationTint(BottomNavigationView view, BottomNavigationConfig config) {
        Integer tint = config.getTint();
        if (tint != null) {
            Context context = view.getContext();
            view.setItemIconTintList(ContextCompat.getColorStateList(context, tint));
            view.setItemTextColor(ContextCompat.getColorStateList(context, tint));
        }
    }
}