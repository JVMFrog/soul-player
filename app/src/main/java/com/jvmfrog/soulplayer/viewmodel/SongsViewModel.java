package com.jvmfrog.soulplayer.viewmodel;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jvmfrog.soulplayer.model.SongsModel;

import java.util.ArrayList;
import java.util.List;

public class SongsViewModel extends ViewModel {
    private MutableLiveData<List<SongsModel>> songFilesLiveData = new MutableLiveData<>();
    public LiveData<List<SongsModel>> getSongFiles() {
        return songFilesLiveData;
    }

    public void loadSongFiles(Context context) {
        List<SongsModel> musicFiles = new ArrayList<>();

        ContentResolver musicResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int pathColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String title = musicCursor.getString(titleColumn);
                String artist = musicCursor.getString(artistColumn);
                String album = musicCursor.getString(albumColumn);
                String path = musicCursor.getString(pathColumn);

                SongsModel musicFile = new SongsModel(title, artist, album, path);
                musicFiles.add(musicFile);
            } while (musicCursor.moveToNext());
        }

        if (musicCursor != null) {
            musicCursor.close();
        }

        if (!musicFiles.isEmpty()) {
            songFilesLiveData.setValue(musicFiles);
        }
    }
}