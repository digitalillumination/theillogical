package dev.doyeong.theillogical.ui.album;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.doyeong.theillogical.databinding.RowSongBinding;

public class AlbumViewAdapter extends RecyclerView.Adapter<AlbumViewAdapter.ViewHolder> {
    private List<String> list;
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
        holder.bind(list.get(position), position + 1);
    }

    public void setList(List<String> list) {
        this.list = list;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowSongBinding binding;
        public ViewHolder(RowSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(String item, int index) {
            binding.setTitle(item);
            binding.setIndex(index);
        }
    }
}
