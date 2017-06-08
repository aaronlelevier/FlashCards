package com.bwldr.flashcards.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.bwldr.flashcards.util.Util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class StackTest {

    private Context mContext;
    private AppDatabase mDb;

    private Category mCategory;
    private Category mCategory2;
    private Stack mStack;
    private Stack mStack2;
    private Stack mStack3;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(mContext, AppDatabase.class).build();

        mCategory = Util.create_category("Java");
        mCategory2 = Util.create_category("Python");
        mStack = new Stack(mCategory.id, "Field Modifiers");
        mStack2 = new Stack(mCategory.id, "Control Flow Modifiers");
        mStack3 = new Stack(mCategory2.id, "Decorators");
        mDb.stackDao().insert(mStack);
        mDb.stackDao().insert(mStack2);
        mDb.stackDao().insert(mStack3);
    }

    @Test
    public void selectAll() {
        List<Stack> stacks = mDb.stackDao().selectAll();

        assertEquals(3, stacks.size());
        Stack s = stacks.get(0);
        assertEquals(36, s.id.length());
        assertEquals("Field Modifiers", s.title);
    }

    @Test
    public void selectByCategoryId() {
        List<Stack> stacks = mDb.stackDao().selectByCategoryId(mCategory.id);
        assertEquals(2, stacks.size());
        assertEquals(mStack.id, stacks.get(0).id);
        assertEquals(mStack2.id, stacks.get(1).id);

        List<Stack> stacks2 = mDb.stackDao().selectByCategoryId(mCategory2.id);
        assertEquals(1, stacks2.size());
        assertEquals(mStack3.id, stacks2.get(0).id);
    }
}
