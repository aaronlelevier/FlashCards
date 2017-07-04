package com.bwldr.flashcards.card;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LiveData;
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
import com.bwldr.flashcards.db.Card;

import java.util.List;


public class CardQuestionFragment extends LifecycleFragment {

    private static int mCardIndex = 0;

    private CardViewModel mCardViewModel;
    private LiveData<List<Card>> mCards;
    private View mView;
    private TextView mQuestion;

    public static Fragment newInstance() {
        return new CardQuestionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCardViewModel = ViewModelProviders.of(getActivity()).get(CardViewModel.class);
        mCards = mCardViewModel.getListData();
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
        super.onViewCreated(view, savedInstanceState);
        registerCardObserver();
    }

    private void registerCardObserver() {
        mCardViewModel.getListData().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                if (cards != null)
                    mQuestion.setText(cards.get(mCardIndex).question);
            }
        });
    }

    private void setupButtons() {
        mQuestion = (TextView) mView.findViewById(R.id.card_question);

        Button btnCheck = (Button) mView.findViewById(R.id.button_check);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnNext = (Button) mView.findViewById(R.id.button_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++mCardIndex;
                if (mCards.getValue() != null)
                    mQuestion.setText(mCards.getValue().get(mCardIndex).question);
            }
        });
    }
}
