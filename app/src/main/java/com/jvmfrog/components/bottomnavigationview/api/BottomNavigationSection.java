package com.jvmfrog.components.bottomnavigationview.api;

public class BottomNavigationSection {
    private final String link;
    private final Integer title;
    private final IconSource iconSource;

    public BottomNavigationSection(String link, Integer title) {
        this(link, title, IconSource.notDefined());
    }

    public BottomNavigationSection(String link, Integer title, IconSource iconSource) {
        this.link = link;
        this.title = title;
        this.iconSource = iconSource;
    }

    public String getLink() {
        return link;
    }

    public Integer getTitle() {
        return title;
    }

    public IconSource getIconSource() {
        return iconSource;
    }
}
