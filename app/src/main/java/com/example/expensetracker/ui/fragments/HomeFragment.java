package com.example.expensetracker.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.expensetracker.R;
import com.example.expensetracker.data.model.Expense;
import com.example.expensetracker.data.remote.ApiClient;
import com.example.expensetracker.data.remote.ExpenseApi;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView tvLatestExpense;
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tvLatestExpense = v.findViewById(R.id.tvLatestExpense);

        // Fetch expenses when fragment is created
        fetchLatestExpense();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh when returning to this fragment
        fetchLatestExpense();
    }

    private void fetchLatestExpense() {
        tvLatestExpense.setText("Loading...");

        // Note: Using ApiClient.get() instead of getClient()
        // and getExpenses() requires page and limit parameters
        ExpenseApi apiService = ApiClient.get().create(ExpenseApi.class);
        Call<List<Expense>> call = apiService.getExpenses(1, 100); // Get first 100 expenses

        call.enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Expense> expenses = response.body();

                    if (expenses.isEmpty()) {
                        tvLatestExpense.setText("No expenses yet. Add your first expense!");
                    } else {
                        // Get the latest expense (last in list)
                        Expense latestExpense = expenses.get(expenses.size() - 1);
                        displayExpense(latestExpense);
                    }
                } else {
                    Log.e(TAG, "Failed to fetch expenses: " + response.code());
                    tvLatestExpense.setText("Failed to load expenses");
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                Log.e(TAG, "Network error: " + t.getMessage());
                tvLatestExpense.setText("Network error. Please try again.");
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayExpense(Expense expense) {
        // Format the expense information
        String dateStr = "";
        if (expense.getCreatedDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
            dateStr = sdf.format(expense.getCreatedDate());
        }

        String expenseText = String.format(Locale.getDefault(),
                "Latest Expense:\n\n" +
                        "Amount: %s %.2f\n" +
                        "Category: %s\n" +
                        "Remark: %s\n" +
                        "Date: %s\n" +
                        "Created by: %s",
                expense.getCurrency() != null ? expense.getCurrency() : "$",
                expense.getAmount(),
                expense.getCategory() != null ? expense.getCategory() : "N/A",
                expense.getRemark() != null ? expense.getRemark() : "N/A",
                dateStr,
                expense.getCreatedBy() != null ? expense.getCreatedBy() : "N/A"
        );
        tvLatestExpense.setText(expenseText);
    }
}