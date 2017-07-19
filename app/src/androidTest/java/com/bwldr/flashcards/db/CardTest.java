package com.bwldr.flashcards.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.bwldr.flashcards.util.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CardTest {

    private Context mContext;
    private AppDatabase mDb;

    private Category mCategory;
    private Stack mStack;
    private Stack mStack2;
    private Card mCard;
    private Card mCard2;
    private Card mCard3;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(mContext, AppDatabase.class).build();

        mCategory = new Category("Java");
        mStack = new Stack(mCategory.id, "Field Modifiers");
        mStack2 = new Stack(mCategory.id, "Control Flow Statements");
        mCard = new Card(mStack.id, "public", "can be accessed by anything");
        mCard2 = new Card(mStack.id, "private", "can only be accessed internally");
        mCard3 = new Card(mStack2.id, "if-then", "single 'if' block");
        mDb.cardDao().insert(mCard);
        mDb.cardDao().insert(mCard2);
        mDb.cardDao().insert(mCard3);
    }

    @Test
    public void selectByStackId() {
        // cards 1
        LiveData<List<Card>> cardsLiveData = mDb.cardDao().selectByStackId(mStack.id);
        List<Card> cards = null;
        try {
            cards = TestUtil.getValue(cardsLiveData);
        } catch (InterruptedException e) {
            // LiveData ret failed
        }
        assertTrue(cards != null);
        assertEquals(2, cards.size());
        assertEquals(mCard.id, cards.get(0).id);
        assertEquals(mCard2.id, cards.get(1).id);

        // cards 2
        LiveData<List<Card>> cards2LiveData = mDb.cardDao().selectByStackId(mStack2.id);
        List<Card> cards2 = null;
        try {
            cards2 = TestUtil.getValue(cards2LiveData);
        } catch (InterruptedException e) {
            // LiveData ret failed
        }
        assertTrue(cards2 != null);
        assertEquals(1, cards2.size());
        assertEquals(mCard3.id, cards2.get(0).id);
    }

    @Test
    public void selectByCardIdIn() {
        // cards 1
        LiveData<List<Card>> cardsLiveData = mDb.cardDao().selectByCardIdIn(mCard.id, mCard2.id);
        List<Card> cards = null;
        try {
            cards = TestUtil.getValue(cardsLiveData);
        } catch (InterruptedException e) {
            // LiveData ret failed
        }
        assertTrue(cards != null);
        assertEquals(2, cards.size());
        assertEquals(mCard2.id, cards.get(0).id);
        assertEquals(mCard.id, cards.get(1).id);
    }
}
