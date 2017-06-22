package com.bwldr.flashcards.api;

import com.bwldr.flashcards.db.Category;
import com.bwldr.flashcards.util.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;


public class FakeServiceGenerator {

    private static final String BASE_URL = "http://cd03e7e8.ngrok.io/";

    public static ApiClient createServiceApiClient() {
        // Create a very simple Retrofit adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        // Create a MockRetrofit object with a NetworkBehavior which manages the fake behavior of calls.
        NetworkBehavior behavior = NetworkBehavior.create();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();

        BehaviorDelegate<ApiClient> delegate = mockRetrofit.create(ApiClient.class);
        return new MockApiClient(delegate);
    }

    public static class MockApiClient implements ApiClient {

        private final BehaviorDelegate<ApiClient> mDelegate;
        private final List<Category> mCategories = new ArrayList<>();

        public MockApiClient(BehaviorDelegate<ApiClient> delegate) {
            this.mDelegate = delegate;

            mCategories.add(Util.create_category("Java"));
        }

        @Override
        public Call<List<Category>> categoriesList() {
            return mDelegate.returningResponse(mCategories).categoriesList();
        }
    }
}
