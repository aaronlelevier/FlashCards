package com.bwldr.flashcards.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.bwldr.flashcards.Inject;
import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.data.CategoryRepositoryContract;
import com.bwldr.flashcards.db.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepositoryContract mCategoryRepo;
    private LiveData<List<Category>> mCategories;

    public CategoryViewModel(Application application) {
        super(application);

        // Inject Repo
        mCategoryRepo = Inject.getCategoryRepository(this.getApplication());
        mCategories = mCategoryRepo.selectAll();

        // Inject ApiClient
        ApiClient client = Inject.getApiClient();
        Call<List<Category>> call = client.categoriesList();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                InsertCategoriesTask task = new InsertCategoriesTask();
                task.execute(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("onFailuire", "error");
            }
        });
    }

    LiveData<List<Category>> getListData() {
        return mCategories;
    }

    Category getListItem(int position) {
        if (mCategories.getValue() != null)
            return mCategories.getValue().get(position);
        return null;
    }

    private class InsertCategoriesTask extends AsyncTask<List<Category>, Void, Void> {

        @Override
        protected Void doInBackground(List<Category>... params) {
            if (params[0] != null) {
                List<Category> data = params[0];
                for (Category category : data) {
                    mCategoryRepo.insert(category);
                }
            }
            return null;
        }
    }
}
