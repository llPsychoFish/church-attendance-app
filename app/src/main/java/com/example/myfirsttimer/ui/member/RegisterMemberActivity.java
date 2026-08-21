package com.example.myfirsttimer.ui.member;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirsttimer.R;
import com.example.myfirsttimer.data.entity.Member;
import com.example.myfirsttimer.ui.firsttimer.RegisterFirstTimerActivity;
import com.example.myfirsttimer.util.Constants;
import com.example.myfirsttimer.util.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterMemberActivity extends AppCompatActivity implements MemberSearchAdapter.OnMemberSelectedListener {

    private MemberViewModel vm;
    private SessionManager session;

    private EditText etSearch, etSurname, etFirstName, etPhone, etEmail, etDob, etCourse, etLevel, etHall, etRoom;
    private RecyclerView rvSuggestions;
    private TextView tvServiceLabel, tvSuccessName;
    private LinearLayout layoutSuccess;
    private View scrollForm;
    private MemberSearchAdapter suggestionAdapter;

    private Member selectedMember;
    private boolean filling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);

        session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, com.example.myfirsttimer.ui.auth.LoginActivity.class));
            finish();
            return;
        }

        vm = new ViewModelProvider(this).get(MemberViewModel.class);

        etSearch = findViewById(R.id.etSearch);
        etSurname = findViewById(R.id.etSurname);
        etFirstName = findViewById(R.id.etFirstName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etDob = findViewById(R.id.etDob);
        etCourse = findViewById(R.id.etCourse);
        etLevel = findViewById(R.id.etLevel);
        etHall = findViewById(R.id.etHall);
        etRoom = findViewById(R.id.etRoom);
        rvSuggestions = findViewById(R.id.rvSuggestions);
        tvServiceLabel = findViewById(R.id.tvServiceLabel);
        tvSuccessName = findViewById(R.id.tvSuccessName);
        layoutSuccess = findViewById(R.id.layoutSuccess);
        scrollForm = findViewById(R.id.scrollForm);
        MaterialButton btnSubmit = findViewById(R.id.btnSubmit);
        MaterialButton btnGoFirstTimer = findViewById(R.id.btnGoFirstTimer);

        suggestionAdapter = new MemberSearchAdapter(this);
        rvSuggestions.setLayoutManager(new LinearLayoutManager(this));
        rvSuggestions.setAdapter(suggestionAdapter);

        updateServiceLabel();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (filling) return;
                selectedMember = null;
                String query = s.toString().trim();
                if (query.length() >= 2) {
                    vm.searchSuggestions(query);
                } else {
                    vm.clearSuggestions();
                }
            }
        });

        btnSubmit.setOnClickListener(v -> submit());

        btnGoFirstTimer.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterFirstTimerActivity.class))
        );

        vm.getSuggestions().observe(this, members -> {
            if (members != null && !members.isEmpty()) {
                suggestionAdapter.setMembers(members);
                rvSuggestions.setVisibility(View.VISIBLE);
            } else {
                rvSuggestions.setVisibility(View.GONE);
            }
        });

        vm.getSelectedMember().observe(this, this::autofill);

        vm.getSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                scrollForm.setVisibility(View.GONE);
                layoutSuccess.setVisibility(View.VISIBLE);
                if (selectedMember != null) {
                    tvSuccessName.setText(selectedMember.surname + " " + selectedMember.firstName);
                }
                scrollForm.postDelayed(this::finish, 1500);
            }
        });
    }

    @Override
    public void onMemberSelected(Member member) {
        vm.selectMember(member);
    }

    private void autofill(Member member) {
        if (member == null) return;
        filling = true;
        selectedMember = member;
        etSearch.setText(trim(member.surname) + " " + trim(member.firstName));
        etSurname.setText(trim(member.surname));
        etFirstName.setText(trim(member.firstName));
        etPhone.setText(trim(member.phone));
        etEmail.setText(trim(member.email));
        etDob.setText(trim(member.dateOfBirth));
        etCourse.setText(trim(member.courseOfStudy));
        etLevel.setText(trim(member.level));
        etHall.setText(trim(member.hallHostel));
        etRoom.setText(trim(member.roomNo));
        rvSuggestions.setVisibility(View.GONE);
        filling = false;
    }

    private void submit() {
        String surname = textOf(etSurname);
        String firstName = textOf(etFirstName);
        String phone = textOf(etPhone);

        if (surname.isEmpty() || firstName.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in surname, first name, and phone", Toast.LENGTH_SHORT).show();
            return;
        }

        Member form = new Member();
        form.id = selectedMember != null ? selectedMember.id : 0L;
        form.surname = surname;
        form.firstName = firstName;
        form.phone = phone;
        form.email = textOf(etEmail);
        form.courseOfStudy = textOf(etCourse);
        form.level = textOf(etLevel);
        form.hallHostel = textOf(etHall);
        form.roomNo = textOf(etRoom);
        form.dateOfBirth = textOf(etDob);

        vm.submitRegistration(form, session.getLoggedInUsherId(), session.getServiceType());
    }

    private String textOf(EditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private void updateServiceLabel() {
        String serviceType = session.getServiceType();
        if (serviceType == null || tvServiceLabel == null) return;

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
        tvServiceLabel.setText(label + " — " + new SimpleDateFormat("dd MMM yyyy", Locale.US).format(new Date()));
    }
}