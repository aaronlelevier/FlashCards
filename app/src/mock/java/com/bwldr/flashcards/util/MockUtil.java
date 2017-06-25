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
        data.add(Util.create_category("Java"));
        return data;
    }

    public static HashMap<String, List<Object>> createFixtureData() {
        if (mFixtureData == null) {
            HashMap<String, List<Object>> data = new HashMap<>();

            List<Object> categories = new ArrayList<>();
            Category category = Util.create_category("Java");
            categories.add(category);
            data.put("Category", categories);

            List<Object> stacks = new ArrayList<>();
            Stack stack = new Stack(category.id, "Field Modifiers");
            stacks.add(stack);
            Stack stack2 = new Stack(category.id, "Control Flow Statements");
            stacks.add(stack2);
            data.put("Stack", stacks);

            List<Object> cards = new ArrayList<>();
            cards.add(new Card(stack.id, "public", "can be accessed by anything"));
            cards.add(new Card(stack.id, "private", "can only be accessed internally"));
            cards.add(new Card(stack2.id, "if-then", "single 'if' block"));
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
