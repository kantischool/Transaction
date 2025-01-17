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
import com.event.transactions.R;
import com.event.transactions.databinding.ActivityMainBinding;
import com.event.transactions.request.LoginRequest;
import com.event.transactions.viewmodel.AppViewmodel;
import com.event.transactions.viewmodel.AppViewmodelFactory;

import java.util.Objects;

import jakarta.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    private AppViewmodel appViewmodel;
    @Inject
    AppViewmodelFactory appViewmodelFactory;
    @Inject
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ((MyApp) getApplication()).applicationComponent.injectMainActivity(this);
        appViewmodel = new ViewModelProvider(this, appViewmodelFactory).get(AppViewmodel.class);

        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnLogin.getId()) {
            if (validate()) {
                String userName = Objects.requireNonNull(binding.tvUsername.getText()).toString();
                String password = Objects.requireNonNull(binding.tvPassword.getText()).toString();

                LoginRequest loginRequest = new LoginRequest(userName, password);
                appViewmodel.login(loginRequest).observe(this, response -> {
                    switch (response.status) {
                        case LOADING:
                            showProgressBar();
                            break;
                        case SUCCESS:
                            hideProgressBar();
                            if (response.data != null) {
                                preferences.edit().putString(TOKEN, response.data.getToken()).apply();
                                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                                startActivity(intent);
                                finish();
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
        }
    }

    private boolean validate() {
        if (binding.tvUsername.getText() == null ||
                binding.tvUsername.getText().toString().isEmpty()) {
            binding.tvUsername.setError(getString(R.string.desc_please_enter_username));
            binding.tvUsername.requestFocus();
            return false;
        }

        if (binding.tvPassword.getText() == null ||
                binding.tvPassword.getText().toString().isEmpty()) {
            binding.tvPassword.setError(getString(R.string.desc_please_enter_password));
            binding.tvPassword.requestFocus();
            return false;
        }
        return true;
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