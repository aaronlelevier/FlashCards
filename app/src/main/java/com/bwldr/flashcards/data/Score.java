package com.bwldr.flashcards.data;

import java.util.ArrayList;
import java.util.HashMap;
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

public class Score {

    private int mCorrect = 0;
    private final int mTotal;
    private List<String> allRetries = new ArrayList<>();
    private HashMap<String, Integer> mCountMap = new HashMap<>();

    public Score(int total) {
        mTotal = total;
    }

    // getters

    public int getTotal() {
        return mTotal;
    }

    public int correctCount() {
        return mCorrect;
    }

    public int onRetryCount() {
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
        mCorrect++;
    }

    /**
     * Call when a Flash Card has to be retried, and this method handles
     * putting cardId in the right bucket for the number of times that the
     * Card has been retried
     *
     * @param cardId String Card.id
     */
    public void addToRetries(String cardId) {
        allRetries.add(cardId);
    }

    public void calculateRetries() {
        // cardId, count
        String temp;
        Integer count;

        for (int i=0; i < allRetries.size(); i++) {
            temp = allRetries.get(i);
            count = mCountMap.get(temp);
            if (count == null) {
                mCountMap.put(temp, 1);
            } else {
                ++count;
                mCountMap.put(temp, count);
            }
        }
    }
}
