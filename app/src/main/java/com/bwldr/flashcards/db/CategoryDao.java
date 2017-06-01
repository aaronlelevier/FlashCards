package com.bwldr.flashcards.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> selectAll();

    @Insert(onConflict = REPLACE)
    void insert(Category category);
}

