package com.event.transactions.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.event.transactions.R;
import com.event.transactions.databinding.TransactionItemBinding;
import com.event.transactions.model.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private Context context;
    private List<Transaction> transactions;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TransactionItemBinding binding = TransactionItemBinding
                .inflate(LayoutInflater.from(context), parent, false);

        return new TransactionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.binding.tvDate.setText(transaction.getDate());
        String amount = String.format("%s %s", context.getString(R.string.desc_rupee_sign), transaction.getAmount());
        holder.binding.tvPrice.setText(amount);
        holder.binding.tvCategory.setText(transaction.getCategory());
        holder.binding.tvDesc.setText(transaction.getDescription());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TransactionItemBinding binding;
        public TransactionViewHolder(TransactionItemBinding binding) {
            super(binding.getRoot());
           this. binding = binding;
        }
    }
}
