package com.example.expensetracker.data.remote;

import com.example.expensetracker.data.model.Expense;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExpenseApi {
    @GET("expenses")
    Call<List<Expense>> getExpenses(@Query("page") int page, @Query("limit") int limit);

    @POST("expenses")
    Call<Expense> createExpense(@Body Expense expense);

    @GET("expenses/{id}")
    Call<Expense> getExpenseById(@Path("id") String id);
}