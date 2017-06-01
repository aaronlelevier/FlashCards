package com.bwldr.flashcards.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.bwldr.flashcards.db.AppDatabase;
import com.bwldr.flashcards.db.Category;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel implements Handler.Callback {

    private static final int MSG_START = 1;
    private static final int MSG_LOOPER_PREPARED = 2;
    private static final int MSG_GENERATED_CATEGORIES = 3;
    private static final int MSG_STOP = 4;

    private Handler mHandler;
    private CategoryGenThread mCategoryGenThread;

    private AppDatabase mDb;
    private LiveData<List<Category>> mCategories;

    public CategoryViewModel(Application application) {
        super(application);
        mDb = Room.inMemoryDatabaseBuilder(this.getApplication(), AppDatabase.class).build();

        mHandler = new Handler(this);
        mCategoryGenThread = new CategoryGenThread("CategoryGenThread");
        mCategoryGenThread.start(); // handles populating of DB

        mCategories = mDb.categoryDao().selectAll();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_LOOPER_PREPARED:
                mCategoryGenThread.mWorkerHandler.obtainMessage(MSG_START)
                        .sendToTarget();
                break;
            case MSG_GENERATED_CATEGORIES:
                joinCategoryGenThread();
                break;
        }
        return true;
    }

    public LiveData<List<Category>> getListData() {
        return mCategories;
    }

    private void joinCategoryGenThread() {
        mCategoryGenThread.mWorkerHandler.obtainMessage(MSG_STOP)
                .sendToTarget();
        try {
            mCategoryGenThread.join();
        } catch (InterruptedException e) {
        }
    }

    private class CategoryGenThread extends HandlerThread implements Handler.Callback {

        Handler mWorkerHandler;

        public CategoryGenThread(String name) {
            super(name);
        }

        @Override
        protected void onLooperPrepared() {
            mWorkerHandler = new Handler(getLooper(), this);

            mHandler.obtainMessage(MSG_LOOPER_PREPARED)
                    .sendToTarget();
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START:
                    generateCategories();
                    mHandler.obtainMessage(MSG_GENERATED_CATEGORIES)
                            .sendToTarget();
                    break;
                case MSG_STOP:
                    getLooper().quit();
                    break;
            }
            return true;
        }

        private void generateCategories() {
            mDb.categoryDao().insert(new Category("Java"));
            mDb.categoryDao().insert(new Category("Python"));
            mDb.categoryDao().insert(new Category("Javascript"));
        }
    }
}
