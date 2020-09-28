package dev.doyeong.theillogical.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import dev.doyeong.theillogical.R;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private Retrofit retrofit = null;

    private APIClient(Context context) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create());
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        String token = APIUtils.getLoginToken(context);
        if (token != null) {
            AuthInterceptor interceptor = new AuthInterceptor(token);

            if (!httpBuilder.interceptors().contains(interceptor)) {
                httpBuilder.addInterceptor(interceptor);
            }
        }
        builder.client(httpBuilder.build());
        retrofit = builder.build();
    }
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static APIClient getInstance(Context context) {
        return new APIClient(context);
    }
    public static APIInterface getAPI(Context context) {
        return getInstance(context).getRetrofit().create(APIInterface.class);
    }
}
