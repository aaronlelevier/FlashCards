package com.bwldr.flashcards.data;

import android.os.Parcel;
import android.os.Parcelable;

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

    private int mCorrect = 0;
    private final int mTotal;
    private List<String> mAllRetries = new ArrayList<>();
    private HashSet<String> mMustRetry = new HashSet<>();
    private HashMap<String, Integer> mCountMap = new HashMap<>();
    private boolean mRetryMode = false;

    public Score(int total) {
        mTotal = total;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Score))
            return false;
        if (((Score) o).getTotal() != mTotal)
            return false;
        if (((Score) o).getCorrect() != mCorrect)
            return false;
        return (((Score) o).getAllRetries().equals(mAllRetries));
    }

    // getters

    /**
     * @return int total number of cards
     */
    public int getTotal() {
        return mTotal;
    }

    /**
     * @return int number of cards correct
     */
    public int getCorrect() {
        return mCorrect;
    }

    public boolean inRetryMode() {
        return mRetryMode;
    }

    public HashSet<String> getMustRetry() {
        return mMustRetry;
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

    public void incCorrect() {
        if (!mRetryMode)
            mCorrect++;
    }

    public void setInRetryMode() {
        mRetryMode = true;
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
        mMustRetry.add(cardId);
    }

    /**
     * Call before displaying the Score Summary because tallies
     * the retry counts per flash card
     */
    public void calculateRetries() {
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
}
