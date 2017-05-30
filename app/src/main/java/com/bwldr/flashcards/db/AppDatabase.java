package com.bwldr.flashcards.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Card.class, Stack.class, Category.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardDao cardDao();
    public abstract StackDao stackDao();
    public abstract CategoryDao categoryDao();
}
