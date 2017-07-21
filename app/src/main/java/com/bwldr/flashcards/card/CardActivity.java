package com.bwldr.flashcards.card;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.db.Card;
import com.bwldr.flashcards.score.ScoreActivity;
import com.bwldr.flashcards.util.Constants;

import java.util.List;

/**
 * Wrapper Activity for the Card Fragments that represent
 * individual flash cards within a flash card stack.
 */

public class CardActivity extends LifecycleActivity implements ShowCardData {

    private CardViewModel mCardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);

        Intent intent = getIntent();
        String stackId = intent.getStringExtra("stackId");
        Log.d("stackId", stackId);

        mCardViewModel.getCards(stackId);

        if (savedInstanceState == null) {
            final int defaultStartIndex = 0;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, CardQuestionFragment.newInstance(defaultStartIndex))
                    .commit();
        }
    }

    // ShowCardData

    @Override
    public void showAnswer(int cardIndex) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CardAnswerFragment.newInstance(cardIndex))
                .commit();
    }

    @Override
    public void showNextQuestionOrScoreSummary(int cardIndex) {
        List<Card> cardList = mCardViewModel.getListData().getValue();

        if (cardList != null && cardIndex + 1 > cardList.size()) {
            if (mCardViewModel.getScore().mustRetry()) {
                // no more Cards, but must retry
                mCardViewModel.setUpMustRetryCards();
                final int firstCardIndex = 0;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, CardQuestionFragment.newInstance(firstCardIndex))
                        .commit();
            } else {
                // all Cards answered either first time, or eventually
                Intent intent = new Intent(this, ScoreActivity.class);
                intent.putExtra(Constants.SCORE, mCardViewModel.getScore());
                startActivity(intent);
            }
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CardQuestionFragment.newInstance(cardIndex))
                    .commit();
        }
    }
}
