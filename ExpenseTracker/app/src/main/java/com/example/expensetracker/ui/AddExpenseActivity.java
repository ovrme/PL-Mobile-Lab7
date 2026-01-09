package com.example.expensetracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.expensetracker.R;

public class AddExpenseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        EditText etAmount = findViewById(R.id.etAmount);
        EditText etRemark = findViewById(R.id.etRemark);
        Spinner spCurrency = findViewById(R.id.spCurrency);
        Spinner spCategory = findViewById(R.id.spCategory);
        Button btnAdd = findViewById(R.id.btnAddExpense);
        spCurrency.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"KHR","USD","THB"}));
        spCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Food","Transport","Shopping"}));
        btnAdd.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("amount", etAmount.getText().toString());
            data.putExtra("currency", spCurrency.getSelectedItem().toString());
            data.putExtra("category", spCategory.getSelectedItem().toString());
            data.putExtra("remark", etRemark.getText().toString());
            setResult(RESULT_OK, data);
            finish();
        });
    }
}