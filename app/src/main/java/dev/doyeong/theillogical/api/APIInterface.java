package dev.doyeong.theillogical.api;

import dev.doyeong.theillogical.models.SignUpRequestModel;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    @GET("api/v1/album")
    Call<ResponseBody> getAlbumList();
    @POST("api/v1/user")
    Call<ResponseBody> createUser(
            @Body SignUpRequestModel body
    );
}
