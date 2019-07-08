package com.taylorvories.c196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.models.Course;
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
    RecyclerView courseListView;

    private List<Course> courseList = new ArrayList<>();
    private Toolbar toolbar;
    private int termId;
    private ArrayAdapter<String> courseListAdapter;

    private EditorViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_term_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        ArrayList<String> tempList = new ArrayList<>();
        tempList.add("String 1");
        tempList.add("String 2");
        courseListAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, tempList);
        courseListView.setAdapter(courseListAdapter);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveTerm.observe(this, term -> {
            tvTermStartDate.setText(TextFormatting.fullDateFormat.format(term.getStartDate()));
            tvTermEndDate.setText(TextFormatting.fullDateFormat.format(term.getEndDate()));
            toolbar.setTitle(term.getTitle());
        });

        // courses
        final Observer<List<Course>> courseObserver =
                courseEntities -> {
                    courseList.clear();
                    courseList.addAll(courseEntities);
                    loadCourses();
                };

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termId);
        } else {
            finish();
        }

        mViewModel.getCoursesInTerm(termId).observe(this, courseObserver);
    }

    private void loadCourses() {
        courseListAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab_edit_term)
    public void openEditActivity() {
        Intent intent = new Intent(this, TermEditActivity.class);
        intent.putExtra(TERM_ID_KEY, termId);
        this.startActivity(intent);
        finish();
    }
}
