package com.bwldr.flashcards.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit ServiceGenerator interface
 */
public interface ServiceGeneratorContract {

    String BASE_URL = "http://ff25ac37.ngrok.io/";

    Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
}
