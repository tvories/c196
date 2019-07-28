package com.taylorvories.c196.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.R;
import com.taylorvories.c196.models.Assessment;
import com.taylorvories.c196.models.Mentor;

import java.util.List;

public class MentorPopupAdapter extends RecyclerView.Adapter<MentorPopupAdapter.MentorViewHolder> {
    private List<Mentor> mMentors;
    private MentorPopupAdapter.MentorSelectedListener mentorSelectedListener;

    public MentorPopupAdapter(List<Mentor> mMentors) {
        super();
        this.mMentors = mMentors;
    }

    public void setMentorSelectedListener(MentorPopupAdapter.MentorSelectedListener mentorSelectedListener) {
        this.mentorSelectedListener = mentorSelectedListener;
    }

    @NonNull
    @Override
    public MentorPopupAdapter.MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MentorPopupAdapter.MentorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mentor_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MentorPopupAdapter.MentorViewHolder holder, final int position) {
        final Mentor mentor = mMentors.get(position);
        holder.tvMentorName.setText(mentor.getName());
        holder.itemView.setOnClickListener(view -> {
            if(mentorSelectedListener != null) {
                mentorSelectedListener.onMentorSelected(position, mentor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMentors.size();
    }

    static class MentorViewHolder extends RecyclerView.ViewHolder {
        TextView tvMentorName;
        ImageView ivIcon;

        public MentorViewHolder(View itemView) {
            super(itemView);
            tvMentorName = itemView.findViewById(R.id.tv_mentor_name);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }

    public interface MentorSelectedListener {
        void onMentorSelected(int position, Mentor mentor);
    }
}
