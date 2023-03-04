package com.jvmfrog.components.bottomnavigationview.builders;

import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationSection;
import com.jvmfrog.components.bottomnavigationview.api.IconSource;

public class BottomNavigationSectionBuilder {
    private String id = "";
    private Integer title = Integer.valueOf("");
    private IconSource iconSource = IconSource.notDefined();

    public void link(String id) {
        this.id = id;
    }

    public void title(Integer title) {
        this.title = title;
    }

    public void iconSource(IconSource iconSource) {
        this.iconSource = iconSource;
    }

    public BottomNavigationSection build() {
        return new BottomNavigationSection(id, title, iconSource);
    }
}
