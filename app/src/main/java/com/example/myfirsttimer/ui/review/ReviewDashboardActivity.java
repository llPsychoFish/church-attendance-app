package com.example.myfirsttimer.ui.review;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirsttimer.R;
import com.example.myfirsttimer.util.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReviewDashboardActivity extends AppCompatActivity {

    private ReviewViewModel viewModel;
    private TextView btnPickDate;
    private TextView tvCountSun, tvCountWed, tvCountFri, tvCountCell;
    private final Calendar selectedDate = Calendar.getInstance();
    private final SimpleDateFormat displayFormat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US);
    private final SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    private final AttendanceListFragment attendanceFragment = new AttendanceListFragment();
    private final FirstTimerListFragment firstTimerFragment = new FirstTimerListFragment();
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_dashboard);

        SessionManager session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        tvCountSun = findViewById(R.id.tvCountSun);
        tvCountWed = findViewById(R.id.tvCountWed);
        tvCountFri = findViewById(R.id.tvCountFri);
        tvCountCell = findViewById(R.id.tvCountCell);
        btnPickDate = findViewById(R.id.btnPickDate);
        MaterialButton btnPrevDate = findViewById(R.id.btnPrevDate);
        MaterialButton btnNextDate = findViewById(R.id.btnNextDate);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Set up fragments
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, firstTimerFragment, "first_timer")
                .hide(firstTimerFragment)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, attendanceFragment, "attendance")
                .commit();
        activeFragment = attendanceFragment;

        // Tab setup
        tabLayout.addTab(tabLayout.newTab().setText("Attendance"));
        tabLayout.addTab(tabLayout.newTab().setText("First Timers"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment target = tab.getPosition() == 0 ? attendanceFragment : firstTimerFragment;
                getSupportFragmentManager().beginTransaction()
                        .hide(activeFragment)
                        .show(target)
                        .commit();
                activeFragment = target;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Date controls
        btnPickDate.setOnClickListener(v -> showDatePicker());
        btnPrevDate.setOnClickListener(v -> {
            selectedDate.add(Calendar.DAY_OF_MONTH, -1);
            updateDateAndLoad();
        });
        btnNextDate.setOnClickListener(v -> {
            selectedDate.add(Calendar.DAY_OF_MONTH, 1);
            updateDateAndLoad();
        });

        // Observe stats
        viewModel.getServiceCounts().observe(this, counts -> {
            tvCountSun.setText(String.valueOf(counts.getOrDefault("SUN", 0)));
            tvCountWed.setText(String.valueOf(counts.getOrDefault("WED", 0)));
            tvCountFri.setText(String.valueOf(counts.getOrDefault("FRI", 0)));
            tvCountCell.setText(String.valueOf(counts.getOrDefault("CELL", 0)));
        });

        updateDateAndLoad();
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, year, month, day) -> {
            selectedDate.set(year, month, day);
            updateDateAndLoad();
        }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateAndLoad() {
        btnPickDate.setText(displayFormat.format(selectedDate.getTime()));
        String dateStr = dbFormat.format(selectedDate.getTime());
        viewModel.loadDataForDate(dateStr);
    }
}
