package com.bwldr.flashcards.api;

/**
 * Created by aaron on 6/6/17.
 */

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Generates a singleton OkHttpClient, and handles Interceptor logic
 */
public class ServiceGenerator {

    private static Retrofit retrofit;

    private static final String BASE_URL = "https://api.github.com/"; // TODO: change to bwldr api URL

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            Interceptor authInterceptor = getAuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(authInterceptor)) {
                httpClient.addInterceptor(authInterceptor);
            }

            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

    private static Interceptor getAuthenticationInterceptor(final String authToken) {
        Interceptor authInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", authToken);

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        };
        return authInterceptor;
    }
}
