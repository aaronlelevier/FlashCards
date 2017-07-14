package com.bwldr.flashcards;

import com.bwldr.flashcards.data.Score;
import com.bwldr.flashcards.util.Util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class ScoreTest {

    private int mTotal = 10;
    private Score mScore = new Score(mTotal);

    @Test
    public void init() {
        assertEquals(mScore.getTotal(), mTotal);
        assertEquals("Default initial correct should be 0",
                mScore.getCorrect(), 0);
    }

    @Test
    public void equals() throws Exception {
        int total = 5;
        String cardId = Util.genId();
        // score
        Score score = new Score(total);
        score.incCorrect();
        score.addToRetries(cardId);
        // score2
        Score score2 = new Score(total);
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
    public void incCorrect() {
        assertEquals(0, mScore.getCorrect());
        mScore.incCorrect();
        assertEquals(1, mScore.getCorrect());
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
}
