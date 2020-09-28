package dev.doyeong.theillogical.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.api.APIClient;
import dev.doyeong.theillogical.api.APIInterface;
import dev.doyeong.theillogical.databinding.FragmentHomeBinding;
import dev.doyeong.theillogical.models.AlbumModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private Context mContext;
    private APIInterface apiInterface;

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
        apiInterface = APIClient.getAPI(getContext());

        recyclerLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        binding.albumRecyclerView.setLayoutManager(this.recyclerLayoutManager);
        recyclerAdapter = new AlbumAdapter();
        binding.albumRecyclerView.setAdapter(recyclerAdapter);
        binding.setAlbumList(albumItems);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        Call<ResponseBody> call = apiInterface.getAlbumList();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String body = response.body().string();
                    JSONObject object = new JSONObject(body);

                    JSONArray data = object.getJSONArray("data");
                    Gson gson = new Gson();

                    for (int i = 0; i < data.length(); i++ ) {
                        JSONObject albumObject = data.getJSONObject(i);
                        AlbumModel album = gson.fromJson(albumObject.toString(), AlbumModel.class);

                        albumItems.add(album);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Toast.makeText(getContext(),"데이터를 가져오는 데 오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}