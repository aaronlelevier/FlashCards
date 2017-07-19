package com.bwldr.flashcards.card;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.db.Card;
import com.bwldr.flashcards.score.ScoreActivity;
import com.bwldr.flashcards.util.Constants;

import java.util.List;


public class CardQuestionFragment extends LifecycleFragment {

    private static int mCardIndex;

    private CardViewModel mCardViewModel;
    private Card mCard;
    private View mView;
    private TextView mQuestion;

    public static Fragment newInstance(int cardIndex) {
        mCardIndex = cardIndex;
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
        registerCardObserver();
        super.onViewCreated(view, savedInstanceState);
    }

    private void registerCardObserver() {
        mCardViewModel.getListData().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                if (mCardViewModel.getCard(mCardIndex) != null) {
                    mCard = mCardViewModel.getCard(mCardIndex);
                    mQuestion.setText(mCard.question);
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
                // TODO: adding to retries, but not logic yet for putting Card back in stack to
                if (mCard != null) {
                    mCardViewModel.addToRetries(mCard.id);

                    ((ShowCardData) getActivity()).showAnswer(mCardIndex);
                }
            }
        });

        Button btnNext = (Button) mView.findViewById(R.id.button_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mark this card as correct
                mCardViewModel.incCorrect();

                ++mCardIndex;
                try {
                    Card card = mCardViewModel.getCard(mCardIndex);
                    mQuestion.setText(card.question);
                } catch (IndexOutOfBoundsException e) {
                    Intent intent = new Intent(getActivity(), ScoreActivity.class);
                    intent.putExtra(Constants.SCORE, mCardViewModel.getScore());
                    startActivity(intent);
                }
            }
        });
    }
}
