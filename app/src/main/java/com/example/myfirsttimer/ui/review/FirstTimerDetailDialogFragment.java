package com.example.myfirsttimer.ui.review;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirsttimer.R;
import com.example.myfirsttimer.data.entity.FirstTimer;
import com.example.myfirsttimer.data.entity.FirstTimerDepartment;
import com.example.myfirsttimer.data.entity.Member;
import com.example.myfirsttimer.util.Constants;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class FirstTimerDetailDialogFragment extends DialogFragment {

    private ReviewViewModel viewModel;
    private ReviewViewModel.FirstTimerWithMember dataItem;

    public static FirstTimerDetailDialogFragment newInstance(ReviewViewModel.FirstTimerWithMember item) {
        FirstTimerDetailDialogFragment fragment = new FirstTimerDetailDialogFragment();
        fragment.dataItem = item;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MyFirstTimer);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_first_timer_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);

        ImageButton btnClose = view.findViewById(R.id.btnClose);
        MaterialButton btnDone = view.findViewById(R.id.btnDone);
        TextView tvDetailName = view.findViewById(R.id.tvDetailName);
        TextView tvDetailPhone = view.findViewById(R.id.tvDetailPhone);
        TextView tvDetailEmail = view.findViewById(R.id.tvDetailEmail);
        TextView tvDetailAcademic = view.findViewById(R.id.tvDetailAcademic);
        TextView tvDetailHostel = view.findViewById(R.id.tvDetailHostel);
        TextView tvDetailDob = view.findViewById(R.id.tvDetailDob);

        TextView tvDetailBornAgain = view.findViewById(R.id.tvDetailBornAgain);
        TextView tvDetailTongues = view.findViewById(R.id.tvDetailTongues);
        TextView tvDetailMembership = view.findViewById(R.id.tvDetailMembership);
        TextView tvDetailInvitedBy = view.findViewById(R.id.tvDetailInvitedBy);

        TextView tvDetailDepartments = view.findViewById(R.id.tvDetailDepartments);
        TextView tvDetailPrayerRequest = view.findViewById(R.id.tvDetailPrayerRequest);
        Spinner spinnerDetailFollowUp = view.findViewById(R.id.spinnerDetailFollowUp);

        btnClose.setOnClickListener(v -> dismiss());
        btnDone.setOnClickListener(v -> dismiss());

        if (dataItem == null) {
            return;
        }

        Member member = dataItem.member;
        FirstTimer ft = dataItem.firstTimer;
        List<FirstTimerDepartment> departments = dataItem.departments;

        // Name & Contact
        tvDetailName.setText(member.surname + " " + member.firstName);
        tvDetailPhone.setText("Phone: " + (member.phone != null && !member.phone.isEmpty() ? member.phone : "N/A"));
        tvDetailEmail.setText("Email: " + (member.email != null && !member.email.isEmpty() ? member.email : "N/A"));

        // Academic & Residency
        StringBuilder academicStr = new StringBuilder("Course & Level: ");
        if (member.courseOfStudy != null && !member.courseOfStudy.isEmpty()) {
            academicStr.append(member.courseOfStudy);
        } else {
            academicStr.append("N/A");
        }
        if (member.level != null && !member.level.isEmpty()) {
            academicStr.append(" (Level ").append(member.level).append(")");
        }
        tvDetailAcademic.setText(academicStr.toString());

        StringBuilder hostelStr = new StringBuilder("Residence: ");
        if (member.hallHostel != null && !member.hallHostel.isEmpty()) {
            hostelStr.append(member.hallHostel);
            if (member.roomNo != null && !member.roomNo.isEmpty()) {
                hostelStr.append(" - Rm ").append(member.roomNo);
            }
        } else {
            hostelStr.append("N/A");
        }
        tvDetailHostel.setText(hostelStr.toString());

        tvDetailDob.setText("Date of Birth: " + (member.dateOfBirth != null && !member.dateOfBirth.isEmpty() ? member.dateOfBirth : "N/A"));

        // Faith / Radio Button choices
        tvDetailBornAgain.setText("Born Again: " + formatBoolean(ft.isBornAgain));
        tvDetailTongues.setText("Speaks in Tongues: " + formatBoolean(ft.speaksInTongues));
        tvDetailMembership.setText("Wants BLW Membership: " + formatBoolean(ft.wantsMembership));
        tvDetailInvitedBy.setText("Invited By: " + (ft.invitedBy != null && !ft.invitedBy.trim().isEmpty() ? ft.invitedBy : "N/A"));

        // Departments
        if (departments != null && !departments.isEmpty()) {
            StringBuilder deptsBuilder = new StringBuilder();
            for (int i = 0; i < departments.size(); i++) {
                if (i > 0) deptsBuilder.append("\n");
                deptsBuilder.append("• ").append(departments.get(i).department);
            }
            tvDetailDepartments.setText(deptsBuilder.toString());
        } else {
            tvDetailDepartments.setText("No department selected");
        }

        // Prayer Request
        if (ft.prayerRequest != null && !ft.prayerRequest.trim().isEmpty()) {
            tvDetailPrayerRequest.setText(ft.prayerRequest);
        } else {
            tvDetailPrayerRequest.setText("None provided");
        }

        // Follow-Up Spinner Setup
        String[] statuses = {
                Constants.FOLLOW_UP_NEW,
                Constants.FOLLOW_UP_CONTACTED,
                Constants.FOLLOW_UP_ATTENDED_AGAIN,
                Constants.FOLLOW_UP_INTEGRATED
        };

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                statuses
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDetailFollowUp.setAdapter(spinnerAdapter);

        if (ft.followUpStatus != null) {
            for (int i = 0; i < statuses.length; i++) {
                if (statuses[i].equals(ft.followUpStatus)) {
                    spinnerDetailFollowUp.setSelection(i);
                    break;
                }
            }
        }

        spinnerDetailFollowUp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean initialized = false;

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                if (!initialized) {
                    initialized = true;
                    return;
                }
                String newStatus = statuses[pos];
                if (!newStatus.equals(ft.followUpStatus)) {
                    ft.followUpStatus = newStatus;
                    viewModel.updateFollowUpStatus(ft.id, newStatus);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private String formatBoolean(Boolean val) {
        if (val == null) return "Not specified";
        return val ? "Yes" : "No";
    }
}
