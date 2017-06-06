package com.bwldr.flashcards.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.util.Log;

import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.api.ServiceGenerator;
import com.bwldr.flashcards.db.AppDatabase;
import com.bwldr.flashcards.db.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends AndroidViewModel {

    private AppDatabase mDb;
    private LiveData<List<Category>> mCategories;

    public CategoryViewModel(Application application) {
        super(application);
        mDb = Room.inMemoryDatabaseBuilder(this.getApplication(), AppDatabase.class).build();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        Call<List<Category>> call = client.categoriesList();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d("onResponse", response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("onFailuire", "error");
            }
        });

        mCategories = mDb.categoryDao().selectAll();
    }

    public LiveData<List<Category>> getListData() {
        return mCategories;
    }
}
