package com.taylorvories.c196;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.models.Course;
import com.taylorvories.c196.models.Term;
import com.taylorvories.c196.ui.CourseAdapter;
import com.taylorvories.c196.ui.RecyclerContext;
import com.taylorvories.c196.ui.TermAdapter;
import com.taylorvories.c196.utilities.TextFormatting;
import com.taylorvories.c196.viewmodel.EditorViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taylorvories.c196.utilities.Constants.TERM_ID_KEY;

public class TermDetailsActivity extends AppCompatActivity {
    @BindView(R.id.term_detail_start)
    TextView tvTermStartDate;

    @BindView(R.id.term_detail_end)
    TextView tvTermEndDate;

    @BindView(R.id.rview_term_detail_course)
    RecyclerView mCourseRecyclerView;

    private List<Course> courseData = new ArrayList<>();
    private Toolbar toolbar;
    private int termId;
    private CourseAdapter mCourseAdapter;
    private EditorViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        toolbar = findViewById(R.id.toolbar);
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
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveTerm.observe(this, term -> {
            tvTermStartDate.setText(TextFormatting.fullDateFormat.format(term.getStartDate()));
            tvTermEndDate.setText(TextFormatting.fullDateFormat.format(term.getEndDate()));
            toolbar.setTitle(term.getTitle());
        });

        // load and observe courses
        final Observer<List<Course>> courseObserver =
            courseEntities -> {
                courseData.clear();
                courseData.addAll(courseEntities);

                if(mCourseAdapter == null) {
                    mCourseAdapter = new CourseAdapter(courseData, TermDetailsActivity.this, RecyclerContext.CHILD);
                    mCourseRecyclerView.setAdapter(mCourseAdapter);
                } else {
                    mCourseAdapter.notifyDataSetChanged();
                }
            };

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadTermData(termId);
        } else {
            finish();
        }

        mViewModel.getCoursesInTerm(termId).observe(this, courseObserver);
    }

    @OnClick(R.id.fab_edit_term)
    public void openEditActivity() {
        Intent intent = new Intent(this, TermEditActivity.class);
        intent.putExtra(TERM_ID_KEY, termId);
        this.startActivity(intent);
        finish();
    }
}
