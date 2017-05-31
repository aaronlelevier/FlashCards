package com.bwldr.flashcards.category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.bwldr.flashcards.db.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<List<Category>> mData = new MutableLiveData<>();

    public CategoryViewModel() {
        List<Category> data = new ArrayList<>();
        data.add(new Category("Java"));
        data.add(new Category("Python"));
        data.add(new Category("Javascript"));
        mData.setValue(data);
    }

    public LiveData<List<Category>> getListData() {
        return mData;
    }
}
