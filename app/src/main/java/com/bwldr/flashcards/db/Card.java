package com.bwldr.flashcards.db;

import android.arch.persistence.room.Entity;

/**
 * Flash Card object
 */
@Entity
public class Card extends BaseRecord {
    public String title;
    public String definition;

    public Card(String title, String definition) {
        super();
        this.title = title;
        this.definition = definition;
    }
}