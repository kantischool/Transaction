package com.event.transactions.ui;

import static com.event.transactions.Constant.TOKEN;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;

import com.event.transactions.MyApp;
import com.event.transactions.R;
import com.event.transactions.databinding.ActivityBioMetricBinding;

import jakarta.inject.Inject;

public class BioMetricActivity extends AppCompatActivity {

    @Inject
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.event.transactions.databinding.ActivityBioMetricBinding binding = ActivityBioMetricBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        ((MyApp) getApplication()).applicationComponent.injectBioMetricActivity(this);
        showBioMetricPromptDialog();
        binding.btnBiometric.setOnClickListener(view1 -> showBioMetricPromptDialog());

    }

    private void showBioMetricPromptDialog() {
        if (checkBiometricSupport()) {
            BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    showToast(getString(R.string.desc_try_again));
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Intent intent;
                    if (preferences.getString(TOKEN, "").isEmpty()) {
                        intent = new Intent(BioMetricActivity.this, MainActivity.class);
                    } else {
                        intent = new Intent(BioMetricActivity.this, TransactionActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    showToast(getString(R.string.desc_try_again));
                }
            };

            BiometricPrompt.PromptInfo promptInfo = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle(getString(R.string.desc_verify_your_identity))
                        .setNegativeButtonText(getString(R.string.desc_cancel))
                        .build();

                BiometricPrompt biometricPrompt = new BiometricPrompt(
                        this,
                        getMainExecutor(),
                        authenticationCallback
                );

                biometricPrompt.authenticate(promptInfo);
            }
        }
    }

    private Boolean checkBiometricSupport() {
        KeyguardManager keyguardManager =
                (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            showToast(getString(R.string.desc_enable_fingerprint_from_setting));
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {
            showToast(getString(R.string.desc_enable_fingreprint_suthentication));
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}