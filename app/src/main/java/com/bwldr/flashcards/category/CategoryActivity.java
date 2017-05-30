package com.bwldr.flashcards.category;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;

import com.bwldr.flashcards.R;

public class CategoryActivity extends LifecycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, CategoryFragment.newInstance())
                    .commit();
        }
    }
}
