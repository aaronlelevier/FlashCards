package com.bwldr.flashcards;

import android.content.Context;

import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.api.ServiceGenerator;
import com.bwldr.flashcards.data.CategoryRepositoryContract;
import com.bwldr.flashcards.data.FakeCategoryRepository;


public class Inject {

    private static ApiClient mApiClient = null;
    private static CategoryRepositoryContract mCategoryRepository = null;

    private Inject() {
    }

    // TODO: this should return "FakeServiceGenerator"
    public static ApiClient getApiClient() {
        if (mApiClient == null) {
            mApiClient = ServiceGenerator.createService(ApiClient.class);
        }
        return mApiClient;
    }

    public synchronized static CategoryRepositoryContract getCategoryRepository(Context context) {
        if (mCategoryRepository == null) {
            mCategoryRepository = new FakeCategoryRepository(context);
        }
        return mCategoryRepository;
    }
}
