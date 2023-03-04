package com.jvmfrog.components.bottomnavigationview.api;

import androidx.annotation.ColorRes;

import com.jvmfrog.components.bottomnavigationview.builders.BottomNavigationConfigBuilder;

import java.util.List;

public class BottomNavigationConfig {
    private final List<BottomNavigationSection> sectionList;
    @ColorRes
    private final Integer tint;
    private final BottomNavigationConfigBuilder.OnItemClickListener onItemClicked;
    private final MenuIconLoader loader;

    public BottomNavigationConfig(List<BottomNavigationSection> sectionList, Integer tint, BottomNavigationConfigBuilder.OnItemClickListener onItemClicked, MenuIconLoader loader) {
        this.sectionList = sectionList;
        this.tint = tint;
        this.onItemClicked = onItemClicked;
        this.loader = loader;
    }

    public List<BottomNavigationSection> getSectionList() {
        return sectionList;
    }

    public Integer getTint() {
        return tint;
    }

    public BottomNavigationConfigBuilder.OnItemClickListener getOnItemClicked() {
        return onItemClicked;
    }

    public MenuIconLoader getLoader() {
        return loader;
    }
}
