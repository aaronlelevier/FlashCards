package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.bwldr.flashcards.db.Category;
import com.bwldr.flashcards.util.Util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Should return fixture data for testing, that would normally be Room AppDatabase calls
 */

@RunWith(AndroidJUnit4.class)
public class FakeCategoryServiceTest {

    private Context mContext;
    private FakeCategoryService mFakeCategoryService;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        mFakeCategoryService = new FakeCategoryService(mContext);
    }

    @Test
    public void insert() {
        assertEquals(0, mFakeCategoryService.selectAll().getValue().size());
        Category category = Util.create_category("foo");

        mFakeCategoryService.insert(category);
        assertEquals(1, mFakeCategoryService.selectAll().getValue().size());
        assertEquals(category, mFakeCategoryService.selectAll().getValue().get(0));
    }

    @Test
    public void selectAll() {
        Category category = Util.create_category("foo");
        mFakeCategoryService.insert(category);

        LiveData<List<Category>> categories = mFakeCategoryService.selectAll();

        assertTrue(categories.getValue() != null);
        assertEquals(categories.getValue().size(), 1);
        Category category2 = categories.getValue().get(0);
        assertEquals(category.id, category2.id);
        assertEquals(category.name, category2.name);
    }
}
