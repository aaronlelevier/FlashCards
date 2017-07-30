package com.bwldr.flashcards.card;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.score.ScoreActivity;
import com.bwldr.flashcards.score.ScoreTransition;
import com.bwldr.flashcards.util.Constants;
import com.bwldr.flashcards.util.Util;

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
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, CardQuestionFragment.newInstance())
                    .commit();
        }
    }

    // ShowCardData

    @Override
    public void showAnswer() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CardAnswerFragment.newInstance())
                .commit();
    }

    @Override
    public void showNextQuestionOrScoreSummary() {
        ScoreTransition transition = mCardViewModel.getScore().transitionToNextCardOrFinish();
        switch (transition) {
            case NEXT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, CardQuestionFragment.newInstance())
                        .commit();
                break;
            case RETRY:
                Util.showToast(this, "retry missed cards");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, CardQuestionFragment.newInstance())
                        .commit();
                break;
            case FINISH:
                Intent intent = new Intent(this, ScoreActivity.class);
                intent.putExtra(Constants.SCORE, mCardViewModel.getScore());
                startActivity(intent);
                break;
        }
    }
}
