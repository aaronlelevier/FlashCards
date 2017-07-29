package com.bwldr.flashcards.score;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwldr.flashcards.R;

import java.util.Locale;


public class ScoreFragment extends Fragment {

    private static Score sScore;

    public static ScoreFragment newInstance(Score score) {
        setScore(score);
        return new ScoreFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        ((TextView) view.findViewById(R.id.tv_total))
                .setText(String.format(Locale.US, "Correct: %d / %d", sScore.getCorrect(), sScore.getTotal()));
        ((TextView) view.findViewById(R.id.tv_retries_1x))
                .setText(String.format(Locale.US, "Retries 1x: %d", sScore.oneRetryCount()));
        ((TextView) view.findViewById(R.id.tv_retries_2x))
                .setText(String.format(Locale.US, "Retries 2x: %d", sScore.twoRetryCount()));
        ((TextView) view.findViewById(R.id.tv_retries_3x))
                .setText(String.format(Locale.US, "Retries 3x: %d", sScore.threeRetryCount()));
        ((TextView) view.findViewById(R.id.tv_retries_more))
                .setText(String.format(Locale.US, "Retries more: %d", sScore.moreRetryCount()));

        return view;
    }

    private static void setScore(Score score) {
        score.calculateRetryCounts();
        sScore = score;
    }
}
