package com.bwldr.flashcards.card;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bwldr.flashcards.R;

/**
 * Wrapper Activity for the Card Fragments that represent
 * individual flash cards within a flash card stack.
 */

public class CardActivity extends LifecycleActivity {

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
}
