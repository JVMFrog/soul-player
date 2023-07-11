package com.jvmfrog.soulplayer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.soulplayer.CustomMediaPlayer;
import com.jvmfrog.soulplayer.adapter.CustomMediaPlayerAdapter;
import com.jvmfrog.soulplayer.databinding.FragmentSongsBinding;
import com.jvmfrog.soulplayer.model.SongsModel;
import com.jvmfrog.soulplayer.viewmodel.SongsViewModel;

import java.util.List;

public class SongsFragment extends Fragment {
    private FragmentSongsBinding binding;
    private SongsViewModel model;
    private CustomMediaPlayerAdapter adapter;

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
        /*model = new ViewModelProvider(this).get(SongsViewModel.class);
        model.getSongFiles().observe(getViewLifecycleOwner(), songFiles -> {
            for (SongsModel songFile : songFiles) {
                Log.d("MusicScanner", "Title: " + songFile.getTitle() +
                        ", Artist: " + songFile.getArtist() +
                        ", Album: " + songFile.getAlbum() +
                        ", Path: " + songFile.getPath());
                binding.songsCount.setText(songFiles.size() + " " + "Songs");
            }
            binding.recview.setLayoutManager(new GridLayoutManager(requireActivity(), 1));
            binding.recview.setAdapter(new CustomMediaPlayerAdapter(requireActivity(), songFiles.size()));
        });
        model.loadSongFiles(requireContext());*/

        CustomMediaPlayer mediaPlayer = CustomMediaPlayer.getInstance(requireContext());
        mediaPlayer.scanMusic();
        List<String> trackList = mediaPlayer.getTrackList();

        binding.recview.setLayoutManager(new GridLayoutManager(requireActivity(), 1));
        binding.recview.setAdapter(new CustomMediaPlayerAdapter(requireActivity(), trackList));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}