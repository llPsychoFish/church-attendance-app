package com.example.myfirsttimer.ui.export;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirsttimer.R;
import com.example.myfirsttimer.util.Constants;
import com.example.myfirsttimer.util.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CsvExportActivity extends AppCompatActivity {

    private CsvExportViewModel viewModel;
    private TextView btnPickDate;
    private TextView tvStatus;
    private Spinner spinnerServiceType;
    private final Calendar selectedDate = Calendar.getInstance();
    private final SimpleDateFormat displayFormat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US);
    private final SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv_export);

        SessionManager session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(CsvExportViewModel.class);

        btnPickDate = findViewById(R.id.btnPickDate);
        tvStatus = findViewById(R.id.tvStatus);
        spinnerServiceType = findViewById(R.id.spinnerServiceType);
        MaterialButton btnExportAttendance = findViewById(R.id.btnExportAttendance);
        MaterialButton btnExportFirstTimers = findViewById(R.id.btnExportFirstTimers);
        MaterialButton btnExportFull = findViewById(R.id.btnExportFullAttendance);

        // Service type spinner with "All Services" option
        List<String> serviceOptions = new ArrayList<>();
        serviceOptions.add("All Services");
        for (String s : Constants.SERVICE_TYPES) {
            serviceOptions.add(s);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, serviceOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceType.setAdapter(spinnerAdapter);

        // Date picker
        btnPickDate.setText(displayFormat.format(selectedDate.getTime()));
        btnPickDate.setOnClickListener(v -> showDatePicker());

        // Export buttons
        btnExportAttendance.setOnClickListener(v -> {
            String date = dbFormat.format(selectedDate.getTime());
            int pos = spinnerServiceType.getSelectedItemPosition();
            String serviceType = pos == 0 ? null : Constants.SERVICE_TYPES[pos - 1];
            viewModel.exportAttendance(date, serviceType);
            showStatus("Exporting attendance...");
        });

        btnExportFirstTimers.setOnClickListener(v -> {
            String date = dbFormat.format(selectedDate.getTime());
            viewModel.exportFirstTimers(date);
            showStatus("Exporting first timers...");
        });

        btnExportFull.setOnClickListener(v -> {
            viewModel.exportFullAttendance();
            showStatus("Exporting full attendance...");
        });

        // Observe export result
        viewModel.getExportResult().observe(this, result -> {
            if (result == null) return;
            if (result.success) {
                showStatus(result.message);
                shareFile(result.file, result.filename);
            } else {
                showStatus(result.message);
                Toast.makeText(this, result.message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, year, month, day) -> {
            selectedDate.set(year, month, day);
            btnPickDate.setText(displayFormat.format(selectedDate.getTime()));
        }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void shareFile(File file, String filename) {
        Uri uri = FileProvider.getUriForFile(this,
                getPackageName() + ".fileprovider", file);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/csv");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, filename);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share CSV via"));
    }

    private void showStatus(String message) {
        tvStatus.setText(message);
        tvStatus.setVisibility(View.VISIBLE);
    }
}
