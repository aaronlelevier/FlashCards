package com.bwldr.flashcards.card;

/**
 * Created by aaron on 7/4/17.
 */

public interface ShowCardData {

    void showAnswer(int cardIndex);

    void showNextQuestionOrScoreSummary(int cardIndex);
}
