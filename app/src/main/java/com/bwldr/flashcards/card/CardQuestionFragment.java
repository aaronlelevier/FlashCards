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
import android.widget.TextView;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.db.Card;

import java.util.List;


public class CardQuestionFragment extends LifecycleFragment {

    private View mView;
    private CardViewModel mCardViewModel;

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
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        registerCardObserver();
    }

    private void registerCardObserver() {
        // NOTE: hard coded for now just to get the 1st card
        final int cardIndex = 0;
        mCardViewModel.getListData().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                TextView question = (TextView) mView.findViewById(R.id.card_question);

                if (cards != null && cards.size() != 0) {
                    question.setText(cards.get(cardIndex).question);
                }
            }
        });
    }
}
