package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.bwldr.flashcards.db.Card;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


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

    @Override
    public LiveData<List<Card>> selectByCardIdIn(HashSet<String> cardIds) {
        List<Card> cards = new ArrayList<>();
        for (Card card : mCardsMutable.getValue()) {
            if (cardIds.contains(card.id)) {
                cards.add(card);
            }
        }
        mCardsMutable.postValue(cards);
        return mCardsMutable;
    }
}
