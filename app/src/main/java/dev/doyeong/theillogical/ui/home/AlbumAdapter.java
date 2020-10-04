package dev.doyeong.theillogical.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dev.doyeong.theillogical.databinding.RowAlbumBinding;
import dev.doyeong.theillogical.models.AlbumModel;
import dev.doyeong.theillogical.ui.album.AlbumActivity;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private List<AlbumModel> list;
    public AlbumAdapter() {
        list = new ArrayList<>();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlbumModel item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<AlbumModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }
    @BindingAdapter("items")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<AlbumModel> items) {
        AlbumAdapter adapter = (AlbumAdapter) recyclerView.getAdapter();

        if (adapter != null) adapter.setList(items);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowAlbumBinding binding;

        public ViewHolder(RowAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(AlbumModel item) {
            binding.setAlbum(item);
            binding.rowAlbum.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), AlbumActivity.class);

                intent.putExtra("albumId", item.getId());
                view.getContext().startActivity(intent);
            });
        }
    }
}
