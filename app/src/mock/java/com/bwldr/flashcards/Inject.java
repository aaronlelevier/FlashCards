package com.bwldr.flashcards;

import android.content.Context;

import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.api.MockServiceGenerator;
import com.bwldr.flashcards.data.CardRepositoryContract;
import com.bwldr.flashcards.data.CategoryRepositoryContract;
import com.bwldr.flashcards.data.MockCardRepository;
import com.bwldr.flashcards.data.MockCategoryRepository;
import com.bwldr.flashcards.data.MockStackRepository;
import com.bwldr.flashcards.data.StackRepositoryContract;


public class Inject {

    private Inject() {
    }

    public static ApiClient getApiClient() {
        return MockServiceGenerator.createService();
    }

    public synchronized static CategoryRepositoryContract getCategoryRepository(Context context) {
        return new MockCategoryRepository(context);
    }

    public synchronized static StackRepositoryContract getStackRepository(Context context) {
        return new MockStackRepository(context);
    }

    public synchronized static CardRepositoryContract getCardRepository(Context context) {
        return new MockCardRepository(context);
    }
}
