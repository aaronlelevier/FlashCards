package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.bwldr.flashcards.db.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 7/3/17.
 */

public class MockCardRepository implements CardRepositoryContract {

    private final List<Card> mCards = new ArrayList<>();
    private final MutableLiveData<List<Card>> mCardsMutable = new MutableLiveData<>();

    public MockCardRepository(Context context) {
    }

    @Override
    public LiveData<List<Card>> selectById(String id) {
        // categoryId - param not used, only in prod Stack Repo
        return mCardsMutable;
    }

    @Override
    public void insert(Card card) {
        mCards.add(card);

        // call to update Stacks on background thread
        mCardsMutable.postValue(mCards);
    }
}
