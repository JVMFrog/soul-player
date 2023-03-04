package com.jvmfrog.components.bottomnavigationview.builders;

import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationSection;
import com.jvmfrog.components.bottomnavigationview.api.SectionsBlock;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationSectionsBlockBuilder {
    private final List<BottomNavigationSection> sections = new ArrayList<>();

    public void section(BottomNavigationSectionBuilder sectionBuilder) {
        BottomNavigationSection section = sectionBuilder.build();
        sections.add(section);
    }

    public SectionsBlock build() {
        return new SectionsBlock(sections);
    }
}
