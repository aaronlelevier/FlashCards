package com.bwldr.flashcards.util;

import android.support.test.runner.AndroidJUnit4;

import com.bwldr.flashcards.db.Card;
import com.bwldr.flashcards.db.Category;
import com.bwldr.flashcards.db.Stack;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MockUtilTest {

    @Test
    public void createCategories() {
        List<Category> data = MockUtil.createCategories();

        assertTrue(data != null);
        assertEquals(1, data.size());
        Category category = data.get(0);
        assertEquals("Java", category.name);
        assertTrue(category.id != null);
    }

    @Test
    public void createFixtureData() {
        HashMap<String, List<Object>> data = MockUtil.createFixtureData();

        // categories
        List<Object> categories = data.get("Category");
        assertTrue(categories != null);
        assertEquals(1, categories.size());
        assertTrue(categories.get(0) instanceof Category);
        Category category = (Category)categories.get(0);
        assertTrue(category.id != null);
        assertEquals("Java", category.name);

        // stacks
        List<Object> stacks = data.get("Stack");
        assertEquals(2, stacks.size());
        // 0
        Stack stack = (Stack)stacks.get(0);
        assertTrue(stack.categoryId.equals(category.id));
        assertEquals("Field Modifiers", stack.name);
        // 1
        Stack stack2 = (Stack)stacks.get(1);
        assertTrue(stack2.categoryId.equals(category.id));
        assertEquals("Control Flow Statements", stack2.name);

        // cards
        List<Object> cards = data.get("Card");
        assertEquals(3, cards.size());
        // 0
        Card card = (Card)cards.get(0);
        assertTrue(card.stackId.equals(stack.id));
        assertEquals("public", card.title);
        assertEquals("can be accessed by anything", card.definition);
        // 1
        Card card2 = (Card)cards.get(1);
        assertTrue(card2.stackId.equals(stack.id));
        assertEquals("private", card2.title);
        assertEquals("can only be accessed internally", card2.definition);
        // 2
        Card card3 = (Card)cards.get(2);
        assertTrue(card3.stackId.equals(stack2.id));
        assertEquals("if-then", card3.title);
        assertEquals("single 'if' block", card3.definition);
    }

    @Test
    public void createFixtureData_lazilyInitialize() {
        assertTrue(MockUtil.getFixtureData() == null);

        MockUtil.createFixtureData();

        HashMap<String, List<Object>> ret = MockUtil.getFixtureData();
        assertTrue(ret != null);

        HashMap<String, List<Object>> ret2 = MockUtil.getFixtureData();
        assertTrue(ret.equals(ret2));
    }

    @Test
    public void createFixtureData_getSubtypeLists() {
        MockUtil.createFixtureData();

        assertTrue(MockUtil.getCategories() != null);
        assertTrue(MockUtil.getStacks() != null);
        assertTrue(MockUtil.getCards() != null);
    }
}
