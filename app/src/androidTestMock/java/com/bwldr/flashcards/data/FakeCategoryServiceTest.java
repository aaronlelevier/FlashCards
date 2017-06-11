package com.bwldr.flashcards.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.bwldr.flashcards.db.Category;

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
    public void selectAll() {
        LiveData<List<Category>> categories = mFakeCategoryService.selectAll();

        assertTrue(categories.getValue() != null);
        assertEquals(categories.getValue().size(), 1);
        Category category = categories.getValue().get(0);
        assertTrue(category.id != null);
        assertTrue(category.name != null);
    }
}
