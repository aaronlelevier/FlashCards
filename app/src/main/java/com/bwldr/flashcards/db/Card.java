package com.bwldr.flashcards.db;

import android.arch.persistence.room.Entity;

/**
 * Flash Card object
 */
@Entity
public class Card extends BaseRecord {

    public String stackId;
    public String question;
    public String answer;

    public Card(String stackId, String question, String answer) {
        super();
        this.stackId = stackId;
        this.question = question;
        this.answer = answer;
    }
}