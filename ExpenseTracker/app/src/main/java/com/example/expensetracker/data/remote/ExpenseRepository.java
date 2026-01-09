package com.example.expensetracker.data.remote;

import androidx.annotation.NonNull;
import com.example.expensetracker.data.model.Expense;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseRepository {
    private final ExpenseApi api = ApiClient.get().create(ExpenseApi.class);

    public interface ListCallback {
        void onSuccess(List<Expense> expenses, boolean hasMore);
        void onError(Throwable t);
    }

    public interface SingleCallback {
        void onSuccess(Expense expense);
        void onError(Throwable t);
    }

    public void getExpenses(int page, int limit, ListCallback cb) {
        api.getExpenses(page, limit).enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(@NonNull Call<List<Expense>> call, @NonNull Response<List<Expense>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean hasMore = response.body().size() == limit;
                    cb.onSuccess(response.body(), hasMore);
                } else {
                    cb.onError(new RuntimeException("Failed: " + response.code()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Expense>> call, @NonNull Throwable t) {
                cb.onError(t);
            }
        });
    }

    public void createExpense(Expense expense, SingleCallback cb) {
        api.createExpense(expense).enqueue(new Callback<Expense>() {
            @Override
            public void onResponse(@NonNull Call<Expense> call, @NonNull Response<Expense> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.onSuccess(response.body());
                } else {
                    cb.onError(new RuntimeException("Failed: " + response.code()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Expense> call, @NonNull Throwable t) {
                cb.onError(t);
            }
        });
    }

    public void getExpenseById(String id, SingleCallback cb) {
        api.getExpenseById(id).enqueue(new Callback<Expense>() {
            @Override
            public void onResponse(@NonNull Call<Expense> call, @NonNull Response<Expense> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.onSuccess(response.body());
                } else {
                    cb.onError(new RuntimeException("Failed: " + response.code()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Expense> call, @NonNull Throwable t) {
                cb.onError(t);
            }
        });
    }
}