package com.bwldr.flashcards;

import android.content.Context;

import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.api.ServiceGenerator;
import com.bwldr.flashcards.data.CardRepository;
import com.bwldr.flashcards.data.CardRepositoryContract;
import com.bwldr.flashcards.data.CategoryRepository;
import com.bwldr.flashcards.data.CategoryRepositoryContract;
import com.bwldr.flashcards.data.StackRepository;
import com.bwldr.flashcards.data.StackRepositoryContract;


public class Inject {

    private static ApiClient mApiClient = null;
    private static CategoryRepositoryContract mCategoryRepository = null;
    private static StackRepositoryContract mStackRepository = null;
    private static CardRepositoryContract mCardRepository = null;

    private Inject() {
    }

    public synchronized static ApiClient getApiClient() {
        if (mApiClient == null) {
            mApiClient = ServiceGenerator.createService();
        }
        return mApiClient;
    }

    public synchronized static CategoryRepositoryContract getCategoryRepository(Context context) {
        if (mCategoryRepository == null) {
            mCategoryRepository = new CategoryRepository(context);
        }
        return mCategoryRepository;
    }

    public synchronized static StackRepositoryContract getStackRepository(Context context) {
        if (mStackRepository == null) {
            mStackRepository = new StackRepository(context);
        }
        return mStackRepository;
    }

    public synchronized static CardRepositoryContract getCardRepository(Context context) {
        if (mCardRepository == null) {
            mCardRepository = new CardRepository(context);
        }
        return mCardRepository;
    }
}
