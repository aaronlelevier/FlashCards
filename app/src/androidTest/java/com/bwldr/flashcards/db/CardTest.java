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

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void populateCards() {
        AppDatabase db = Room.inMemoryDatabaseBuilder(mContext, AppDatabase.class).build();
        Card card = new Card("public", "can be accessed by anything");
        Card card2 = new Card("private", "can only be accessed internally");
        db.cardDao().insertCard(card);
        db.cardDao().insertCard(card2);

        List<Card> cards = db.cardDao().selectAll();

        assertEquals(2, cards.size());
        Card c = cards.get(0);
        assertEquals(36, c.id.length());
        assertEquals("public", c.title);
        assertEquals("can be accessed by anything", card.definition);
    }
}
