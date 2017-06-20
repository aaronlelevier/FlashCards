package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.bwldr.flashcards.db.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * For testing using mocks, vs. actual CategoryRepository
 */
public class FakeCategoryRepository implements CategoryRepositoryContract {

    private Context mContext;
    private final List<Category> mCategories = new ArrayList<>();

    public FakeCategoryRepository(Context context) {
        // not used by "mock" flavor at this time
    }

    public void insert(Category category) {
        mCategories.add(category);
    }

    @Override
    public LiveData<List<Category>> selectAll() {
        return new LiveData<List<Category>>() {
            @Override
            public List<Category> getValue() {
                return mCategories;
            }
        };
    }
}
