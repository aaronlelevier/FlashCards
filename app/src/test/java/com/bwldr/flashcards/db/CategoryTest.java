package com.bwldr.flashcards.db;

import com.bwldr.flashcards.db.Category;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CategoryTest {

    @Test
    public void categoryToString() {
        String name = "foo";
        Category category = new Category(name);
        assertEquals(name, category.toString());
    }
}