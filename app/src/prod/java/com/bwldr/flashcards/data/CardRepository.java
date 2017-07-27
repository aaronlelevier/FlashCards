package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.bwldr.flashcards.db.AppDatabase;
import com.bwldr.flashcards.db.Card;

import java.util.HashSet;
import java.util.List;


public class CardRepository implements CardRepositoryContract {

    private AppDatabase mDb;

    public CardRepository(Context context) {
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
    }

    @Override
    public LiveData<List<Card>> selectById(String id) {
        return mDb.cardDao().selectByStackId(id);
    }

    @Override
    public void insert(Card card) {
        mDb.cardDao().insert(card);
    }

    @Override
    public LiveData<List<Card>> selectByCardIdIn(HashSet<String> cardIds) {
        return mDb.cardDao().selectByCardIdIn(cardIds.toArray(new String[cardIds.size()]));
    }
}
