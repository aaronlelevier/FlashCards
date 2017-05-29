package com.bwldr.flashcards.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CardDao {

    @Query("SELECT * FROM Card")
    public List<Card> selectAll();

    @Insert(onConflict = REPLACE)
    public void insert(Card card);

    @Query("SELECT * FROM Card WHERE stack_id = :id")
    List<Card> selectByStackId(String id);
}
