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
    public String name;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
