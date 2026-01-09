package com.example.expensetracker.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.expensetracker.R;
import com.example.expensetracker.data.model.Expense;
import com.example.expensetracker.data.remote.ExpenseRepository;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Date;

public class AddExpenseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        EditText etAmount = v.findViewById(R.id.etAmount);
        EditText etRemark = v.findViewById(R.id.etRemark);
        Spinner spCurrency = v.findViewById(R.id.spCurrency);
        Spinner spCategory = v.findViewById(R.id.spCategory);
        Button btnSubmit = v.findViewById(R.id.btnSubmit);
        spCurrency.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"KHR","USD","THB"}));
        spCategory.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Food","Transport","Shopping"}));
        btnSubmit.setOnClickListener(b -> {
            try {
                double amount = Double.parseDouble(etAmount.getText().toString());
                String currency = spCurrency.getSelectedItem().toString();
                String category = spCategory.getSelectedItem().toString();
                String remark = etRemark.getText().toString();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Expense e = new Expense(amount, currency, category, remark, new Date(), uid);
                new ExpenseRepository().createExpense(e, new ExpenseRepository.SingleCallback() {
                    @Override public void onSuccess(Expense expense) { Toast.makeText(requireContext(), "Expense added", Toast.LENGTH_SHORT).show(); }
                    @Override public void onError(Throwable t) { Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show(); }
                });
            } catch (Exception ex) { Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_LONG).show(); }
        });
        return v;
    }
}