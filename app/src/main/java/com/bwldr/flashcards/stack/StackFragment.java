package com.bwldr.flashcards.stack;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwldr.flashcards.R;
import com.bwldr.flashcards.db.Stack;
import com.bwldr.flashcards.util.RecyclerItemClickListener;

import java.util.List;


public class StackFragment extends LifecycleFragment {

    private static final String CATEGORY_ID = "CATEGORY_ID";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StackViewModel mStackViewModel;

    public static StackFragment newInstance(String categoryId) {
        StackFragment fragment = new StackFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_ID, categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStackViewModel = ViewModelProviders.of(getActivity()).get(StackViewModel.class);
        String categoryId = (String) getArguments().get(CATEGORY_ID);
        mStackViewModel.getStacks(categoryId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        registerStackObserver();

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d("x", "onItemClick: " + Integer.toString(position));
                        Stack stack = mStackViewModel.getListItem(position);
                        Intent intent = new Intent(getActivity(), StackActivity.class);
                        intent.putExtra("stackId", stack.id);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Log.d("x", "onItemClick: " + Integer.toString(position));
                    }
                })
        );
    }

    private void registerStackObserver() {
        mStackViewModel.getListData().observe(this, new Observer<List<Stack>>() {
            @Override
            public void onChanged(@Nullable List<Stack> stacks) {
                mAdapter = new StackAdapter(mStackViewModel.getListData());
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }
}
