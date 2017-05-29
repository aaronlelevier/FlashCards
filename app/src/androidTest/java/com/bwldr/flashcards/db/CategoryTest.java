package com.bwldr.flashcards.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CategoryTest {

    private Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void populateCategories() {
        AppDatabase db = Room.inMemoryDatabaseBuilder(mContext, AppDatabase.class).build();
        Category category = new Category("Java");
        Category category2 = new Category("Python");
        db.categoryDao().insert(category);
        db.categoryDao().insert(category2);

        List<Category> categories = db.categoryDao().selectAll();

        assertEquals(2, categories.size());
        Category s = categories.get(0);
        assertEquals(36, s.id.length());
        assertEquals("Java", s.title);
    }
}
