package com.bwldr.flashcards.score;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.bwldr.flashcards.db.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Represents the Score Summary to be shown after completing a
 * Stack of flash cards.
 *
 * Example:
 * 5/10 Correct
 *
 * Retries
 * - 1x: 3
 * - 2x: 1
 * - 3x: 0
 * - more: 0
 */

public class Score implements Parcelable {

    private List<Card> mCards;
    private int mCardIndex = 0;
    private int mCorrect = 0;
    private int mCurrentTotal;
    private final int mTotal;
    private boolean mIsFirstRound = true;
    private List<String> mAllRetries = new ArrayList<>();
    private HashSet<String> mMustRetries = new HashSet<>();
    private HashMap<String, Integer> mCountMap = new HashMap<>();

    /**
     * Constructor used for Parcelable form CardActivity to ScoreActivity
     * @param total
     */
    public Score(int total) {
        mCurrentTotal = total;
        mTotal = total;
    }

    public Score(@NonNull List<Card> cards) {
        mCards = cards;
        mCurrentTotal = cards.size();
        mTotal = cards.size();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Score))
            return false;
        if (((Score) o).getCurrentTotal() != mCurrentTotal)
            return false;
        if (((Score) o).getTotal() != mTotal)
            return false;
        if (((Score) o).getCorrect() != mCorrect)
            return false;
        return (((Score) o).getAllRetries().equals(mAllRetries));
    }

    /**
     * This method increments the current score if necessary, and
     * tells whether there are more cards left in the stack.
     * @param correct
     * @return true if there are more Cards in the stack, else
     * false, and we should transition to the Score Summary View
     */
    public void answeredCardCorrect(boolean correct) {
        if (correct) {
            incCorrect();
        } else {
            markCardIncorrect();
        }
    }

    public void incCorrect() {
        if (mIsFirstRound)
            mCorrect++;
    }

    public void markCardIncorrect() {
        Card card = mCards.get(mCardIndex);
        mAllRetries.add(card.id);
        mMustRetries.add(card.id);
    }

    public ScoreTransition transitionToNextCardOrFinish() {
        if (mCardIndex < maxCardIndex()) {
            mCardIndex++;
            return ScoreTransition.NEXT;
        } else if (mustRetry()) {
            resetCardsForMustRetries();
            return ScoreTransition.RETRY;
        }
        calculateRetryCounts();
        return ScoreTransition.FINISH;
    }

    public void resetCardsForMustRetries() {
        setInRetryMode();
        mCardIndex = 0;

        List<Card> cardsToRetry = new ArrayList<>();
        for (Card c: mCards) {
            if (mMustRetries.contains(c.id)) {
                cardsToRetry.add(c);
            }
        }
        mCards = cardsToRetry;

        mMustRetries.clear();
    }

    // getters

    public List<Card> getCards() {
        return mCards;
    }

    public Card getCard() {
        return mCards.get(mCardIndex);
    }

    public int getCardIndex() {
        return mCardIndex;
    }

    public int maxCardIndex() {
        return getCurrentTotal()-1;
    }

    /**
     * @return int total number of cards
     */
    public int getCurrentTotal() {
        // mCards will be null when calling the `Score(int)` constructor for passing
        // a Parcelable `Score` from the CardActivity to the ScoreActivity at
        // which point we only care about the `Score.mCurrentTotal`, not the `Score.mCards`
        if (mCards != null) {
            mCurrentTotal = mCards.size();
        }
        return mCurrentTotal;
    }

    public int getTotal() {
        return mTotal;
    }

    /**
     * @return int number of cards correct
     */
    public int getCorrect() {
        return mCorrect;
    }

    public boolean isFirstRound() {
        return mIsFirstRound;
    }

    public HashSet<String> getMustRetries() {
        return mMustRetries;
    }

    public List<String> getAllRetries() {
        return mAllRetries;
    }

    public int oneRetryCount() {
        return getCountForRetryBucket(1);
    }

    public int twoRetryCount() {
        return getCountForRetryBucket(2);
    }

    public int threeRetryCount() {
        return getCountForRetryBucket(3);
    }

    public int moreRetryCount() {
        int count = 0;
        for (int value: mCountMap.values()) {
            if (value >= 4) {
                count++;
            }
        }
        return count;
    }

    private int getCountForRetryBucket(int bucketValue) {
        int count = 0;
        for (int value: mCountMap.values()) {
            if (value == bucketValue) {
                count++;
            }
        }
        return count;
    }

    // setters

    public void setInRetryMode() {
        mIsFirstRound = false;
    }

    /**
     * Call when a Flash Card has to be retried, and this method handles
     * putting cardId in the right bucket for the number of times that the
     * Card has been retried
     *
     * @param cardId String Card.id
     */
    public void addToRetries(String cardId) {
        mAllRetries.add(cardId);
        mMustRetries.add(cardId);
    }

    /**
     * Call before displaying the Score Summary because tallies
     * the retry counts per flash card
     */
    public void calculateRetryCounts() {
        // cardId, count
        String temp;
        Integer count;

        for (int i = 0; i < mAllRetries.size(); i++) {
            temp = mAllRetries.get(i);
            count = mCountMap.get(temp);
            if (count == null) {
                mCountMap.put(temp, 1);
            } else {
                ++count;
                mCountMap.put(temp, count);
            }
        }
    }

    // Parcelable

    private Score(Parcel in) {
        mCurrentTotal = in.readInt();
        mTotal = in.readInt();
        mCorrect = in.readInt();
        mAllRetries = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mCurrentTotal);
        out.writeInt(mTotal);
        out.writeInt(mCorrect);
        out.writeStringList(mAllRetries);
    }

    public static final Parcelable.Creator<Score> CREATOR
            = new Parcelable.Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[0];
        }
    };

    public boolean mustRetry() {
        return mMustRetries.size() > 0;
    }
}
