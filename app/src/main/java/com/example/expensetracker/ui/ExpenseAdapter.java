package com.example.expensetracker.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.expensetracker.R;
import com.example.expensetracker.data.model.Expense;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.VH> {
    public interface OnClick { void onClick(Expense e); }
    private final List<Expense> items = new ArrayList<>();
    private final OnClick onClick;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    public ExpenseAdapter(OnClick onClick) { this.onClick = onClick; }

    public void submit(List<Expense> page, boolean append) {
        if (!append) items.clear();
        items.addAll(page);
        notifyDataSetChanged();
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Expense e = items.get(pos);
        h.tvAmount.setText(String.valueOf(e.getAmount()));
        h.tvCurrency.setText(e.getCurrency());
        h.tvCategory.setText(e.getCategory());
        h.tvRemark.setText(e.getRemark());
        h.tvCreatedDate.setText(e.getCreatedDate() != null ? sdf.format(e.getCreatedDate()) : "");
        h.itemView.setOnClickListener(v -> onClick.onClick(e));
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvAmount, tvCurrency, tvCategory, tvRemark, tvCreatedDate;
        VH(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCurrency = itemView.findViewById(R.id.tvCurrency);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
        }
    }
}