package com.example.myfirsttimer.ui.member;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirsttimer.R;
import com.example.myfirsttimer.data.entity.Member;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MemberSearchAdapter extends RecyclerView.Adapter<MemberSearchAdapter.ViewHolder> {

    public interface OnMarkPresentListener {
        void onMarkPresent(Member member);
    }

    private List<Member> members = new ArrayList<>();
    private final OnMarkPresentListener listener;

    public MemberSearchAdapter(OnMarkPresentListener listener) {
        this.listener = listener;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_member_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Member member = members.get(position);
        String fullName = member.surname + " " + member.firstName;
        holder.tvName.setText(fullName);
        holder.tvPhone.setText(member.phone != null ? member.phone : "");
        holder.btnMarkPresent.setOnClickListener(v -> listener.onMarkPresent(member));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPhone;
        MaterialButton btnMarkPresent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvMemberName);
            tvPhone = itemView.findViewById(R.id.tvMemberPhone);
            btnMarkPresent = itemView.findViewById(R.id.btnMarkPresent);
        }
    }
}
