package com.taylorvories.c196;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.models.Assessment;
import com.taylorvories.c196.models.Course;
import com.taylorvories.c196.ui.CourseAdapter;
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

    @BindView(R.id.course_detail_status)
    TextView tvCourseStatus;

    private List<Assessment> assessmentList = new ArrayList<>();
    private List<Course> courseData = new ArrayList<>();
    private Toolbar toolbar;
    private int courseId;
    private CourseAdapter mCourseAdapter;
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
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveCourse.observe(this, course -> {
            tvCourseStartDate.setText(TextFormatting.fullDateFormat.format(course.getStartDate()));
            tvCourseEndDate.setText(TextFormatting.fullDateFormat.format(course.getAnticipatedEndDate()));
            tvCourseStatus.setText(course.getCourseStatus().toString());
            toolbar.setTitle(course.getTitle());
        });

        // Assessments
        //TODO: Write assessment piece

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadCourseData(courseId);
        } else {
            finish();
        }

        //TODO: Add assessment observer
    }

    @OnClick(R.id.fab_edit_course)
    public void openEditActivity() {
        Intent intent = new Intent(this, CourseEditActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        this.startActivity(intent);
        finish();
    }
}
