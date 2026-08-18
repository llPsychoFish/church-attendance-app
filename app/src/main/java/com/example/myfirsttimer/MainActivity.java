package com.example.myfirsttimer;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnRegisterMember;
    private MaterialButton btnRegisterFirstTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnRegisterMember = findViewById(R.id.btnRegisterMember);
        btnRegisterFirstTimer = findViewById(R.id.btnRegisterFirstTimer);

        if (btnRegisterMember != null) {
            btnRegisterMember.setOnClickListener(v -> 
                Toast.makeText(MainActivity.this, "Register Member selected", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnRegisterFirstTimer != null) {
            btnRegisterFirstTimer.setOnClickListener(v -> 
                Toast.makeText(MainActivity.this, "Register First Timer selected (Welcome!)", Toast.LENGTH_SHORT).show()
            );
        }
    }
}