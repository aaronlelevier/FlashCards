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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CardViewModel extends AndroidViewModel {

    private CardRepositoryContract mCardRepo;
    private LiveData<List<Card>> mCards;
    private final MutableLiveData<Card> mCard = new MutableLiveData<>();

    public CardViewModel(Application application) {
        super(application);
    }

    public void getCards(String stackId) {
        // Inject Repo
        mCardRepo = Inject.getCardRepository(this.getApplication());
        mCards = mCardRepo.selectById(stackId);

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

    LiveData<List<Card>> getListData() {
        return mCards;
    }

    private LiveData<Card> getCard() {
        return mCard;
    }

    LiveData<Card> getListItem(int position) {
        if (mCards.getValue() != null) {
            mCard.setValue(mCards.getValue().get(position));
            return getCard();
        }
        return null;
    }

    private class InsertCardsTask extends AsyncTask<List<Card>, Void, Void> {

        @Override
        protected Void doInBackground(List<Card>... params) {
            if (params[0] != null) {
                List<Card> data = params[0];
                for (Card card : data) {
                    Log.d("doInBackground", card.toString());
                    mCardRepo.insert(card);
                }
            }
            return null;
        }
    }
}
