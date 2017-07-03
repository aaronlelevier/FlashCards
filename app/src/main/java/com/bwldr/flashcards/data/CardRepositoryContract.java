package com.bwldr.flashcards.data;


import android.arch.lifecycle.LiveData;

import com.bwldr.flashcards.db.Card;

import java.util.List;

public interface CardRepositoryContract {

    LiveData<List<Card>> selectById(String id);

    void insert(Card card);
}
