package com.taylorvories.c196;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.models.Assessment;
import com.taylorvories.c196.models.Course;
import com.taylorvories.c196.models.Mentor;
import com.taylorvories.c196.ui.AssessmentAdapter;
import com.taylorvories.c196.ui.CourseAdapter;
import com.taylorvories.c196.ui.MentorAdapter;
import com.taylorvories.c196.ui.RecyclerContext;
import com.taylorvories.c196.utilities.TextFormatting;
import com.taylorvories.c196.viewmodel.EditorViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taylorvories.c196.utilities.Constants.COURSE_ID_KEY;

public class CourseDetailsActivity extends AppCompatActivity {
    @BindView(R.id.course_detail_start)
    TextView tvCourseStartDate;

    @BindView(R.id.course_detail_end)
    TextView tvCourseEndDate;

    @BindView(R.id.rview_course_detail_assessments)
    RecyclerView mAssRecyclerView;

    @BindView(R.id.rview_course_detail_mentors)
    RecyclerView mMentorsRecyclerView;

    @BindView(R.id.course_detail_status)
    TextView tvCourseStatus;

    @BindView(R.id.course_detail_note)
    TextView tvCourseNote;

    private List<Assessment> assessmentData = new ArrayList<>();
    private List<Mentor> mentorData = new ArrayList<>();
    private Toolbar toolbar;
    private int courseId;
    private AssessmentAdapter mAssessmentAdapter;
    private MentorAdapter mMentorAdapter;
    private EditorViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {
        mAssRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAssRecyclerView.setLayoutManager(layoutManager);

        mMentorsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        mMentorsRecyclerView.setLayoutManager(layoutManager1);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveCourse.observe(this, course -> {
            tvCourseStartDate.setText(TextFormatting.fullDateFormat.format(course.getStartDate()));
            tvCourseEndDate.setText(TextFormatting.fullDateFormat.format(course.getAnticipatedEndDate()));
            tvCourseStatus.setText(course.getCourseStatus().toString());
            tvCourseNote.setText(course.getNote());
            toolbar.setTitle(course.getTitle());
        });

        // Assessments
        final Observer<List<Assessment>> assessmentObserver =
            assessmentEntities -> {
                assessmentData.clear();
                assessmentData.addAll(assessmentEntities);

                if(mAssessmentAdapter == null) {
                    mAssessmentAdapter = new AssessmentAdapter(assessmentData, CourseDetailsActivity.this, RecyclerContext.CHILD);
                    mAssRecyclerView.setAdapter(mAssessmentAdapter);
                } else {
                    mAssessmentAdapter.notifyDataSetChanged();
                }
            };

        // Mentors
        final Observer<List<Mentor>> mentorObserver =
            mentorEntities -> {
                mentorData.clear();
                mentorData.addAll(mentorEntities);

                if(mMentorAdapter == null) {
                    mMentorAdapter = new MentorAdapter(mentorData, CourseDetailsActivity.this, RecyclerContext.CHILD);
                    mMentorsRecyclerView.setAdapter(mMentorAdapter);
                } else {
                    mMentorAdapter.notifyDataSetChanged();
                }
            };

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadCourseData(courseId);
        } else {
            finish();
        }

        mViewModel.getAssessmentsInCourse(courseId).observe(this, assessmentObserver);
        mViewModel.getMentorsInCourse(courseId).observe(this, mentorObserver);
    }

    @OnClick(R.id.fab_edit_course)
    public void openEditActivity() {
        Intent intent = new Intent(this, CourseEditActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        this.startActivity(intent);
        finish();
    }
}
