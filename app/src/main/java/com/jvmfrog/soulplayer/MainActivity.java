package com.jvmfrog.soulplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationConfig;
import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationSection;
import com.jvmfrog.components.bottomnavigationview.api.IconSource;
import com.jvmfrog.components.bottomnavigationview.builders.BottomNavigationConfigBuilder;
import com.jvmfrog.components.bottomnavigationview.utils.BottomNavigationUtils;
import com.jvmfrog.soulplayer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<BottomNavigationSection> list = new ArrayList<>();
        list.add(new BottomNavigationSection("about_app", R.string.app_name, new IconSource.ResourceId(R.drawable.ic_launcher_background)));
        list.add(new BottomNavigationSection("about_app", R.string.app_name, new IconSource.ResourceId(R.drawable.ic_launcher_background)));
        list.add(new BottomNavigationSection("about_app", R.string.app_name, new IconSource.ResourceId(R.drawable.ic_launcher_background)));

        BottomNavigationUtils.setBottomNavigationSections(binding.bottomNavigationView, new BottomNavigationConfig(list, 434343,
                        section -> {
                            //
                        }, null));
    }
}