package com.bwldr.flashcards.stack;


import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.db.Stack;

import java.util.List;

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.ViewHolder> {

    private LiveData<List<Stack>> mStacks;

    public StackAdapter(LiveData<List<Stack>> stacks) {
        mStacks = stacks;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Stack stack = mStacks.getValue().get(position);
        viewHolder.mTextView.setText(stack.title);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mStacks.getValue() != null)
            count = mStacks.getValue().size();
        return count;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.list_item_text);
        }
    }
}