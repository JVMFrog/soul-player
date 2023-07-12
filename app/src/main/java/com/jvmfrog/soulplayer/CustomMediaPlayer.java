package com.jvmfrog.soulplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomMediaPlayer {
    private static CustomMediaPlayer instance;

    private MediaPlayer mediaPlayer;
    private List<String> trackList;
    private int currentTrackIndex;
    private int savedPosition;
    private boolean isLooping;
    private SeekBar seekBar;
    private boolean isSeekBarTracking;
    private MediaMetadataRetriever metadataRetriever;
    private Context context;

    private CustomMediaPlayer(Context context) {
        trackList = new ArrayList<>();
        currentTrackIndex = 0;
        savedPosition = 0;
        isLooping = false;
        mediaPlayer = new MediaPlayer();
        metadataRetriever = new MediaMetadataRetriever();
        this.context = context.getApplicationContext();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (isLooping) {
                    play();
                } else {
                    playNext();
                }
            }
        });
    }

    public static CustomMediaPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new CustomMediaPlayer(context);
        }
        return instance;
    }

    public void setTrackList(List<String> tracks) {
        trackList.clear();
        trackList.addAll(tracks);
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
        setupSeekBar();
    }

    private void setupSeekBar() {
        if (seekBar != null) {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (isSeekBarTracking) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    isSeekBarTracking = true;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    isSeekBarTracking = false;
                }
            });
        }
    }

    public void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            if (savedPosition > 0) {
                mediaPlayer.seekTo(savedPosition);
            } else {
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(trackList.get(currentTrackIndex));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            savedPosition = mediaPlayer.getCurrentPosition();
        }
    }

    public void playNext() {
        if (currentTrackIndex < trackList.size() - 1) {
            currentTrackIndex++;
            savedPosition = 0;
            play();
        }
    }

    public void playPrevious() {
        if (currentTrackIndex > 0) {
            currentTrackIndex--;
            savedPosition = 0;
            play();
        }
    }

    public void toggleLooping() {
        isLooping = !isLooping;
        mediaPlayer.setLooping(isLooping);
    }

    public void seekTo(int progress) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(progress);
        }
    }

    public void setCurrentTrackIndex(int index) {
        currentTrackIndex = index;
    }

    public void addToQueue(String trackPath, boolean addToEnd) {
        if (addToEnd) {
            trackList.add(trackPath);
        } else {
            trackList.add(currentTrackIndex + 1, trackPath);
        }
    }

    public String getCurrentTrackTitle() {
        if (currentTrackIndex >= 0 && currentTrackIndex < trackList.size()) {
            metadataRetriever.setDataSource(trackList.get(currentTrackIndex));
            return metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        }
        return "";
    }

    public String getCurrentTrackAlbum() {
        if (currentTrackIndex >= 0 && currentTrackIndex < trackList.size()) {
            metadataRetriever.setDataSource(trackList.get(currentTrackIndex));
            return metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        }
        return "";
    }

    public String getCurrentTrackDuration() {
        if (mediaPlayer != null) {
            int duration = mediaPlayer.getDuration();
            int minutes = duration / 60000;
            int seconds = (duration % 60000) / 1000;
            return String.format("%02d:%02d", minutes, seconds);
        }
        return "";
    }

    public String getCurrentTrackGenre() {
        if (currentTrackIndex >= 0 && currentTrackIndex < trackList.size()) {
            metadataRetriever.setDataSource(trackList.get(currentTrackIndex));
            return metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        }
        return "";
    }

    public Bitmap getCurrentTrackAlbumArt() {
        if (currentTrackIndex >= 0 && currentTrackIndex < trackList.size()) {
            metadataRetriever.setDataSource(trackList.get(currentTrackIndex));
            byte[] albumArt = metadataRetriever.getEmbeddedPicture();
            if (albumArt != null) {
                return BitmapFactory.decodeByteArray(albumArt, 0, albumArt.length);
            }
        }
        return null;
    }

    public void scanMusic() {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM
        };

        Cursor cursor = contentResolver.query(uri, projection, selection, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            while (cursor.moveToNext()) {
                String path = cursor.getString(dataColumnIndex);
                trackList.add(path);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    public void scanAlbums() {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS
        };
        String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        Cursor cursor = contentResolver.query(uri, projection, null, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            int albumColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM);
            while (cursor.moveToNext()) {
                String albumName = cursor.getString(albumColumnIndex);
                // Логика обработки альбомов
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    public void scanPlaylists() {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Playlists.NAME
        };
        String sortOrder = MediaStore.Audio.Playlists.NAME + " ASC";

        Cursor cursor = contentResolver.query(uri, projection, null, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            int playlistColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.NAME);
            while (cursor.moveToNext()) {
                String playlistName = cursor.getString(playlistColumnIndex);
                // Логика обработки плейлистов
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    public List<String> getTrackList() {
        return trackList;
    }

    public void release() throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (metadataRetriever != null) {
            metadataRetriever.release();
            metadataRetriever = null;
        }
    }
}