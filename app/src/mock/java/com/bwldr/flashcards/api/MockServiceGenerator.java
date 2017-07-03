package com.bwldr.flashcards.api;

import com.bwldr.flashcards.db.Card;
import com.bwldr.flashcards.db.Category;
import com.bwldr.flashcards.db.Stack;
import com.bwldr.flashcards.util.MockUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;


public class MockServiceGenerator implements ServiceGeneratorContract {

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
        private final List<Stack> mStacks = new ArrayList<>();
        private final List<Card> mCards = new ArrayList<>();

        MockApiClient(BehaviorDelegate<ApiClient> delegate) {
            this.mDelegate = delegate;
        }

        @Override
        public Call<List<Category>> categoriesList() {
            if (mCategories.size() == 0) {
                List<Category> categories = MockUtil.getCategories();
                for (Category category: categories) {
                    mCategories.add(category);
                }
            }
            return mDelegate.returningResponse(mCategories).categoriesList();
        }

        @Override
        public Call<List<Stack>> stacksList(String categoryId) {
            if (mStacks.size() == 0) {
                List<Stack> stacks = MockUtil.getStacks();
                for (Stack stack: stacks) {
                    if (stack.categoryId.equals(categoryId)) {
                        mStacks.add(stack);
                    }
                }
            }
            return mDelegate.returningResponse(mStacks).stacksList(categoryId);
        }

        @Override
        public Call<List<Card>> cardsList(String stackId) {
            if (mCards.size() == 0) {
                List<Card> cards = MockUtil.getCards();
                for (Card card: cards) {
                    if (card.stackId.equals(stackId)) {
                        mCards.add(card);
                    }
                }
            }
            return mDelegate.returningResponse(mCards).cardsList(stackId);
        }
    }
}
