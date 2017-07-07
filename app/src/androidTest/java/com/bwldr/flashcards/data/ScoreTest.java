package com.bwldr.flashcards.data;

import android.os.Parcel;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Counterpart test to Score unit test, but for Android Parcelable
 */

public class ScoreTest {

    @Test
    public void parcelable() {
        int total = 10;
        Score score = new Score(total);

        Parcel parcel = Parcel.obtain();
        score.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Score createdFromParcel = Score.CREATOR.createFromParcel(parcel);
        assertEquals(score, createdFromParcel);
    }
}
