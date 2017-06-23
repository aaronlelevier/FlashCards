package com.bwldr.flashcards.api;

/**
 * Created by aaron on 6/6/17.
 */

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

import static com.bwldr.flashcards.api.ServiceGeneratorContract.builder;
import static com.bwldr.flashcards.api.ServiceGeneratorContract.httpClient;

/**
 * Generates a singleton OkHttpClient, and handles Interceptor logic
 */
public class ServiceGenerator {

    private static Retrofit retrofit;

    /** Non-Generic */
    public static ApiClient createService() {
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(ApiClient.class);
    }

    // TODO: have not yet implemented with AuthToken here, and on "bwldr-rest-api"
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
