package com.taylorvories.c196;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.models.Course;
import com.taylorvories.c196.ui.CourseAdapter;
import com.taylorvories.c196.ui.RecyclerContext;
import com.taylorvories.c196.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseActivity extends AppCompatActivity {
    @BindView(R.id.course_recycler_view)
    RecyclerView mCourseRecyclerView;

    @OnClick(R.id.course_fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, CourseEditActivity.class);
        startActivity(intent);
    }

    private List<Course> courseData = new ArrayList<>();
    private CourseAdapter mCourseAdapter;
    private CourseViewModel mCourseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);
        Toolbar toolbar = findViewById(R.id.course_toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {
        mCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCourseRecyclerView.setLayoutManager(layoutManager);
    }

    private void initViewModel() {
        final Observer<List<Course>> courseObserver =
            courseEntities -> {
                courseData.clear();
                courseData.addAll(courseEntities);

                if(mCourseAdapter == null) {
                    mCourseAdapter = new CourseAdapter(courseData, CourseActivity.this, RecyclerContext.MAIN, -1);
                    mCourseRecyclerView.setAdapter(mCourseAdapter);
                } else {
                    mCourseAdapter.notifyDataSetChanged();
                }
            };
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mCourseViewModel.mCourses.observe(this, courseObserver);
    }
}
