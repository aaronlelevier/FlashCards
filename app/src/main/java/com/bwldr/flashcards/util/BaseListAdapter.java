package com.bwldr.flashcards.util;

import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwldr.flashcards.R;

import java.util.List;

/**
 * Generic RecyclerView.Adapter subclass for Category/Stack because
 * the only difference between them is the Object type
 */

public class BaseListAdapter<T> extends RecyclerView.Adapter<BaseListAdapter.ViewHolder> {

    private LiveData<List<T>> mItems;

    public BaseListAdapter(LiveData<List<T>> items) {
        mItems = items;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        T item = mItems.getValue().get(position);
        viewHolder.mTextView.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mItems.getValue() != null)
            count = mItems.getValue().size();
        return count;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.list_item_text);
        }
    }
}
