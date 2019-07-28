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

import java.util.List;

public class AssessmentPopupAdapter extends RecyclerView.Adapter<AssessmentPopupAdapter.AssessmentViewHolder> {
    private List<Assessment> mAssessments;
    private AssessmentSelectedListener assessmentSelectedListener;

    public AssessmentPopupAdapter(List<Assessment> mAssessments) {
        super();
        this.mAssessments = mAssessments;
    }

    public void setAssessmentSelectedListener(AssessmentPopupAdapter.AssessmentSelectedListener assessmentSelectedListener) {
        this.assessmentSelectedListener = assessmentSelectedListener;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssessmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, final int position) {
        final Assessment assessment = mAssessments.get(position);
        holder.tvAssessmentTitle.setText(assessment.getTitle());
        holder.itemView.setOnClickListener(view -> {
            if(assessmentSelectedListener != null) {
                assessmentSelectedListener.onAssessmentSelected(position, assessment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    static class AssessmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssessmentTitle;
        ImageView ivIcon;

        public AssessmentViewHolder(View itemView) {
            super(itemView);
            tvAssessmentTitle = itemView.findViewById(R.id.tv_assessment_title);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }

    public interface AssessmentSelectedListener {
        void onAssessmentSelected(int position, Assessment assessment);
    }
}
