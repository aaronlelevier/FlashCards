package com.bwldr.flashcards.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import com.bwldr.flashcards.db.AppDatabase;
import com.bwldr.flashcards.db.Category;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private AppDatabase mDb;
    private LiveData<List<Category>> mCategories;

    public CategoryViewModel(Application application) {
        super(application);
        mDb = Room.inMemoryDatabaseBuilder(this.getApplication(), AppDatabase.class).build();

        CategoryGenAsyncTask categoryGenTask = new CategoryGenAsyncTask();
        categoryGenTask.execute();

        mCategories = mDb.categoryDao().selectAll();
    }

    public LiveData<List<Category>> getListData() {
        return mCategories;
    }

    private class CategoryGenAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
//            mDb.categoryDao().insert(new Category("Java"));
//            mDb.categoryDao().insert(new Category("Python"));
//            mDb.categoryDao().insert(new Category("Javascript"));
            return null;
        }
    }
}
