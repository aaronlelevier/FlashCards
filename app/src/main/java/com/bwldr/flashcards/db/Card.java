package com.bwldr.flashcards.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Flash Card object
 */
@Entity
public class Card extends BaseRecord {
    /**
     * The {@link Stack} that this Card belongs to
     */
    @ColumnInfo(name="stack_id")
    public String stackId;
    public String title;
    public String definition;

    public Card(String stackId, String title, String definition) {
        super();
        this.stackId = stackId;
        this.title = title;
        this.definition = definition;
    }
}