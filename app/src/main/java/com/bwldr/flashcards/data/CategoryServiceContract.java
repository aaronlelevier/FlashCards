package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;

import com.bwldr.flashcards.db.Category;

import java.util.List;

/**
 * Contract to be followed by Fake and Real CategoryService implementations.
 */
public interface CategoryServiceContract {

    LiveData<List<Category>> selectAll();

    void insert(Category category);
}
