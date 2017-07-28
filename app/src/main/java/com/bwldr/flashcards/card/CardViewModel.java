package com.bwldr.flashcards.card;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.bwldr.flashcards.Inject;
import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.data.CardRepositoryContract;
import com.bwldr.flashcards.db.Card;
import com.bwldr.flashcards.score.Score;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CardViewModel extends AndroidViewModel {

    private static Score sScore;
    private static MutableLiveData<Score> sScoreMutable = new MutableLiveData<>();

    private CardRepositoryContract mCardRepo;

    public CardViewModel(Application application) {
        super(application);
    }

    public Score getScore() {
        return sScore;
    }

    public LiveData<Score> getLiveScore() {
        return sScoreMutable;
    }

    /**
     * Populates Cards in the DB and initializes the Score object
     * @param stackId of Cards to request from API
     */
    void getCards(String stackId) {
        // Inject Repo
        mCardRepo = Inject.getCardRepository(this.getApplication());

        // Inject ApiClient
        ApiClient client = Inject.getApiClient();
        Call<List<Card>> call = client.cardsList(stackId);
        call.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                InsertCardsTask task = new InsertCardsTask();
                task.execute(response.body());
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.d("onFailuire", "error");
            }
        });
    }

    private class InsertCardsTask extends AsyncTask<List<Card>, Void, Void> {

        @Override
        protected Void doInBackground(List<Card>... params) {
            if (params[0] != null) {
                List<Card> cards = params[0];
                for (Card c : cards) {
                    Log.d("doInBackground", c.toString());
                    mCardRepo.insert(c);
                }
                sScore = new Score(cards);
                sScoreMutable.postValue(sScore);
            }
            return null;
        }
    }
}
