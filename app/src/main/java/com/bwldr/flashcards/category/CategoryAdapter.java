package com.bwldr.flashcards.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.db.Category;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mCategories;

    public CategoryAdapter(List<Category> categories) {
        mCategories = categories;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Category category = mCategories.get(position);
        viewHolder.mTextView.setText(category.title);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.category_list_item_text);
        }
    }
}
