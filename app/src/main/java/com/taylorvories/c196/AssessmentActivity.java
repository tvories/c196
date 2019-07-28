package com.taylorvories.c196;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.models.Assessment;
import com.taylorvories.c196.ui.AssessmentAdapter;
import com.taylorvories.c196.ui.RecyclerContext;
import com.taylorvories.c196.viewmodel.AssessmentViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssessmentActivity extends AppCompatActivity implements AssessmentAdapter.AssessmentSelectedListener {
    @BindView(R.id.ass_recycler_view)
    RecyclerView mAssessmentRecyclerView;

    @OnClick(R.id.ass_fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, AssessmentEditActivity.class);
        startActivity(intent);
    }

    private List<Assessment> assessmentData = new ArrayList<>();
    private AssessmentAdapter mAssessmentAdapter;
    private AssessmentViewModel mAssessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_main);
        Toolbar toolbar = findViewById(R.id.ass_toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {
        mAssessmentRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAssessmentRecyclerView.setLayoutManager(layoutManager);
    }

    private void initViewModel() {
        final Observer<List<Assessment>> assessmentObserver =
            assessmentEntities -> {
                assessmentData.clear();
                assessmentData.addAll(assessmentEntities);

                if(mAssessmentAdapter == null) {
                    mAssessmentAdapter = new AssessmentAdapter(assessmentData, AssessmentActivity.this, RecyclerContext.MAIN, this);
                    mAssessmentRecyclerView.setAdapter(mAssessmentAdapter);
                } else {
                    mAssessmentAdapter.notifyDataSetChanged();
                }
            };
        mAssessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        mAssessmentViewModel.mAssessments.observe(this, assessmentObserver);
    }

    @Override
    public void onAssessmentSelected(int position, Assessment assessment) {

    }
}
