package com.jvmfrog.soulplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvmfrog.soulplayer.CustomMediaPlayer;
import com.jvmfrog.soulplayer.R;

import java.util.List;

public class CustomMediaPlayerAdapter extends RecyclerView.Adapter<CustomMediaPlayerAdapter.ViewHolder> {
    private Context context;
    private List<String> trackList;
    private LayoutInflater inflater;
    private CustomMediaPlayer mediaPlayer;

    public CustomMediaPlayerAdapter(Context context, List<String> trackList) {
        this.context = context;
        this.trackList = trackList;
        inflater = LayoutInflater.from(context);
        mediaPlayer = CustomMediaPlayer.getInstance(context);
    }

    public void setTrackList(List<String> trackList) {
        this.trackList = trackList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_single_song, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String trackPath = trackList.get(position);
        mediaPlayer.setTrackList(trackList);
        holder.titleTextView.setText(mediaPlayer.getCurrentTrackTitle());
        holder.artistTextView.setText(mediaPlayer.getCurrentTrackAlbum());
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView artistTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleView);
            artistTextView = itemView.findViewById(R.id.artistView);
        }
    }
}