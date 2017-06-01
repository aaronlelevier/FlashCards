package com.bwldr.flashcards.category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.bwldr.flashcards.db.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel implements Handler.Callback {

    private static final int MSG_START = 1;
    private static final int MSG_LOOPER_PREPARED = 2;
    private static final int MSG_GENERATED_CATEGORIES = 3;
    private static final int MSG_STOP = 4;

    private Handler mHandler;
    private CategoryGenThread mCategoryGenThread;

    private MutableLiveData<List<Category>> mData = new MutableLiveData<>();

    public CategoryViewModel() {
        mHandler = new Handler(this);
        mCategoryGenThread = new CategoryGenThread("CategoryGenThread");
        mCategoryGenThread.start();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_LOOPER_PREPARED:
                mCategoryGenThread.mWorkerHandler.obtainMessage(MSG_START)
                        .sendToTarget();
                break;
            case MSG_GENERATED_CATEGORIES:
                setCategories((List<Category>)msg.obj);
                break;
        }
        return true;
    }

    public LiveData<List<Category>> getListData() {
        return mData;
    }

    private void setCategories(List<Category> data) {
        mData.setValue(data);
        joinCategoryGenThread();
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
                    List<Category> categories = generateCategories();
                    mHandler.obtainMessage(MSG_GENERATED_CATEGORIES, categories)
                            .sendToTarget();
                    break;
                case MSG_STOP:
                    getLooper().quit();
                    break;
            }
            return true;
        }

        private List<Category> generateCategories() {
            List<Category> data = new ArrayList<>();
            data.add(new Category("Java"));
            data.add(new Category("Python"));
            data.add(new Category("Javascript"));
            return data;
        }
    }
}
