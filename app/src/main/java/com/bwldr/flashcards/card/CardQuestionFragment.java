package com.bwldr.flashcards.card;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.score.Score;


public class CardQuestionFragment extends LifecycleFragment {

    private static Score sScore;

    private CardViewModel mCardViewModel;
    private View mView;
    private TextView mQuestion;

    public static Fragment newInstance() {
        return new CardQuestionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCardViewModel = ViewModelProviders.of(getActivity()).get(CardViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_card_question, container, false);
        setupButtons();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        registerScoreObserver();
        super.onViewCreated(view, savedInstanceState);
    }

    private void registerScoreObserver() {
        mCardViewModel.getLiveScore().observe(this, new Observer<Score>() {
            @Override
            public void onChanged(@Nullable Score score) {
                if (score != null) {
                    sScore = score;
                    mQuestion.setText(sScore.getCard().question);
                }
            }
        });
    }

    private void setupButtons() {
        mQuestion = (TextView) mView.findViewById(R.id.card_question);

        Button btnCheck = (Button) mView.findViewById(R.id.button_check);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sScore != null) {
                    sScore.answeredCardCorrect(false);
                    ((ShowCardData) getActivity()).showAnswer();
                }
            }
        });

        Button btnNext = (Button) mView.findViewById(R.id.button_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sScore != null) {
                    sScore.answeredCardCorrect(true);
                    ((ShowCardData) getActivity()).showNextQuestionOrScoreSummary();
                }
            }
        });
    }
}
