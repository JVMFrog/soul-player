package com.jvmfrog.soulplayer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.soulplayer.databinding.FragmentSongsBinding;
import com.jvmfrog.soulplayer.model.SongsModel;
import com.jvmfrog.soulplayer.viewmodel.SongsViewModel;

public class SongsFragment extends Fragment {
    private FragmentSongsBinding binding;
    private SongsViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSongsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(this).get(SongsViewModel.class);
        model.getSongFiles().observe(getViewLifecycleOwner(), songFiles -> {
            for (SongsModel songFile : songFiles) {
                Log.d("MusicScanner", "Title: " + songFile.getTitle() +
                        ", Artist: " + songFile.getArtist() +
                        ", Album: " + songFile.getAlbum() +
                        ", Path: " + songFile.getPath());
                binding.songsCount.setText(songFiles.size() + " " + "Songs");
            }
        });
        model.loadSongFiles(requireContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}