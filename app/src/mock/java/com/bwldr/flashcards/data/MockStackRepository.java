package com.bwldr.flashcards.data;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.bwldr.flashcards.db.Stack;

import java.util.ArrayList;
import java.util.List;

public class MockStackRepository implements StackRepositoryContract {

    private final List<Stack> mStacks = new ArrayList<>();
    private final MutableLiveData<List<Stack>> mStacksMutable = new MutableLiveData<>();

    public MockStackRepository(Context context) {
        // "context" not used by "mock" flavor
        mStacksMutable.postValue(mStacks);
    }

    @Override
    public void insert(Stack stack) {
        mStacks.add(stack);

        // call to update Stacks on background thread
        mStacksMutable.postValue(mStacks);
    }

    /**
     * @param categoryId - param not used, only in prod Stack Repo
     */
    @Override
    public LiveData<List<Stack>> selectById(String categoryId) {
        return mStacksMutable;
    }
}
