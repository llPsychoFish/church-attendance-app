package com.example.myfirsttimer.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myfirsttimer.R;
import com.example.myfirsttimer.util.Constants;
import com.example.myfirsttimer.util.SessionManager;
import com.google.android.material.button.MaterialButton;

public class ServiceSelectionDialog extends DialogFragment {

    public interface OnServiceSelectedListener {
        void onServiceSelected(String serviceType);
    }

    private SessionManager session;

    public static ServiceSelectionDialog newInstance() {
        return new ServiceSelectionDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        session = new SessionManager(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_service_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnSun = view.findViewById(R.id.btnServiceSun);
        MaterialButton btnWed = view.findViewById(R.id.btnServiceWed);
        MaterialButton btnFri = view.findViewById(R.id.btnServiceFri);
        MaterialButton btnCell = view.findViewById(R.id.btnServiceCell);

        View.OnClickListener listener = v -> {
            int id = v.getId();
            String serviceType;
            if (id == R.id.btnServiceSun) {
                serviceType = Constants.SERVICE_SUN;
            } else if (id == R.id.btnServiceWed) {
                serviceType = Constants.SERVICE_WED;
            } else if (id == R.id.btnServiceFri) {
                serviceType = Constants.SERVICE_FRI;
            } else if (id == R.id.btnServiceCell) {
                serviceType = Constants.SERVICE_CELL;
            } else {
                return;
            }
            session.setServiceType(serviceType);
            if (getActivity() instanceof OnServiceSelectedListener) {
                ((OnServiceSelectedListener) getActivity()).onServiceSelected(serviceType);
            }
            dismiss();
        };

        btnSun.setOnClickListener(listener);
        btnWed.setOnClickListener(listener);
        btnFri.setOnClickListener(listener);
        btnCell.setOnClickListener(listener);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
        }
    }
}
