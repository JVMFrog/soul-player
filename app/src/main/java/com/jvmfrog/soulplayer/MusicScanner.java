package com.jvmfrog.soulplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicScanner {

    public static List<HashMap<String, String>> getAlbums (Context context) {
        List<HashMap<String, String>> albumsList = new ArrayList<>();
        String[] projection = {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST, MediaStore.Audio.Albums.ALBUM_ART};
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                HashMap<String, String> album = new HashMap<>();
                album.put("albumId", cursor.getString(0));
                album.put("albumTitle", cursor.getString(1));
                album.put("albumArtist", cursor.getString(2));
                album.put("albumArt", cursor.getString(3));
                albumsList.add(album);
            } while (cursor.moveToNext());
        } if (cursor != null)
            cursor.close();
        return albumsList;
    }
}
