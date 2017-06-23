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


public class FakeServiceGenerator implements ServiceGeneratorContract {

    public static ApiClient createService() {
        // Create a very simple Retrofit adapter which points the API.
        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();

        // Create a MockRetrofit object with a NetworkBehavior which manages the fake behavior of calls.
        NetworkBehavior behavior = NetworkBehavior.create();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();

        BehaviorDelegate<ApiClient> delegate = mockRetrofit.create(ApiClient.class);
        return new MockApiClient(delegate);
    }

    private static class MockApiClient implements ApiClient {

        private final BehaviorDelegate<ApiClient> mDelegate;
        private final List<Category> mCategories = new ArrayList<>();

        MockApiClient(BehaviorDelegate<ApiClient> delegate) {
            this.mDelegate = delegate;

            mCategories.add(Util.create_category("Java"));
        }

        @Override
        public Call<List<Category>> categoriesList() {
            return mDelegate.returningResponse(mCategories).categoriesList();
        }
    }
}
