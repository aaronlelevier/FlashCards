package com.bwldr.flashcards.db;

import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Entity;

import java.util.UUID;

/**
 * Base class for all {@link Entity} to generate the 'id'
 */
public abstract class BaseRecord {
    @PrimaryKey
    public String id;

    public BaseRecord() {
        this.id = UUID.randomUUID().toString();
    }
}
