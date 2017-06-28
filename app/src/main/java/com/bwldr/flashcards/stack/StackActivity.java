package com.bwldr.flashcards.stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bwldr.flashcards.R;

public class StackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        Intent intent = getIntent();
        String categoryId = intent.getStringExtra("categoryId");
        Log.d("categoryId", categoryId);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, StackFragment.newInstance(categoryId))
                    .commit();
        }
    }
}
