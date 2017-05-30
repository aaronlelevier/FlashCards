package com.bwldr.flashcards.category;

import android.arch.lifecycle.ViewModel;

import com.bwldr.flashcards.db.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {

    public List<Category> mData = new ArrayList<>();

    public CategoryViewModel() {
        mData.add(new Category("Java"));
        mData.add(new Category("Python"));
        mData.add(new Category("Javascript"));
    }
}
