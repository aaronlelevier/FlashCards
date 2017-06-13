package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.bwldr.flashcards.db.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * For testing using mocks, vs. actual CategoryService
 */
public class FakeCategoryService implements CategoryServiceContract {

    private Context mContext;
    private final List<Category> mCategories = new ArrayList<>();

    public FakeCategoryService(Context context) {
        mContext = context;
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
