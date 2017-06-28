package com.bwldr.flashcards;

import android.content.Context;

import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.api.MockServiceGenerator;
import com.bwldr.flashcards.data.CategoryRepositoryContract;
import com.bwldr.flashcards.data.MockCategoryRepository;
import com.bwldr.flashcards.data.MockStackRepository;
import com.bwldr.flashcards.data.StackRepositoryContract;


public class Inject {

    private static ApiClient mApiClient = null;
    private static CategoryRepositoryContract mCategoryRepository = null;
    private static StackRepositoryContract mStackRepository = null;

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

    public synchronized static StackRepositoryContract getStackRepository(Context context) {
        if (mStackRepository == null) {
            mStackRepository = new MockStackRepository(context);
        }
        return mStackRepository;
    }
}
