package com.bwldr.flashcards.api;

import com.bwldr.flashcards.db.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Endpoints for bwldr rest api
 */
public interface ApiClient {

    @GET("/api/categories/")
    Call<List<Category>> categoriesList();
}
