package com.example.expensetracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.expensetracker.R;
import com.example.expensetracker.data.model.Expense;
import com.example.expensetracker.data.remote.ExpenseRepository;

public class ExpenseDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);
        TextView tvAmount = findViewById(R.id.tvAmount);
        TextView tvCurrency = findViewById(R.id.tvCurrency);
        TextView tvCategory = findViewById(R.id.tvCategory);
        TextView tvRemark = findViewById(R.id.tvRemark);
        TextView tvCreatedDate = findViewById(R.id.tvCreatedDate);
        Button btnAddNew = findViewById(R.id.btnAddNewExpense);
        Button btnBack = findViewById(R.id.btnBackToHome);
        String expenseId = getIntent().getStringExtra("expenseId");
        if (expenseId != null) {
            new ExpenseRepository().getExpenseById(expenseId, new ExpenseRepository.SingleCallback() {
                @Override public void onSuccess(Expense e) {
                    tvAmount.setText(String.valueOf(e.getAmount()));
                    tvCurrency.setText(e.getCurrency());
                    tvCategory.setText(e.getCategory());
                    tvRemark.setText(e.getRemark());
                    tvCreatedDate.setText(e.getCreatedDate() != null ? e.getCreatedDate().toString() : "");
                }
                @Override public void onError(Throwable t) { tvRemark.setText("Error: " + t.getMessage()); }
            });
        } else {
            tvAmount.setText(getIntent().getStringExtra("amount"));
            tvCurrency.setText(getIntent().getStringExtra("currency"));
            tvCategory.setText(getIntent().getStringExtra("category"));
            tvRemark.setText(getIntent().getStringExtra("remark"));
            tvCreatedDate.setText(getIntent().getStringExtra("createdDate"));
        }
        btnAddNew.setOnClickListener(v -> { startActivity(new Intent(this, AddExpenseActivity.class)); finish(); });
        btnBack.setOnClickListener(v -> finish());
    }
}