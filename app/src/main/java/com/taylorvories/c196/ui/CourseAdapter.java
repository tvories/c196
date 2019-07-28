package com.taylorvories.c196.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.taylorvories.c196.CourseDetailsActivity;
import com.taylorvories.c196.CourseEditActivity;
import com.taylorvories.c196.R;
import com.taylorvories.c196.models.Course;
import com.taylorvories.c196.utilities.TextFormatting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taylorvories.c196.utilities.Constants.COURSE_ID_KEY;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final List<Course> mCourses;
    private final Context mContext;
    private final RecyclerContext rContext;
    private CourseSelectedListener courseSelectedListener;

    public CourseAdapter(List<Course> mCourses, Context mContext, RecyclerContext rContext, CourseSelectedListener courseSelectedListener) {
        this.mCourses = mCourses;
        this.mContext = mContext;
        this.rContext = rContext;
        this.courseSelectedListener = courseSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_cardview, parent, false);
        return new ViewHolder(view, courseSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        final Course course = mCourses.get(position);
        holder.tvTitle.setText(course.getTitle());
        String startAndEnd = TextFormatting.cardDateFormat.format(course.getStartDate()) + " to " + TextFormatting.cardDateFormat.format(course.getAnticipatedEndDate());
        holder.tvDates.setText(startAndEnd);

        switch(rContext) {
            case MAIN:
                Log.v("rContext", "rContext is " + rContext.name());
                holder.courseFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_edit));
                holder.courseImageBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, CourseDetailsActivity.class);
                    intent.putExtra(COURSE_ID_KEY, course.getId());
                    mContext.startActivity(intent);
                });

                holder.courseFab.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, CourseEditActivity.class);
                    intent.putExtra(COURSE_ID_KEY, course.getId());
                    mContext.startActivity(intent);
                });
                break;
            case CHILD:
                holder.courseFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_delete));
                holder.courseFab.setOnClickListener(v -> {
                    if(courseSelectedListener != null){
                        courseSelectedListener.onCourseSelected(position, course);
                    }
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                    builder.setTitle("Are you sure you want to remove this course?");
//                    builder.setMessage("This will not delete the course, only remove it from this term.");
//                    builder.setIcon(android.R.drawable.ic_dialog_alert);
//                    builder.setPositiveButton("Continue", (dialog, id) -> {
//                        dialog.dismiss();
//                        course.setTermId(-1);
//                        mCourses.remove(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, mCourses.size());
//                    });
//                    builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.card_course_title)
        TextView tvTitle;
        @BindView(R.id.card_course_fab)
        FloatingActionButton courseFab;
        @BindView(R.id.card_course_dates)
        TextView tvDates;
        @BindView(R.id.btn_course_details)
        ImageButton courseImageBtn;
        CourseSelectedListener courseSelectedListener;

        public ViewHolder(View itemView, CourseSelectedListener courseSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.courseSelectedListener = courseSelectedListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            courseSelectedListener.onCourseSelected(getAdapterPosition(), mCourses.get(getAdapterPosition()));
        }
    }

    public interface CourseSelectedListener {
        void onCourseSelected(int position, Course course);
    }
}
