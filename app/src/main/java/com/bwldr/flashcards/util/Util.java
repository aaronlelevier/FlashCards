package com.bwldr.flashcards.util;

import java.util.UUID;

public class Util {

    public static String genId() {
        return UUID.randomUUID().toString();
    }
}
