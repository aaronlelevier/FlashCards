package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;

import com.bwldr.flashcards.db.Stack;

import java.util.List;

/**
 * Contract to be followed by Fake and Real StackRepository implementations.
 */
public interface StackRepositoryContract {

    LiveData<List<Stack>> selectById(String categoryId);

    void insert(Stack stack);
}
