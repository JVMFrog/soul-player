package com.jvmfrog.soulplayer;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.SeekBar;

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
    private CountDownTimer sleepTimer;

    private CustomMediaPlayer() {
        trackList = new ArrayList<>();
        currentTrackIndex = 0;
        savedPosition = 0;
        isLooping = false;
        mediaPlayer = new MediaPlayer();
        metadataRetriever = new MediaMetadataRetriever();

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

    public static CustomMediaPlayer getInstance() {
        if (instance == null) {
            instance = new CustomMediaPlayer();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mediaPlayer.start();
            startSleepTimer(); // Start sleep timer when playing
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            savedPosition = mediaPlayer.getCurrentPosition();
            cancelSleepTimer(); // Cancel sleep timer when paused
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

    public void setSleepTimer(long milliseconds) {
        cancelSleepTimer();

        sleepTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                
            }

            @Override
            public void onFinish() {
                pause();
            }
        };
    }

    public void startSleepTimer() {
        if (sleepTimer != null) {
            sleepTimer.start();
        }
    }

    public void cancelSleepTimer() {
        if (sleepTimer != null) {
            sleepTimer.cancel();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (metadataRetriever != null) {
            metadataRetriever.release();
            metadataRetriever = null;
        }
        cancelSleepTimer();
    }
}