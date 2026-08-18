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

public class ResetCredentialsActivity extends AppCompatActivity {
    private AuthViewModel vm;
    private TextInputEditText etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_credentials);

        vm = new ViewModelProvider(this).get(AuthViewModel.class);
        etUsername = findViewById(R.id.etUsername);
        MaterialButton btnReset = findViewById(R.id.btnReset);
        TextView tvBack = findViewById(R.id.tvBackToLogin);

        btnReset.setOnClickListener(v -> {
            String username = textOf(etUsername);
            if (username.isEmpty()) {
                Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
                return;
            }
            vm.resetCredentials(username);
        });

        tvBack.setOnClickListener(v -> finish());

        vm.getResetDone().observe(this, done -> {
            if (done) {
                Toast.makeText(this,
                        "Credentials cleared. Please re-register.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, RegisterUsherActivity.class));
                finish();
            }
        });
    }

    private String textOf(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}
