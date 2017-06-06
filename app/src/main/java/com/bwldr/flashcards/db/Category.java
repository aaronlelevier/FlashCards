package com.bwldr.flashcards.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Category object that contains Stacks of Flash Cards
 */
@Entity
public class Category {
    @PrimaryKey
    public String id;
    public String title;

    public Category(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
