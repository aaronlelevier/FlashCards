package com.bwldr.flashcards;

import android.content.Context;

import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.api.MockServiceGenerator;
import com.bwldr.flashcards.data.CategoryRepositoryContract;
import com.bwldr.flashcards.data.MockCategoryRepository;


public class Inject {

    private static ApiClient mApiClient = null;
    private static CategoryRepositoryContract mCategoryRepository = null;

    private Inject() {
    }

    public static ApiClient getApiClient() {
        if (mApiClient == null) {
            mApiClient = MockServiceGenerator.createService();
        }
        return mApiClient;
    }

    public synchronized static CategoryRepositoryContract getCategoryRepository(Context context) {
        if (mCategoryRepository == null) {
            mCategoryRepository = new MockCategoryRepository(context);
        }
        return mCategoryRepository;
    }
}
