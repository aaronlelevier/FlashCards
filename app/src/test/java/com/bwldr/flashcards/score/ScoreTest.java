package com.bwldr.flashcards.score;

import com.bwldr.flashcards.db.Card;
import com.bwldr.flashcards.util.Util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;


public class ScoreTest {

    private static final Card mCard1 = new Card(Util.genId(), "public", "anyone can access");
    private static final Card mCard2 = new Card(Util.genId(), "private", "only class can access");
    private List<Card> mCards;
    private Score mScore;

    @Before
    public void setUp() {
        mCards = new ArrayList<>(Arrays.asList(mCard1, mCard2));
        mScore = new Score(mCards);
    }

    @Test
    public void init() {
        assertEquals("Store Cards on Score",
                mCards, mScore.getCards());
        assertEquals("Correct should default to 0 on init",
                0, mScore.getCorrect());
        assertEquals("Total should be the same as 'mCards.size()'",
                2, mScore.getTotal());
        assertEquals("No Cards have retried yet",
                0, mScore.getMustRetries().size());
        assertTrue("Start is first round",
                mScore.isFirstRound());
        assertEquals("List index of the current Card",
                0, mScore.getCardIndex());
    }

    @Test
    public void equals() {
        String cardId = mCard1.id;
        // score
        Score score = new Score(mCards);
        score.incCorrect();
        score.addToRetries(cardId);
        // score2
        Score score2 = new Score(mCards);
        score2.incCorrect();
        score2.addToRetries(cardId);
        // pre-test
        assertEquals(score.getCorrect(), score2.getCorrect());
        assertEquals(score.getTotal(), score2.getTotal());
        assertTrue(score.getAllRetries().equals(score2.getAllRetries()));

        boolean ret = score.equals(score2);

        assertTrue(ret);
    }

    @Test
    public void getCard() {
        assertEquals(0, mScore.getCardIndex());
        assertEquals(mCard1, mScore.getCards().get(0));

        Card ret = mScore.getCard();

        assertEquals(mCard1, ret);
    }

    @Test
    public void answeredCardCorrect_true() {
        Score scoreSpy = Mockito.spy(new Score(mCards));

        scoreSpy.answeredCardCorrect(true);

        verify(scoreSpy).incCorrect();
    }

    @Test
    public void answeredCardCorrect_false() {
        Score scoreSpy = Mockito.spy(new Score(mCards));

        scoreSpy.answeredCardCorrect(false);

        verify(scoreSpy).markCardIncorrect();
    }

    @Test
    public void answeredCardCorrect_returnFalseIfThereAreRemainingCards() {
        // answered both cards, so no more cards left
        mScore.answeredCardCorrect(true);
        boolean ret = mScore.transitionToNextCardOrFinish();
        assertTrue(ret);

        mScore.answeredCardCorrect(true);
        boolean ret2 = mScore.transitionToNextCardOrFinish();
        assertFalse(ret2);
    }

    @Test
    public void answeredCardCorrect_returnTrueIfThereAreRemainingCards() {
        // answered one card incorrect, so has to retry
        mScore.answeredCardCorrect(false);
        boolean ret = mScore.transitionToNextCardOrFinish();
        assertTrue(ret);

        mScore.answeredCardCorrect(true);
        boolean ret2 = mScore.transitionToNextCardOrFinish();
        assertTrue(ret2);
    }

    @Test
    public void incCorrect() {
        assertEquals(0, mScore.getCorrect());
        assertTrue(mScore.isFirstRound());
        mScore.incCorrect();
        assertEquals("Should increment correct only if in 1st round",
                1, mScore.getCorrect());

        mScore.setInRetryMode();
        assertFalse(mScore.isFirstRound());
        mScore.incCorrect();
        assertEquals("No longer in 1st round, so don't increment",
                1, mScore.getCorrect());
    }

    @Test
    public void markCardIncorrect() {
        assertEquals(0, mScore.getAllRetries().size());
        assertEquals(0, mScore.getMustRetries().size());
        assertEquals(0, mScore.getCorrect());

        mScore.markCardIncorrect();

        assertEquals(1, mScore.getAllRetries().size());
        assertEquals(1, mScore.getMustRetries().size());
        assertEquals(0, mScore.getCorrect());
    }

    @Test
    public void maxCardIndex() {
        assertEquals(2, mScore.getTotal());
        assertEquals(1, mScore.maxCardIndex());
    }

