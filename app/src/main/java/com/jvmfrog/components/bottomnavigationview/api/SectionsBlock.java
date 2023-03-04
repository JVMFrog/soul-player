package com.jvmfrog.components.bottomnavigationview.api;

import java.util.List;

public class SectionsBlock {
    private final List<BottomNavigationSection> sections;

    public SectionsBlock(List<BottomNavigationSection> sections) {
        this.sections = sections;
    }

    public List<BottomNavigationSection> getSections() {
        return sections;
    }
}
