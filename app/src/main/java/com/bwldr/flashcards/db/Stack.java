package com.bwldr.flashcards.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Represents a group (or stack) of Flash Cards
 */
@Entity
public class Stack extends BaseRecord {
    /**
     * The {@link Category} that this Stack belongs to
     */
    @ColumnInfo(name = "category_id")
    public String categoryId;
    public String title;

    public Stack(String categoryId, String title) {
        super();
        this.categoryId = categoryId;
        this.title = title;
    }
}
