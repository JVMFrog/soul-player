package com.jvmfrog.soulplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.jvmfrog.soulplayer.R;
import com.jvmfrog.soulplayer.model.SongsModel;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SongsModel> model = new ArrayList<>();

    public SongsAdapter(List<SongsModel> model) {
        this.model = model;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_song, parent, false);
        return new DEFAULT_ITEM(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SongsModel song = model.get(position);
        if (song == null)
            return;

        DEFAULT_ITEM default_item = (DEFAULT_ITEM) holder;
        default_item.title.setText(song.getTitle());
        default_item.artist.setText(song.getArtist());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class DEFAULT_ITEM extends RecyclerView.ViewHolder {
        private AppCompatImageView image;
        private MaterialTextView title, artist;
        public DEFAULT_ITEM(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.titleView);
            artist = itemView.findViewById(R.id.artistView);
        }
    }
}
