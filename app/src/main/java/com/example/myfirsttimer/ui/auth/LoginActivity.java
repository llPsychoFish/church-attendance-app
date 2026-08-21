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

public class LoginActivity extends AppCompatActivity {
    private AuthViewModel vm;
    private TextInputEditText etUsername;
    private TextInputEditText etPin;
    private MaterialButton btnLogin;
    private TextView tvRegister;
    private TextView tvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vm = new ViewModelProvider(this).get(AuthViewModel.class);
        etUsername = findViewById(R.id.etUsername);
        etPin = findViewById(R.id.etPin);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvGoRegister);
        tvForgot = findViewById(R.id.tvForgotPin);

        btnLogin.setOnClickListener(v -> {
            String username = textOf(etUsername);
            String pin = textOf(etPin);
            if (username.isEmpty() || pin.isEmpty()) {
                Toast.makeText(this, "Enter username and PIN", Toast.LENGTH_SHORT).show();
                return;
            }
            vm.login(username, pin);
        });

        tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterUsherActivity.class)));

        tvForgot.setOnClickListener(v -> startActivity(new Intent(this, ResetCredentialsActivity.class)));

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
