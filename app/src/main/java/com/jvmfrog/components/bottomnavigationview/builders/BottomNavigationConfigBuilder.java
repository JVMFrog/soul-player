package com.jvmfrog.components.bottomnavigationview.builders;

import android.view.MenuItem;

import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationConfig;
import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationSection;
import com.jvmfrog.components.bottomnavigationview.api.MenuIconLoader;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationConfigBuilder {
    private List<BottomNavigationSection> sections = new ArrayList<>();
    private MenuIconLoader loader = new MenuIconLoader() {
        @Override
        public void loadIcon(MenuItem menuItem, String url) {
            // fallback implementation, ignore
        }
    };
    private int tintRes;
    private OnItemClickListener onItemClicked = section -> {};

    public void sections(List<BottomNavigationSection> sectionList) {
        sections.clear();
        sections.addAll(sectionList);
    }

    public void sections(BottomNavigationSectionsBlockBuilder builder) {
        List<BottomNavigationSection> sectionList = builder.build().getSections();
        sections.clear();
        sections.addAll(sectionList);
    }

    public void onItemClicked(OnItemClickListener listener) {
        onItemClicked = listener;
    }

    public void remoteLoader(MenuIconLoader loader) {
        this.loader = loader;
    }

    public void tint(int colorSelectorIdRes) {
        tintRes = colorSelectorIdRes;
    }

    public BottomNavigationConfig build() {
        return new BottomNavigationConfig(sections, tintRes, onItemClicked, loader);
    }

    public interface OnItemClickListener {
        void onItemClick(BottomNavigationSection section);
    }
}