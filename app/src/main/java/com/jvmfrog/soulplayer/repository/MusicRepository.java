package com.jvmfrog.soulplayer.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jvmfrog.soulplayer.model.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicRepository {
    private Context context;
    private MutableLiveData<List<Song>> songs;

    public MusicRepository() {
        this.context = context;
    }

    public LiveData<List<Song>> getSongs() {
        if (songs == null) {
            songs = new MutableLiveData<>();
            loadSongs();
        }
        return songs;
    }

    private void loadSongs() {
        ContentResolver contentResolver = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";

        Cursor cursor = contentResolver.query(
                songUri,
                null,
                selection,
                null,
                null
        );

        List<Song> songList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int pathColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String title = cursor.getString(titleColumn);
                String artist = cursor.getString(artistColumn);
                String filePath = cursor.getString(pathColumn);
                Song song = new Song(title, artist, filePath);
                songList.add(song);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        songs.postValue(songList);
    }

    public void scanMusic() {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = contentResolver.query(uri, projection, selection, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int titleColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            List<Song> songList = new ArrayList<>();

            while (cursor.moveToNext()) {
                String path = cursor.getString(dataColumnIndex);
                String title = cursor.getString(titleColumnIndex);
                String artist = cursor.getString(artistColumnIndex);
                Song song = new Song(title, artist, path);
                songList.add(song);
            }

            cursor.close();

            songs.postValue(songList);
        }
    }
}