    @Test
    public void transitionToNextCardOrFinish_noRetriesPath() {
        // 1st card
        assertTrue(mScore.maxCardIndex() > mScore.getCardIndex());

        boolean ret = mScore.transitionToNextCardOrFinish();

        assertTrue(ret);
        assertEquals(1, mScore.getCardIndex());

        // 2nd card
        assertEquals(mScore.getCardIndex(), mScore.maxCardIndex());

        boolean ret2 = mScore.transitionToNextCardOrFinish();

        assertFalse(ret2);
    }

    @Test
    public void transitionToNextCardOrFinish_mustRetryPath() {
        // 1st card - incorrect
        int initCardIndex = mScore.getCardIndex();
        assertEquals(0, initCardIndex);
        assertEquals(0, mScore.getCorrect());

        mScore.answeredCardCorrect(false);
        mScore.transitionToNextCardOrFinish();

        assertEquals(1, mScore.getCardIndex());
        assertEquals(0, mScore.getCorrect());
        // all retries
        assertEquals(1, mScore.getAllRetries().size());
        assertEquals(mCard1.id, mScore.getAllRetries().get(0));
        // must retries
        assertEquals(1, mScore.getMustRetries().size());
        assertTrue(mScore.getMustRetries().contains(mCard1.id));

        assertEquals("No more cards after this one",
                mScore.getCardIndex(), mScore.maxCardIndex());

        // 2nd card - correct
        mScore.answeredCardCorrect(true);
        mScore.transitionToNextCardOrFinish();

        assertEquals(0, mScore.getCardIndex());
        assertEquals(1, mScore.getCorrect());
        assertEquals(0, mScore.getAllRetries().size());
        assertEquals(0, mScore.getMustRetries().size());
    }

    @Test
    public void resetCardsForMustRetries() {
        assertEquals(2, mScore.getTotal());
        assertEquals(0, mScore.getCorrect());

        mScore.answeredCardCorrect(false);

        assertEquals(0, mScore.getCorrect());
        // all retries
        assertEquals(1, mScore.getAllRetries().size());
        assertEquals(mCard1.id, mScore.getAllRetries().get(0));
        // must retries
        assertEquals(1, mScore.getMustRetries().size());
        assertTrue(mScore.getMustRetries().contains(mCard1.id));

        mScore.resetCardsForMustRetries();

        assertEquals(1, mScore.getTotal());
        assertEquals(mCard1, mScore.getCards().get(0));
        assertEquals(0, mScore.getAllRetries().size());
        assertEquals(0, mScore.getMustRetries().size());
    }

    @Test
    public void addToRetries() {
        final String cardIdOne = Util.genId();
        final String cardIdOne2 = Util.genId();
        final String cardIdTwo = Util.genId();
        final String cardIdThree = Util.genId();
        final String cardIdFour = Util.genId();
        final String cardIdFive = Util.genId();

        mScore.addToRetries(cardIdOne);

        mScore.addToRetries(cardIdOne2);

        for (int i = 0; i < 2; i++) {
            mScore.addToRetries(cardIdTwo);
        }

        for (int i = 0; i < 3; i++) {
            mScore.addToRetries(cardIdThree);
        }

        for (int i = 0; i < 4; i++) {
            mScore.addToRetries(cardIdFour);
        }

        for (int i = 0; i < 5; i++) {
            mScore.addToRetries(cardIdFive);
        }

        mScore.calculateRetries();

        assertEquals(2, mScore.oneRetryCount());
        assertEquals(1, mScore.twoRetryCount());
        assertEquals(1, mScore.threeRetryCount());
        assertEquals(2, mScore.moreRetryCount());
    }

    @Test
    public void addToRetries_mustRetry() {
        final String cardIdOne = Util.genId();
        final String cardIdTwo = Util.genId();

        assertEquals(0, mScore.getMustRetries().size());

        mScore.addToRetries(cardIdOne);

        assertEquals(1, mScore.getMustRetries().size());
        assertTrue(mScore.getMustRetries().contains(cardIdOne));

        for (int i = 0; i < 2; i++) {
            mScore.addToRetries(cardIdTwo);
        }

        assertEquals(2, mScore.getMustRetries().size());
        assertTrue(mScore.getMustRetries().contains(cardIdOne));
        assertTrue(mScore.getMustRetries().contains(cardIdTwo));
    }

    @Test
    public void mustRetry() {
        final String cardIdOne = Util.genId();
        assertEquals(0, mScore.getMustRetries().size());
        mScore.addToRetries(cardIdOne);

        assertTrue(mScore.getMustRetries().size() > 0);

        assertTrue("Error: if their are entries in the mMustRetries Set, this should return true",
                mScore.mustRetry());
    }
}
