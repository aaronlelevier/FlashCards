package com.bwldr.flashcards.util;

import com.bwldr.flashcards.db.Category;

import java.util.UUID;

public class Util {

    public static String genId() {
        return UUID.randomUUID().toString();
    }

    public static Category create_category(String name) {
        return new Category(genId(), name);
    }
}
