package com.example.myfirsttimer.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirsttimer.MainActivity;
import com.example.myfirsttimer.R;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterUsherActivity extends AppCompatActivity {
    private AuthViewModel vm;
    private TextInputEditText etName;
    private TextInputEditText etUsername;
    private TextInputEditText etPin;
    private TextInputEditText etConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_usher);

        vm = new ViewModelProvider(this).get(AuthViewModel.class);
        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etPin = findViewById(R.id.etPin);
        etConfirm = findViewById(R.id.etConfirmPin);
        MaterialButton btnCreate = findViewById(R.id.btnCreateAccount);
        TextView tvBack = findViewById(R.id.tvBackToLogin);

        btnCreate.setOnClickListener(v -> {
            String name = textOf(etName);
            String username = textOf(etUsername);
            String pin = textOf(etPin);
            String confirm = textOf(etConfirm);

            if (name.isEmpty() || username.isEmpty() || pin.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pin.equals(confirm)) {
                Toast.makeText(this, "PINs do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            vm.register(name, username, pin);
        });

        tvBack.setOnClickListener(v -> finish());

        vm.getAuthResult().observe(this, result -> {
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();
            if (result.success) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }

    private String textOf(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}
