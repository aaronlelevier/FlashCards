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

        // Category
        List<Object> categories = data.get("Category");
        assertTrue(categories != null);
        assertEquals(2, categories.size());
        // 1
        assertTrue(categories.get(0) instanceof Category);
        Category category = (Category)categories.get(0);
        assertTrue(category.id != null);
        assertEquals("Java", category.name);
        // 2
        Category category2 = (Category)categories.get(1);
        assertTrue(category2.id != null);
        assertEquals("Python", category2.name);

        // Stack
        List<Object> stacks = data.get("Stack");
        assertEquals(3, stacks.size());
        // stack 1
        // 0
        Stack stack = (Stack)stacks.get(0);
        assertTrue(stack.categoryId.equals(category.id));
        assertEquals("Field Modifiers", stack.name);
        // 1
        Stack stack2 = (Stack)stacks.get(1);
        assertTrue(stack2.categoryId.equals(category.id));
        assertEquals("Control Flow Statements", stack2.name);
        // stack 2
        // 0
        Stack stackC2 = (Stack)stacks.get(2);
        assertTrue(stackC2.categoryId.equals(category2.id));
        assertEquals("Built-ins", stackC2.name);

        // Card
        List<Object> cards = data.get("Card");
        assertEquals(4, cards.size());
        // cards for stack 1
        // 0
        Card card = (Card)cards.get(0);
        assertTrue(card.stackId.equals(stack.id));
        assertEquals("public", card.question);
        assertEquals("can be accessed by anything", card.answer);
        // 1
        Card card2 = (Card)cards.get(1);
        assertTrue(card2.stackId.equals(stack.id));
        assertEquals("private", card2.question);
        assertEquals("can only be accessed internally", card2.answer);
        // 2
        Card card3 = (Card)cards.get(2);
        assertTrue(card3.stackId.equals(stack2.id));
        assertEquals("if-then", card3.question);
        assertEquals("single 'if' block", card3.answer);
        // cards for stack 2
        // 0
        Card cardC2 = (Card)cards.get(3);
        assertTrue(cardC2.stackId.equals(stackC2.id));
        assertEquals("any", cardC2.question);
        assertEquals("return True if any are True", cardC2.answer);
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
