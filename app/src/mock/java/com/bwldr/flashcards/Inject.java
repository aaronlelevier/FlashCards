package com.bwldr.flashcards;

import com.bwldr.flashcards.data.CategoryRepositoryContract;
import com.bwldr.flashcards.data.FakeCategoryRepository;


public class Inject {

    private static CategoryRepositoryContract mCategoryRepository = null;

    private Inject() {
        throw new AssertionError("Can't be instantiated because this is a static class");
    }

    public synchronized static CategoryRepositoryContract getCategoryRepository() {
        if (mCategoryRepository == null) {
            mCategoryRepository = new FakeCategoryRepository(null);
        }
        return mCategoryRepository;
    }
}
