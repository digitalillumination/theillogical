package dev.doyeong.theillogical.api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token;
    public AuthInterceptor(String token) {
        this.token = token;
    }
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder().addHeader("Authorization", "Bearer " + this.token);

        Request request = builder.build();
        return chain.proceed(request);
    }
}
