package com.bwldr.flashcards.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bwldr.flashcards.R;

public class CategoryActivity extends AppCompatActivity {

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
