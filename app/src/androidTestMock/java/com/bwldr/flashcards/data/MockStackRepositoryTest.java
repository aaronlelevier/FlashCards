package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.bwldr.flashcards.db.Stack;
import com.bwldr.flashcards.util.Util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Should return fixture data for testing, that would normally be Room AppDatabase calls
 */

@RunWith(AndroidJUnit4.class)
public class MockStackRepositoryTest {

    private Context mContext;
    private MockStackRepository mMockStackRepository;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        mMockStackRepository = new MockStackRepository(mContext);
    }

    @Test
    public void selectAll() {
        Stack stack = new Stack(Util.genId(), "Modifiers");
        mMockStackRepository.insert(stack);

        LiveData<List<Stack>> stacks = mMockStackRepository.selectById("1");

        assertTrue(stacks.getValue() != null);
        assertEquals(stacks.getValue().size(), 1);
        Stack stack2 = stacks.getValue().get(0);
        assertEquals(stack.id, stack2.id);
        assertEquals(stack.name, stack2.name);
    }
}
