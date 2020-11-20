package dev.doyeong.theillogical.ui.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.doyeong.theillogical.music.Music;
import dev.doyeong.theillogical.music.MusicManager;
import dev.doyeong.theillogical.databinding.RowSongBinding;

public class AlbumViewAdapter extends RecyclerView.Adapter<AlbumViewAdapter.ViewHolder> {
    private List<String> list;
    private String albumId;
    public AlbumViewAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowSongBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), position + 1, this.albumId);
    }

    public void setList(List<String> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @BindingAdapter("items")
    public static void bindItem(RecyclerView view, ObservableArrayList<String> list) {
        AlbumViewAdapter adapter = (AlbumViewAdapter) view.getAdapter();

        if (adapter != null) adapter.setList(list);
    }
    @BindingAdapter("albumId")
    public static void bindItem(RecyclerView view, String albumId) {
        AlbumViewAdapter adapter = (AlbumViewAdapter) view.getAdapter();

        if (adapter != null) adapter.setAlbumId(albumId);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowSongBinding binding;
        public ViewHolder(RowSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(String item, int index, String albumId) {
            binding.setTitle(item);
            binding.setIndex(index);
            binding.songLayout.setOnClickListener(view -> {
                // TODO: Start music player service

                Context context = binding.getRoot().getContext();

                Music music = new Music(albumId, index);
                MusicManager.startMusic(context, music);
            });
        }
    }
}
