package com.event.transactions.ui;

import static com.event.transactions.Constant.ERROR;
import static com.event.transactions.Constant.LOADING;
import static com.event.transactions.Constant.SUCCESS;
import static com.event.transactions.Constant.TOKEN;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.event.transactions.MyApp;
import com.event.transactions.adapter.TransactionAdapter;
import com.event.transactions.databinding.ActivityTransactionBinding;
import com.event.transactions.util.NetWorkUtils;
import com.event.transactions.viewmodel.AppViewmodel;
import com.event.transactions.viewmodel.AppViewmodelFactory;

import jakarta.inject.Inject;

public class TransactionActivity extends AppCompatActivity {

    private ActivityTransactionBinding binding;
    private AppViewmodel appViewmodel;
    @Inject
    AppViewmodelFactory appViewmodelFactory;
    @Inject
    SharedPreferences preferences;

    private TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ((MyApp) getApplication()).applicationComponent.injectTransactionActivity(this);
        appViewmodel = new ViewModelProvider(this, appViewmodelFactory).get(AppViewmodel.class);

        binding.btnLogout.setOnClickListener(view -> {
            preferences.edit().putString(TOKEN, "").apply();
            Intent intent = new Intent(TransactionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        if (NetWorkUtils.isInternetAvailable(this)) {
            transactions();
        } else {
            getTransactionFromRoom();
        }
    }

    private void getTransactionFromRoom() {
        appViewmodel.transactionsFromRoom().observe(this, transactions -> {
            transactionAdapter = new TransactionAdapter(this, transactions);
            binding.rvTransaction.setAdapter(transactionAdapter);
        });
    }

    private void transactions() {
        appViewmodel.transaction().observe(this, response -> {
            switch (response.status) {
                case LOADING:
                    showProgressBar();
                    // show loader
                    break;
                case SUCCESS:
                    hideProgressBar();
                    if (response.data != null) {
                        transactionAdapter =new TransactionAdapter(this, response.data);
                        binding.rvTransaction.setAdapter(transactionAdapter);
                    } else {
                        showToast(response.message);
                    }
                    break;
                case ERROR:
                    hideProgressBar();
                    showToast(response.message);
                    break;
            }
        });
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void showToast(String message) {
        if (message == null) {
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}