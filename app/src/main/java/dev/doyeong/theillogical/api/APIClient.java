package dev.doyeong.theillogical.api;

import android.content.Context;

import dev.doyeong.theillogical.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private Retrofit retrofit = null;
    private static APIClient instance = null;

    private APIClient(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static APIClient getInstance(Context context) {
        if (instance == null) instance = new APIClient(context);
        return instance;
    }
}
