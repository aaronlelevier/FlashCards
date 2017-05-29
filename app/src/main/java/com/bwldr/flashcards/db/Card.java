package com.bwldr.flashcards.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

/**
 * Flash Card object
 */
@Entity
public class Card {
    @PrimaryKey
    public String id;
    public String title;
    public String definition;

    public Card(String title, String definition) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.definition = definition;
    }
}