package dev.doyeong.theillogical.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("api/v1/album")
    Call<ResponseBody> getAlbumList();
}
