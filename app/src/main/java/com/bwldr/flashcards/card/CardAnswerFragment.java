package com.bwldr.flashcards.card;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.db.Card;

import java.util.List;


public class CardAnswerFragment extends LifecycleFragment {

    private static int mCardIndex;

    private CardViewModel mCardViewModel;
    private View mView;
    private TextView mAnswer;

    public static CardAnswerFragment newInstance(int cardIndex) {
        mCardIndex = cardIndex;
        return new CardAnswerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCardViewModel = ViewModelProviders.of(getActivity()).get(CardViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_card_answer, container, false);
        setOnClickListener();
        mAnswer = (TextView) mView.findViewById(R.id.card_answer);
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
                    mAnswer.setText(cards.get(mCardIndex).answer);
            }
        });
    }

    private void setOnClickListener() {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++mCardIndex;
                ((ShowCardData)getActivity()).showNextQuestionOrScoreSummary(mCardIndex);
            }
        });
    }
}
