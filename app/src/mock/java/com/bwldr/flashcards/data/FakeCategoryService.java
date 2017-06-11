package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.bwldr.flashcards.db.Category;
import com.bwldr.flashcards.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * For testing using mocks, vs. actual CategoryService
 */
public class FakeCategoryService implements CategoryServiceContract {

    private Context mContext;

    public FakeCategoryService(Context context) {
        mContext = context;
    }

    @Override
    public LiveData<List<Category>> selectAll() {
        final List<Category> categories = new ArrayList<>();
        categories.add(new Category(Util.genId(), "Java"));

        return new LiveData<List<Category>>() {
            @Override
            public List<Category> getValue() {
                return categories;
            }
        };
    }
}
