package com.bwldr.flashcards.db;

import android.arch.persistence.room.Entity;

/**
 * Represents a group (or stack) of Flash Cards
 */
@Entity
public class Stack extends BaseRecord {
    /**
     * The {@link Category} that this Stack belongs to
     */
    public String categoryId;
    public String name;

    public Stack(String categoryId, String name) {
        super();
        this.categoryId = categoryId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "id: " + id + " categoryId: " + categoryId + " name: " + name;
    }
}
