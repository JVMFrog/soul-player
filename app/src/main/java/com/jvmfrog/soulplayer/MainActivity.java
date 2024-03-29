package com.jvmfrog.soulplayer;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationConfig;
import com.jvmfrog.components.bottomnavigationview.api.BottomNavigationSection;
import com.jvmfrog.components.bottomnavigationview.api.IconSource;
import com.jvmfrog.components.bottomnavigationview.utils.BottomNavigationUtils;
import com.jvmfrog.soulplayer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        List<BottomNavigationSection> list = new ArrayList<>();
        list.add(new BottomNavigationSection("forYou", R.string.for_you, new IconSource.ResourceId(R.drawable.ic_launcher_foreground)));
        list.add(new BottomNavigationSection("songs", R.string.songs, new IconSource.ResourceId(R.drawable.ic_launcher_foreground)));
        list.add(new BottomNavigationSection("albums", R.string.albums, new IconSource.ResourceId(R.drawable.ic_launcher_foreground)));
        list.add(new BottomNavigationSection("artists", R.string.artists, new IconSource.ResourceId(R.drawable.ic_launcher_foreground)));
        list.add(new BottomNavigationSection("playlists", R.string.playlists, new IconSource.ResourceId(R.drawable.ic_launcher_foreground)));

        BottomNavigationUtils.setBottomNavigationSections(
                binding.bottomNavigationView,
                new BottomNavigationConfig(list, null,
                        section -> {
                            navController.navigate(section.getLink());
                        }, null
                )
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}