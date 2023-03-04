package com.jvmfrog.components.bottomnavigationview.api;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jvmfrog.components.bottomnavigationview.builders.BottomNavigationConfigBuilder;
import com.jvmfrog.components.bottomnavigationview.utils.BottomNavigationUtils;

public class BottomNavigationExtension {

    public static void setup(@NonNull BottomNavigationView bottomNavigationView, BottomNavigationConfigBuilder configBuilder) {
        BottomNavigationConfig config = configBuilder.build();
        BottomNavigationUtils.setBottomNavigationSections(bottomNavigationView, config);
        BottomNavigationUtils.setBottomNavigationTint(bottomNavigationView, config);
    }

}
