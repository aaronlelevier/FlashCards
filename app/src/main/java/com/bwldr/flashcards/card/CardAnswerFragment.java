package com.bwldr.flashcards.card;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwldr.flashcards.R;


public class CardAnswerFragment extends LifecycleFragment {

    private CardViewModel mCardViewModel;
    private View mView;
    private TextView mAnswer;

    public static CardAnswerFragment newInstance() {
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
        mAnswer.setText(mCardViewModel.getScore().getCard().answer);
        return mView;
    }

    private void setOnClickListener() {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShowCardData)getActivity()).showNextQuestionOrScoreSummary();
            }
        });
    }
}
