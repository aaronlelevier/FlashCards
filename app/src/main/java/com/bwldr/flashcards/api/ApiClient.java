package com.bwldr.flashcards.api;

import com.bwldr.flashcards.db.Card;
import com.bwldr.flashcards.db.Category;
import com.bwldr.flashcards.db.Stack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Endpoints for bwldr rest api
 */
public interface ApiClient {

    @GET("/api/categories/")
    Call<List<Category>> categoriesList();

    @GET("/api/stacks/{id}/")
    Call<List<Stack>> stacksList(@Path("id") String categoryId);

    @GET("/api/cards/{id}/")
    Call<List<Card>> cardsList(@Path("id") String stackId);
}
