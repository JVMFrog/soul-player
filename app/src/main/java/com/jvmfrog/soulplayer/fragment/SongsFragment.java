package com.jvmfrog.soulplayer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvmfrog.soulplayer.CustomMediaPlayer;
import com.jvmfrog.soulplayer.adapter.CustomMediaPlayerAdapter;
import com.jvmfrog.soulplayer.databinding.FragmentSongsBinding;
import com.jvmfrog.soulplayer.model.Song;
import com.jvmfrog.soulplayer.repository.MusicRepository;
import com.jvmfrog.soulplayer.viewmodel.MusicViewModel;

import java.io.IOException;
import java.util.List;

public class SongsFragment extends Fragment {
    private FragmentSongsBinding binding;
    private CustomMediaPlayer mediaPlayer;
    private MusicViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        viewModel.getSongs().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                // Обновление пользовательского интерфейса с новым списком песен
                // Например, обновление RecyclerView или ListView
                binding.recview.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.recview.setAdapter(new CustomMediaPlayerAdapter(requireContext(), songs));
            }
        });

        mediaPlayer = CustomMediaPlayer.getInstance(requireContext());
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

    }

    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer.scanMusic();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        try {
            mediaPlayer.release();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}