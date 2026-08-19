package com.example.myfirsttimer.ui.review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirsttimer.R;
import com.example.myfirsttimer.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<ReviewViewModel.AttendanceWithMember> items = new ArrayList<>();

    public void setItems(List<ReviewViewModel.AttendanceWithMember> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewViewModel.AttendanceWithMember item = items.get(position);
        holder.tvMemberName.setText(item.member.surname + " " + item.member.firstName);
        holder.tvServiceType.setText(getServiceLabel(item.attendance.serviceType));
        holder.tvTime.setText(formatTime(item.attendance.timestamp));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String getServiceLabel(String code) {
        if (Constants.SERVICE_SUN.equals(code)) return "Sunday Service";
        if (Constants.SERVICE_WED.equals(code)) return "Wednesday Service";
        if (Constants.SERVICE_FRI.equals(code)) return "Friday Service";
        if (Constants.SERVICE_CELL.equals(code)) return "Cell Meeting";
        return code;
    }

    private String formatTime(long timestamp) {
        return new SimpleDateFormat("h:mm a", Locale.US).format(new Date(timestamp));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvMemberName;
        final TextView tvServiceType;
        final TextView tvTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
            tvServiceType = itemView.findViewById(R.id.tvServiceType);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
