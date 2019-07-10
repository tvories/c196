package com.taylorvories.c196.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    public CourseAdapter(List<Course> mCourses, Context mContext, RecyclerContext rContext) {
        this.mCourses = mCourses;
        this.mContext = mContext;
        this.rContext = rContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        final Course course = mCourses.get(position);
        holder.tvTitle.setText(course.getTitle());
        String startAndEnd = TextFormatting.cardDateFormat.format(course.getStartDate()) + " to " + TextFormatting.cardDateFormat.format(course.getAnticipatedEndDate());
        holder.tvDates.setText(startAndEnd);

        switch(rContext) {
            case PARENT:
                holder.courseFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_edit));
//                holder.courseImageBtn.setOnClickListener(v -> {
//                    Intent intent = new Intent(mContext, CourseDetailsActivity.class);
//                    intent.putExtra(COURSE_ID_KEY, course.getId());
//                    mContext.startActivity(intent);
//                });

                holder.courseFab.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, CourseEditActivity.class);
                    intent.putExtra(COURSE_ID_KEY, course.getId());
                    mContext.startActivity(intent);
                });
            case CHILD:
                holder.courseFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_delete));
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_course_title)
        TextView tvTitle;
        @BindView(R.id.card_course_fab)
        FloatingActionButton courseFab;
        @BindView(R.id.card_course_dates)
        TextView tvDates;
        @BindView(R.id.btn_course_details)
        ImageButton courseImageBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
