package com.bwldr.flashcards.util;

import com.bwldr.flashcards.db.Card;
import com.bwldr.flashcards.db.Category;
import com.bwldr.flashcards.db.Stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockUtil extends Util {

    private static HashMap<String, List<Object>> mFixtureData;

    public static HashMap<String, List<Object>> getFixtureData() {
        return mFixtureData;
    }

    public static List<Category> createCategories() {
        List<Category> data = new ArrayList<>();
        data.add(new Category("Java"));
        return data;
    }

    public static HashMap<String, List<Object>> createFixtureData() {
        if (mFixtureData == null) {
            HashMap<String, List<Object>> data = new HashMap<>();

            List<Object> categories = new ArrayList<>();
            Category category = new Category("Java");
            Category category2 = new Category("Python");
            categories.add(category);
            categories.add(category2);
            data.put("Category", categories);

            List<Object> stacks = new ArrayList<>();
            Stack stack = new Stack(category.id, "Field Modifiers");
            Stack stack2 = new Stack(category.id, "Control Flow Statements");
            Stack stackC2 = new Stack(category2.id, "Built-ins");
            stacks.add(stack);
            stacks.add(stack2);
            stacks.add(stackC2);
            data.put("Stack", stacks);

            List<Object> cards = new ArrayList<>();
            cards.add(new Card(stack.id, "public", "can be accessed by anything"));
            cards.add(new Card(stack.id, "private", "can only be accessed internally"));
            cards.add(new Card(stack2.id, "if-then", "single 'if' block"));
            cards.add(new Card(stackC2.id, "any", "return True if any are True"));
            data.put("Card", cards);

            mFixtureData = data;
        }
        return mFixtureData;
    }

    public static List<Category> getCategories() {
        createFixtureData();
        List<? extends Object> categories = mFixtureData.get("Category");
        return (List<Category>)categories;
    }

    public static List<Stack> getStacks() {
        createFixtureData();
        List<? extends Object> stacks = mFixtureData.get("Stack");
        return (List<Stack>)stacks;
    }

    public static List<Card> getCards() {
        createFixtureData();
        List<? extends Object> cards = mFixtureData.get("Category");
        return (List<Card>)cards;
    }
}
