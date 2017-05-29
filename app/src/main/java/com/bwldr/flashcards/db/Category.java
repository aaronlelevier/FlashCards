package com.bwldr.flashcards.db;

import android.arch.persistence.room.Entity;

/**
 * Category object that contains Stacks of Flash Cards
 */
@Entity
public class Category extends BaseRecord {
    public String title;

    public Category(String title) {
        super();
        this.title = title;
    }
}
