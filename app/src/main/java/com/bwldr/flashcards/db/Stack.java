package com.bwldr.flashcards.db;

import android.arch.persistence.room.Entity;

/**
 * Represents a group (or stack) of Flash Cards
 */
@Entity
public class Stack extends AbstractRecord {
    public String title;

    public Stack(String title) {
        super();
        this.title = title;
    }
}
