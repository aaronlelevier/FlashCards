package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.bwldr.flashcards.db.AppDatabase;
import com.bwldr.flashcards.db.Category;

import java.util.List;


public class CategoryRepository implements CategoryRepositoryContract {

    private AppDatabase mDb;
    private LiveData<List<Category>> mCategories;

    public CategoryRepository(Context context) {
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
    }

    @Override
    public LiveData<List<Category>> selectAll() {
        if (mCategories == null) {
            mCategories = mDb.categoryDao().selectAll();
        }
        return mCategories;
    }

    @Override
    public void insert(Category category) {
        mDb.categoryDao().insert(category);
    }
}
