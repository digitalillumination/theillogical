package dev.doyeong.theillogical.ui.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import dev.doyeong.theillogical.databinding.RowSongInPlaylistBinding;
import dev.doyeong.theillogical.models.SongModel;
import dev.doyeong.theillogical.music.Music;
import dev.doyeong.theillogical.music.MusicManager;

public class PlaylistViewAdapter extends RecyclerView.Adapter<PlaylistViewAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MusicManager.getMusicQueue().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Music>>() {
            @Override
            public void onChanged(ObservableList<Music> sender) {
                PlaylistViewAdapter.this.notifyDataSetChanged();

                System.out.println(sender);
            }

            @Override
            public void onItemRangeChanged(ObservableList<Music> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<Music> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeMoved(ObservableList<Music> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Music> sender, int positionStart, int itemCount) {

            }
        });
        return new ViewHolder(
                RowSongInPlaylistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(MusicManager.getMusicQueue().get(position), position);
    }

    @Override
    public int getItemCount() {
        return MusicManager.getMusicQueue().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RowSongInPlaylistBinding binding;
        public ViewHolder(RowSongInPlaylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Music music, int position) {
            music.setOnInformationReceivedListener(song -> {
                binding.setSong(song);
                binding.setIsPlaying(MusicManager.getPosition() == position);
            });

            MusicManager.setOnInformationReceivedListener(new MusicManager.OnInformationReceivedListener() {
                @Override
                public void onInformationReceived(SongModel song) {
                    binding.setIsPlaying(MusicManager.getPosition() == position);
                }

                @Override
                public void onDurationReceived(int duration) {

                }

                @Override
                public void onIsPlayingChanged(boolean isPlaying) {

                }
            });
            binding.getRoot().setOnClickListener(view -> {
                MusicManager.setPositionAndPlay(binding.getRoot().getContext(), position);
            });
        }
    }
}
