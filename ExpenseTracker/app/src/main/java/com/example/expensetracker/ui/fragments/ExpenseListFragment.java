package com.example.expensetracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.expensetracker.R;
import com.example.expensetracker.data.model.Expense;
import com.example.expensetracker.data.remote.ExpenseRepository;
import com.example.expensetracker.ui.EndlessRecyclerViewScrollListener;
import com.example.expensetracker.ui.ExpenseAdapter;
import com.example.expensetracker.ui.ExpenseDetailActivity;
import java.util.List;

public class ExpenseListFragment extends Fragment {
    private ExpenseAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private final ExpenseRepository repo = new ExpenseRepository();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView rv = v.findViewById(R.id.rvExpenses);
        LinearLayoutManager lm = new LinearLayoutManager(requireContext());
        rv.setLayoutManager(lm);
        adapter = new ExpenseAdapter(e -> {
            Intent i = new Intent(requireContext(), ExpenseDetailActivity.class);
            i.putExtra("expenseId", e.getId());
            startActivity(i);
        });
        rv.setAdapter(adapter);
        scrollListener = new EndlessRecyclerViewScrollListener(lm) {
            @Override public void onLoadMore(int page, int totalItemsCount, RecyclerView view) { fetch(page, 20, true); }
        };
        rv.addOnScrollListener(scrollListener);
        fetch(1, 20, false);
        return v;
    }

    private void fetch(int page, int limit, boolean append) {
        repo.getExpenses(page, limit, new ExpenseRepository.ListCallback() {
            @Override public void onSuccess(List<Expense> expenses, boolean hasMore) { adapter.submit(expenses, append); }
            @Override public void onError(Throwable t) { /* TODO: show error */ }
        });
    }
}