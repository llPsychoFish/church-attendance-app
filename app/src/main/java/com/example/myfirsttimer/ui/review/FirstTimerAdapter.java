package com.example.myfirsttimer.ui.review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirsttimer.R;
import com.example.myfirsttimer.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class FirstTimerAdapter extends RecyclerView.Adapter<FirstTimerAdapter.ViewHolder> {

    public interface OnFollowUpStatusChangedListener {
        void onFollowUpStatusChanged(long firstTimerId, String newStatus);
    }

    public interface OnItemClickListener {
        void onItemClick(ReviewViewModel.FirstTimerWithMember item);
    }

    private List<ReviewViewModel.FirstTimerWithMember> items = new ArrayList<>();
    private OnFollowUpStatusChangedListener listener;
    private OnItemClickListener itemClickListener;

    public void setItems(List<ReviewViewModel.FirstTimerWithMember> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnFollowUpStatusChangedListener(OnFollowUpStatusChangedListener listener) {
        this.listener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_first_timer_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewViewModel.FirstTimerWithMember item = items.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(item);
            }
        });
        holder.tvMemberName.setText(item.member.surname + " " + item.member.firstName);
        holder.tvPhone.setText(item.member.phone != null ? item.member.phone : "No phone");

        if (item.departments != null && !item.departments.isEmpty()) {
            StringBuilder depts = new StringBuilder();
            for (int i = 0; i < item.departments.size(); i++) {
                if (i > 0) depts.append(", ");
                depts.append(item.departments.get(i).department);
            }
            holder.tvDepartment.setText(depts.toString());
            holder.tvDepartment.setVisibility(View.VISIBLE);
        } else {
            holder.tvDepartment.setVisibility(View.GONE);
        }

        String[] statuses = {
                Constants.FOLLOW_UP_NEW,
                Constants.FOLLOW_UP_CONTACTED,
                Constants.FOLLOW_UP_ATTENDED_AGAIN,
                Constants.FOLLOW_UP_INTEGRATED
        };

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                holder.itemView.getContext(),
                android.R.layout.simple_spinner_item,
                statuses
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerFollowUp.setAdapter(spinnerAdapter);

        String currentStatus = item.firstTimer.followUpStatus;
        if (currentStatus != null) {
            for (int i = 0; i < statuses.length; i++) {
                if (statuses[i].equals(currentStatus)) {
                    holder.spinnerFollowUp.setSelection(i);
                    break;
                }
            }
        }

        final long firstTimerId = item.firstTimer.id;
        holder.spinnerFollowUp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean initialized = false;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (!initialized) {
                    initialized = true;
                    return;
                }
                if (listener != null) {
                    listener.onFollowUpStatusChanged(firstTimerId, statuses[pos]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvMemberName;
        final TextView tvPhone;
        final TextView tvDepartment;
        final Spinner spinnerFollowUp;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            spinnerFollowUp = itemView.findViewById(R.id.spinnerFollowUp);
        }
    }
}
