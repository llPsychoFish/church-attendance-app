package com.example.myfirsttimer.ui.firsttimer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirsttimer.MainActivity;
import com.example.myfirsttimer.R;
import com.example.myfirsttimer.util.Constants;
import com.example.myfirsttimer.util.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RegisterFirstTimerActivity extends AppCompatActivity {

    private FirstTimerViewModel vm;
    private SessionManager session;

    private EditText etSurname, etFirstName, etPhone, etEmail, etDob;
    private EditText etCourse, etLevel, etHall, etRoom, etInvitedBy, etPrayer;
    private RadioGroup rgBornAgain, rgTongues, rgMembership;
    private CheckBox cbUshering, cbChoir, cbTechnical, cbCreativeArt, cbMedia, cbInnovations;
    private TextView tvServiceLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first_timer);

        session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, com.example.myfirsttimer.ui.auth.LoginActivity.class));
            finish();
            return;
        }

        vm = new ViewModelProvider(this).get(FirstTimerViewModel.class);

        // Bind views
        etSurname = findViewById(R.id.etSurname);
        etFirstName = findViewById(R.id.etFirstName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etDob = findViewById(R.id.etDob);
        etCourse = findViewById(R.id.etCourse);
        etLevel = findViewById(R.id.etLevel);
        etHall = findViewById(R.id.etHall);
        etRoom = findViewById(R.id.etRoom);
        etInvitedBy = findViewById(R.id.etInvitedBy);
        etPrayer = findViewById(R.id.etPrayer);
        rgBornAgain = findViewById(R.id.rgBornAgain);
        rgTongues = findViewById(R.id.rgTongues);
        rgMembership = findViewById(R.id.rgMembership);
        cbUshering = findViewById(R.id.cbUshering);
        cbChoir = findViewById(R.id.cbChoir);
        cbTechnical = findViewById(R.id.cbTechnical);
        cbCreativeArt = findViewById(R.id.cbCreativeArt);
        cbMedia = findViewById(R.id.cbMedia);
        cbInnovations = findViewById(R.id.cbInnovations);
        tvServiceLabel = findViewById(R.id.tvServiceLabel);
        MaterialButton btnSubmit = findViewById(R.id.btnSubmit);

        updateServiceLabel();

        btnSubmit.setOnClickListener(v -> submit());

        vm.getSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(this, "First timer registered successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }

    private void submit() {
        String surname = textOf(etSurname);
        String firstName = textOf(etFirstName);
        String phone = textOf(etPhone);

        if (surname.isEmpty() || firstName.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in surname, first name, and phone", Toast.LENGTH_SHORT).show();
            return;
        }

        Boolean isBornAgain = getRadioValue(rgBornAgain, R.id.rbBornAgainYes);
        Boolean speaksInTongues = getRadioValue(rgTongues, R.id.rbTonguesYes);
        Boolean wantsMembership = getRadioValue(rgMembership, R.id.rbMemberYes);

        List<String> departments = new ArrayList<>();
        if (cbUshering.isChecked()) departments.add("Ushering");
        if (cbChoir.isChecked()) departments.add("Choir");
        if (cbTechnical.isChecked()) departments.add("Technical");
        if (cbCreativeArt.isChecked()) departments.add("Creative Art");
        if (cbMedia.isChecked()) departments.add("Media & New Media");
        if (cbInnovations.isChecked()) departments.add("Innovations");

        vm.registerFirstTimer(
                surname, firstName, phone,
                textOf(etEmail), textOf(etCourse), textOf(etLevel),
                textOf(etHall), textOf(etRoom), textOf(etDob),
                textOf(etInvitedBy), isBornAgain, speaksInTongues,
                wantsMembership, textOf(etPrayer), departments,
                session.getLoggedInUsherId(), session.getServiceType()
        );
    }

    private String textOf(EditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    private Boolean getRadioValue(RadioGroup rg, int yesId) {
        if (rg.getCheckedRadioButtonId() == -1) return null;
        return rg.getCheckedRadioButtonId() == yesId;
    }

    private void updateServiceLabel() {
        String serviceType = session.getServiceType();
        if (serviceType == null || tvServiceLabel == null) return;

        String label;
        switch (serviceType) {
            case Constants.SERVICE_SUN: label = "Sunday Service"; break;
            case Constants.SERVICE_WED: label = "Wednesday Service"; break;
            case Constants.SERVICE_FRI: label = "Friday Service"; break;
            case Constants.SERVICE_CELL: label = "Cell Meeting"; break;
            default: label = serviceType; break;
        }
        tvServiceLabel.setText(label + " — " + new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.US).format(new java.util.Date()));
    }
}
