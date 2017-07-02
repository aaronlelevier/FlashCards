package com.bwldr.flashcards.db;

import android.arch.persistence.room.Entity;

/**
 * Category object that contains Stacks of Flash Cards
 */
@Entity
public class Category extends BaseRecord {
    public String name;

    public Category(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
