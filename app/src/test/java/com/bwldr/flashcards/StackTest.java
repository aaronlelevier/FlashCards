package com.bwldr.flashcards;

import com.bwldr.flashcards.db.Stack;
import com.bwldr.flashcards.util.Util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StackTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void stackToString() {
        String categoryId = Util.genId();
        String name = "foo";
        Stack stack = new Stack(categoryId, name);

        String ret = stack.toString();

        assertEquals("id: " + stack.id + " categoryId: " + categoryId + " name: " + name, ret);
    }
}