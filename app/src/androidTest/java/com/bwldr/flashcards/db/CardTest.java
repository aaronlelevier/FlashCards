package com.bwldr.flashcards.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CardTest {

    private Context mContext;
    private AppDatabase mDb;

    private Stack mStack;
    private Stack mStack2;
    private Card mCard;
    private Card mCard2;
    private Card mCard3;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(mContext, AppDatabase.class).build();

        mStack = new Stack("Field Modifiers");
        mStack2 = new Stack("Control Flow Statements");
        mCard = new Card(mStack.id, "public", "can be accessed by anything");
        mCard2 = new Card(mStack.id, "private", "can only be accessed internally");
        mCard3 = new Card(mStack2.id, "if-then", "single 'if' block");
        mDb.cardDao().insert(mCard);
        mDb.cardDao().insert(mCard2);
        mDb.cardDao().insert(mCard3);
    }

    @Test
    public void selectAll() {
        List<Card> cards = mDb.cardDao().selectAll();

        assertEquals(3, cards.size());
        Card c = cards.get(0);
        assertEquals(36, c.id.length());
        assertEquals(36, c.stackId.length());
        assertEquals("public", c.title);
        assertEquals("can be accessed by anything", c.definition);
    }

    @Test
    public void selectByStackId() {
        List<Card> cards = mDb.cardDao().selectByStackId(mStack.id);
        assertEquals(2, cards.size());
        assertEquals(mCard.id, cards.get(0).id);
        assertEquals(mCard2.id, cards.get(1).id);

        List<Card> cards2 = mDb.cardDao().selectByStackId(mStack2.id);
        assertEquals(1, cards2.size());
        assertEquals(mCard3.id, cards2.get(0).id);
    }
}
