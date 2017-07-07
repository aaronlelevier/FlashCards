package com.bwldr.flashcards.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CardDao {

    @Query("SELECT * FROM Card WHERE stackId = :id")
    List<Card> selectByStackId(String id);

    @Insert(onConflict = REPLACE)
    void insert(Card card);
}
