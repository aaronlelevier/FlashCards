package com.bwldr.flashcards.score;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.data.Score;
import com.bwldr.flashcards.util.Constants;


public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Score score = getIntent().getParcelableExtra(Constants.SCORE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ScoreFragment.newInstance(score))
                    .commit();
        }
    }
}
