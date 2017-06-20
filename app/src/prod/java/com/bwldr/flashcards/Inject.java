package com.bwldr.flashcards;

import android.content.Context;

import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.api.ServiceGenerator;
import com.bwldr.flashcards.data.CategoryRepository;
import com.bwldr.flashcards.data.CategoryRepositoryContract;


public class Inject {

    private static ApiClient mApiClient = null;
    private static CategoryRepositoryContract mCategoryRepository = null;

    private Inject() {
    }

    public synchronized static ApiClient getApiClient() {
        if (mApiClient == null) {
            mApiClient = ServiceGenerator.createService(ApiClient.class);
        }
        return mApiClient;
    }

    public synchronized static CategoryRepositoryContract getCategoryRepository(Context context) {
        if (mCategoryRepository == null) {
            mCategoryRepository = new CategoryRepository(context);
        }
        return mCategoryRepository;
    }
}
