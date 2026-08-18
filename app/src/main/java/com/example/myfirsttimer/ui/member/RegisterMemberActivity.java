package com.example.myfirsttimer.ui.member;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirsttimer.MainActivity;
import com.example.myfirsttimer.R;
import com.example.myfirsttimer.ui.home.ServiceSelectionDialog;
import com.example.myfirsttimer.util.Constants;
import com.example.myfirsttimer.util.SessionManager;
import com.google.android.material.button.MaterialButton;

public class RegisterMemberActivity extends AppCompatActivity implements MemberSearchAdapter.OnMarkPresentListener {

    private MemberViewModel vm;
    private SessionManager session;
    private EditText etSearch;
    private RecyclerView rvResults;
    private LinearLayout layoutNotFound;
    private LinearLayout layoutSuccess;
    private TextView tvResultHeader;
    private TextView tvServiceLabel;
    private TextView tvSuccessName;
    private MemberSearchAdapter adapter;

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
        rvResults = findViewById(R.id.rvResults);
        layoutNotFound = findViewById(R.id.layoutNotFound);
        layoutSuccess = findViewById(R.id.layoutSuccess);
        tvResultHeader = findViewById(R.id.tvResultHeader);
        tvServiceLabel = findViewById(R.id.tvServiceLabel);
        tvSuccessName = findViewById(R.id.tvSuccessName);
        MaterialButton btnSearch = findViewById(R.id.btnSearch);
        MaterialButton btnGoFirstTimer = findViewById(R.id.btnGoFirstTimer);

        adapter = new MemberSearchAdapter(this);
        rvResults.setLayoutManager(new LinearLayoutManager(this));
        rvResults.setAdapter(adapter);

        updateServiceLabel();

        btnSearch.setOnClickListener(v -> doSearch());

        btnGoFirstTimer.setOnClickListener(v -> {
            // Phase 5 will implement the First Timer flow
            Toast.makeText(this, "First Timer flow — coming in Phase 5", Toast.LENGTH_SHORT).show();
        });

        vm.getSearchResults().observe(this, members -> {
            if (members != null && !members.isEmpty()) {
                adapter.setMembers(members);
                rvResults.setVisibility(View.VISIBLE);
                tvResultHeader.setVisibility(View.VISIBLE);
                layoutNotFound.setVisibility(View.GONE);
            } else {
                rvResults.setVisibility(View.GONE);
                tvResultHeader.setVisibility(View.GONE);
            }
        });

        vm.getNotFound().observe(this, isNotFound -> {
            if (Boolean.TRUE.equals(isNotFound)) {
                layoutNotFound.setVisibility(View.VISIBLE);
                rvResults.setVisibility(View.GONE);
                tvResultHeader.setVisibility(View.GONE);
            }
        });

        vm.getMarkedMember().observe(this, member -> {
            if (member != null) {
                layoutSuccess.setVisibility(View.VISIBLE);
                tvSuccessName.setText(member.surname + " " + member.firstName);
                rvResults.setVisibility(View.GONE);
                tvResultHeader.setVisibility(View.GONE);
                layoutNotFound.setVisibility(View.GONE);

                // Return to home after 1.5 seconds
                rvResults.postDelayed(() -> {
                    finish();
                }, 1500);
            }
        });
    }

    private void doSearch() {
        String query = etSearch.getText() == null ? "" : etSearch.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(this, "Enter a name or phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        layoutSuccess.setVisibility(View.GONE);
        vm.search(query);
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
        tvServiceLabel.setText(label + " — " + new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.US).format(new java.util.Date()));
    }

    @Override
    public void onMarkPresent(com.example.myfirsttimer.data.entity.Member member) {
        vm.markPresent(member, session.getLoggedInUsherId(), session.getServiceType());
    }
}
