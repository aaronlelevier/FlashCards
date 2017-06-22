package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.bwldr.flashcards.db.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * For testing using mocks, vs. actual CategoryRepository
 */
public class FakeCategoryRepository implements CategoryRepositoryContract {

    private final List<Category> mCategories = new ArrayList<>();
    private final MutableLiveData<List<Category>> mCategoriesMutable = new MutableLiveData<>();

    public FakeCategoryRepository(Context context) {
        // not used by "mock" flavor at this time
    }

    public void insert(Category category) {
        mCategories.add(category);
    }

    public List<Category> getCategories() {
        return mCategories;
    }

    @Override
    public void setCategories(List<Category> categories) {
        mCategoriesMutable.postValue(categories);
    }

    @Override
    public LiveData<List<Category>> selectAll() {
        return mCategoriesMutable;
    }
}
