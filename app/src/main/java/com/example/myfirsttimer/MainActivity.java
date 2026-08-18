package com.example.myfirsttimer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.myfirsttimer.ui.auth.LoginActivity;
import com.example.myfirsttimer.ui.home.ServiceSelectionDialog;
import com.example.myfirsttimer.ui.member.RegisterMemberActivity;
import com.example.myfirsttimer.util.Constants;
import com.example.myfirsttimer.util.SessionManager;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements ServiceSelectionDialog.OnServiceSelectedListener {

    private MaterialButton btnRegisterMember;
    private MaterialButton btnRegisterFirstTimer;
    private MaterialButton btnLogout;
    private TextView tvServiceType;
    private SessionManager session;

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

        session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        btnRegisterMember = findViewById(R.id.btnRegisterMember);
        btnRegisterFirstTimer = findViewById(R.id.btnRegisterFirstTimer);
        btnLogout = findViewById(R.id.btnLogout);
        tvServiceType = findViewById(R.id.tvServiceType);

        if (!session.hasServiceType()) {
            showServiceSelectionDialog();
        } else {
            updateServiceTypeDisplay();
        }

        btnRegisterMember.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterMemberActivity.class))
        );

        btnRegisterFirstTimer.setOnClickListener(v ->
                Toast.makeText(this, "Register First Timer — coming in Phase 5", Toast.LENGTH_SHORT).show()
        );

        btnLogout.setOnClickListener(v -> {
            session.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void showServiceSelectionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ServiceSelectionDialog dialog = ServiceSelectionDialog.newInstance();
        dialog.show(fm, "service_selection");
    }

    private void updateServiceTypeDisplay() {
        String serviceType = session.getServiceType();
        if (serviceType == null || tvServiceType == null) return;

        String label;
        switch (serviceType) {
            case Constants.SERVICE_SUN:
                label = "Sunday Service";
                break;
            case Constants.SERVICE_WED:
                label = "Wednesday Service";
                break;
            case Constants.SERVICE_FRI:
                label = "Friday Service";
                break;
            case Constants.SERVICE_CELL:
                label = "Cell Meeting";
                break;
            default:
                label = serviceType;
                break;
        }
        tvServiceType.setText(label);
    }

    @Override
    public void onServiceSelected(String serviceType) {
        updateServiceTypeDisplay();
    }
}
