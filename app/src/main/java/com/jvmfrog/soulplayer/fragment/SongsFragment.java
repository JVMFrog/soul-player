package com.jvmfrog.soulplayer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.soulplayer.CustomMediaPlayer;
import com.jvmfrog.soulplayer.adapter.CustomMediaPlayerAdapter;
import com.jvmfrog.soulplayer.databinding.FragmentSongsBinding;
import com.jvmfrog.soulplayer.model.SongsModel;
import com.jvmfrog.soulplayer.viewmodel.SongsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongsFragment extends Fragment {
    private FragmentSongsBinding binding;
    private CustomMediaPlayer mediaPlayer;
    private SongsViewModel model;
    private CustomMediaPlayerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = CustomMediaPlayer.getInstance(requireContext());
        mediaPlayer.scanMusic();
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
        mediaPlayer.setTrackList(Collections.emptyList()); // Очистить список треков в CustomMediaPlayer

        adapter = new CustomMediaPlayerAdapter(requireActivity(), mediaPlayer.getTrackList());
        binding.recview.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recview.setAdapter(adapter);

        model.getSongFiles().observe(getViewLifecycleOwner(), songFiles -> {
            List<String> songPaths = getSongPaths(songFiles);
            mediaPlayer.setTrackList(songPaths); // Устанавливаем новый список треков в CustomMediaPlayer

            adapter.setTrackList(songPaths); // Обновляем список треков в адаптере
            adapter.notifyDataSetChanged(); // Уведомляем адаптер об изменении данных

            for (SongsModel songFile : songFiles) {
                String title = songFile.getTitle();
                String artist = songFile.getArtist();
                String album = songFile.getAlbum();
                String path = songFile.getPath();
                // Вывод информации о треке в лог
                String logMessage = "Title: " + title + ", Artist: " + artist + ", Album: " + album + ", Path: " + path;
                System.out.println(logMessage);
            }
        });

        model.loadSongFiles(requireContext()); // Загрузить список файлов
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<String> getSongPaths(List<SongsModel> songFiles) {
        List<String> songPaths = new ArrayList<>();
        for (SongsModel songFile : songFiles) {
            songPaths.add(songFile.getPath());
        }
        return songPaths;
    }
}