package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.bwldr.flashcards.db.AppDatabase;
import com.bwldr.flashcards.db.Stack;

import java.util.List;


public class StackRepository implements StackRepositoryContract {

    private AppDatabase mDb;

    public StackRepository(Context context) {
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
    }

    @Override
    public LiveData<List<Stack>> selectById(String id) {
        return mDb.stackDao().selectByCategoryId(id);
    }

    @Override
    public void insert(Stack stack) {
        mDb.stackDao().insert(stack);
    }
}
