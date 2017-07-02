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
    public void stackToString() {
        String name = "foo";
        Stack stack = new Stack(Util.genId(), name);
        assertEquals(name, stack.toString());
    }
}