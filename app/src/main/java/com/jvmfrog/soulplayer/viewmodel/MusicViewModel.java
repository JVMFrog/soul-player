package com.jvmfrog.soulplayer.viewmodel;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jvmfrog.soulplayer.model.Song;
import com.jvmfrog.soulplayer.repository.MusicRepository;

import java.util.ArrayList;
import java.util.List;

public class MusicViewModel extends ViewModel {
    private MutableLiveData<List<Song>> songs;
    private MusicRepository repository;

    public MusicViewModel() {
        repository = new MusicRepository();
    }

    public LiveData<List<Song>> getSongs() {
        if (songs == null) {
            songs = (MutableLiveData<List<Song>>) repository.getSongs();
        }
        return songs;
    }

    public void scanMusic() {
        repository.scanMusic();
    }
}