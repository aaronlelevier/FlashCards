package com.bwldr.flashcards.stack;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.bwldr.flashcards.Inject;
import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.data.StackRepositoryContract;
import com.bwldr.flashcards.db.Stack;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StackViewModel extends AndroidViewModel {

    private StackRepositoryContract mStackRepo;
    private LiveData<List<Stack>> mStacks;

    public StackViewModel(Application application) {
        super(application);
    }

    public void getStacks(String categoryId) {
        // Inject Repo
        mStackRepo = Inject.getStackRepository(this.getApplication());
        mStacks = mStackRepo.selectById(categoryId);

        // Inject ApiClient
        ApiClient client = Inject.getApiClient();
        Call<List<Stack>> call = client.stacksList(categoryId);
        call.enqueue(new Callback<List<Stack>>() {
            @Override
            public void onResponse(Call<List<Stack>> call, Response<List<Stack>> response) {
                InsertStacksTask task = new InsertStacksTask();
                task.execute(response.body());
            }

            @Override
            public void onFailure(Call<List<Stack>> call, Throwable t) {
                Log.d("onFailuire", "error");
            }
        });
    }

    LiveData<List<Stack>> getListData() {
        return mStacks;
    }

    Stack getListItem(int position) {
        if (mStacks.getValue() != null)
            return mStacks.getValue().get(position);
        return null;
    }

    private class InsertStacksTask extends AsyncTask<List<Stack>, Void, Void> {

        @Override
        protected Void doInBackground(List<Stack>... params) {
            if (params[0] != null) {
                List<Stack> data = params[0];
                for (Stack stack : data) {
                    Log.d("doInBackground", stack.toString());
                    mStackRepo.insert(stack);
                }
            }
            return null;
        }
    }
}
