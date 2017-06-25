package com.bwldr.flashcards.util;

import com.bwldr.flashcards.db.Card;
import com.bwldr.flashcards.db.Category;
import com.bwldr.flashcards.db.Stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockUtil extends Util {

    public static List<Object> createDbData() {
        List<Object> data = new ArrayList<>();

        Category category = Util.create_category("Java");
        Stack stack = new Stack(category.id, "Field Modifiers");
        Stack stack2 = new Stack(category.id, "Control Flow Statements");
        Card card = new Card(stack.id, "public", "can be accessed by anything");
        Card card2 = new Card(stack.id, "private", "can only be accessed internally");
        Card card3 = new Card(stack2.id, "if-then", "single 'if' block");

        data.add(category);
        data.add(stack);
        data.add(stack2);
        data.add(card);
        data.add(card2);
        data.add(card3);

        return data;
    }

    public static List<Category> createCategories() {
        List<Category> data = new ArrayList<>();
        data.add(Util.create_category("Java"));
        return data;
    }

    public static HashMap<String, List<Object>> createFixtureData() {
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

        return data;
    }
}
