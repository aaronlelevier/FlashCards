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
public class StackTest {

    private Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void populateStacks() {
        AppDatabase db = Room.inMemoryDatabaseBuilder(mContext, AppDatabase.class).build();
        Stack stack = new Stack("Field Modifiers");
        Stack stack2 = new Stack("Control Flow Modifiers");
        db.stackDao().insert(stack);
        db.stackDao().insert(stack2);

        List<Stack> stacks = db.stackDao().selectAll();

        assertEquals(2, stacks.size());
        Stack s = stacks.get(0);
        assertEquals(36, s.id.length());
        assertEquals("Field Modifiers", s.title);
    }
}
