package com.taylorvories.c196;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.taylorvories.c196.models.Assessment;
import com.taylorvories.c196.models.Course;
import com.taylorvories.c196.models.Mentor;
import com.taylorvories.c196.ui.AssessmentAdapter;
import com.taylorvories.c196.ui.AssessmentDropdownMenu;
import com.taylorvories.c196.ui.CourseAdapter;
import com.taylorvories.c196.ui.CourseDropdownMenu;
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
import static com.taylorvories.c196.utilities.Constants.TERM_ID_KEY;

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

    @BindView(R.id.fab_add_assessment)
    FloatingActionButton fabAddAssessment;

    @BindView(R.id.fab_add_mentor)
    FloatingActionButton fabAddMentor;

    private List<Assessment> assessmentData = new ArrayList<>();
    private List<Mentor> mentorData = new ArrayList<>();
    private List<Assessment> unassignedAssessments = new ArrayList<>();
    private List<Mentor> unassignedMentors = new ArrayList<>();
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

        // Load and observe unassigned assessments to enable adding them
        final Observer<List<Assessment>> unassignedAssessmentObserver =
            assessmentEntities -> {
                unassignedAssessments.clear();
                unassignedAssessments.addAll(assessmentEntities);
            };

        // Load and observe unassigned mentors to enable adding them
        final Observer<List<Mentor>> unassignedMentorObserver =
            mentorEntities -> {
                unassignedMentors.clear();
                unassignedMentors.addAll(mentorEntities);
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
        mViewModel.getUnassignedAssessments().observe(this, unassignedAssessmentObserver);
        mViewModel.getUnassignedMentors().observe(this, unassignedMentorObserver);
    }

    @OnClick(R.id.fab_edit_course)
    public void openEditActivity() {
        Intent intent = new Intent(this, CourseEditActivity.class);
        intent.putExtra(COURSE_ID_KEY, courseId);
        this.startActivity(intent);
        finish();
    }

    @OnClick(R.id.fab_add_assessment)
    public void assessmentAddButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new or existing Assessment?");
        builder.setMessage("Would you like to add an existing assessment to this course or create a new assessment?");
        builder.setIcon(R.drawable.ic_add);
        builder.setPositiveButton("New", (dialog, id) -> {
            dialog.dismiss();
            Intent intent = new Intent(this, AssessmentEditActivity.class);
            intent.putExtra(COURSE_ID_KEY, courseId);
            this.startActivity(intent);
        });
        builder.setNegativeButton("Existing", (dialog, id) -> {
            // Ensure at least once unassigned assessment is available
            if(unassignedAssessments.size() >= 1) {
                final AssessmentDropdownMenu menu = new AssessmentDropdownMenu(this, unassignedAssessments);
                menu.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                menu.setWidth(getPxFromDp(200));
                menu.setOutsideTouchable(true);
                menu.setFocusable(true);
                menu.showAsDropDown(fabAddAssessment);
                menu.setAssessmentSelectedListener((position, assessment) -> {
                    menu.dismiss();
                    assessment.setCourseId(courseId);
                    mViewModel.overwriteAssessment(assessment, courseId);
                });
            } else { // No unassigned courses.  Notify user.
                Toast.makeText(getApplicationContext(), "There are no unassigned assessments.  Create a new assessment.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getPxFromDp(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
