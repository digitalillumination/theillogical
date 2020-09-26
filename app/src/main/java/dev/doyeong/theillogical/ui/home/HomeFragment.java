package dev.doyeong.theillogical.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.databinding.FragmentHomeBinding;
import dev.doyeong.theillogical.models.AlbumModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private Context mContext;

    private ObservableArrayList<AlbumModel> albumItems = new ObservableArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        recyclerLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        binding.albumRecyclerView.setLayoutManager(this.recyclerLayoutManager);
        recyclerAdapter = new AlbumAdapter();
        binding.albumRecyclerView.setAdapter(recyclerAdapter);
        binding.setAlbumList(albumItems);

        albumItems.add(new AlbumModel("aaa", "Enter The Dil Clan", "Dil", "http://dil.com/"));
        albumItems.add(new AlbumModel("aaa", "Enter The Dil Clan PT. II", "Dil", "http://dil.com/"));
        albumItems.add(new AlbumModel("aaa", "권병옥", "KBO", "http://dil.com/"));
        albumItems.add(new AlbumModel("aaa", "FLEX PART 1: 권병옥의 스위스 계좌", "KBO", "http://dil.com/"));
        return binding.getRoot();
    }

}