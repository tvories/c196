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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.taylorvories.c196.models.Course;
import com.taylorvories.c196.ui.CourseAdapter;
import com.taylorvories.c196.ui.CourseDropdownMenu;
import com.taylorvories.c196.ui.CoursePopupAdapter;
import com.taylorvories.c196.ui.RecyclerContext;
import com.taylorvories.c196.utilities.TextFormatting;
import com.taylorvories.c196.viewmodel.EditorViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taylorvories.c196.utilities.Constants.TERM_ID_KEY;

public class TermDetailsActivity extends AppCompatActivity implements CourseAdapter.CourseSelectedListener {
    @BindView(R.id.term_detail_start)
    TextView tvTermStartDate;

    @BindView(R.id.term_detail_end)
    TextView tvTermEndDate;

    @BindView(R.id.rview_term_detail_course)
    RecyclerView mCourseRecyclerView;

    @BindView(R.id.fab_add_course)
    FloatingActionButton fabAddCourse;

    private List<Course> courseData = new ArrayList<>();
    private List<Course> unassignedCourses = new ArrayList<>();
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
                    mCourseAdapter = new CourseAdapter(courseData, TermDetailsActivity.this, RecyclerContext.CHILD, this);
                    mCourseRecyclerView.setAdapter(mCourseAdapter);
                } else {
                    mCourseAdapter.notifyDataSetChanged();
                }
            };

        // Load and observe unassigned courses to enable adding them
        final Observer<List<Course>> unassignedCourseObserver =
            courseEntities -> {
                unassignedCourses.clear();
                unassignedCourses.addAll(courseEntities);
            };

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadTermData(termId);
        } else {
            finish();
        }

        mViewModel.getCoursesInTerm(termId).observe(this, courseObserver);
        mViewModel.getUnassignedCourses().observe(this, unassignedCourseObserver);
    }

    @OnClick(R.id.fab_edit_term)
    public void openEditActivity() {
        Intent intent = new Intent(this, TermEditActivity.class);
        intent.putExtra(TERM_ID_KEY, termId);
        this.startActivity(intent);
        finish();
    }

    @OnClick(R.id.fab_add_course)
    public void courseAddButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new or existing course?");
        builder.setMessage("Would you like to add an existing course to this term or create a new course?");
        builder.setIcon(R.drawable.ic_add);
        builder.setPositiveButton("New", (dialog, id) -> {
            dialog.dismiss();
            Intent intent = new Intent(this, CourseEditActivity.class);
            intent.putExtra(TERM_ID_KEY, termId);
            this.startActivity(intent);
        });
        builder.setNegativeButton("Existing", (dialog, id) -> {
            // Ensure at least once unassigned course is available
            if(unassignedCourses.size() >= 1) {
                final CourseDropdownMenu menu = new CourseDropdownMenu(this, unassignedCourses);
                menu.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                menu.setWidth(getPxFromDp(200));
                menu.setOutsideTouchable(true);
                menu.setFocusable(true);
                menu.showAsDropDown(fabAddCourse);
                menu.setCourseSelectedListener((position, course) -> {
                    menu.dismiss();
                    course.setTermId(termId);
                    mViewModel.overwriteCourse(course, termId);
                });
            } else { // No unassigned courses.  Notify user.
                Toast.makeText(getApplicationContext(), "There are no unassigned courses.  Create a new course.", Toast.LENGTH_SHORT).show();
            }

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getPxFromDp(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCourseSelected(int position, Course course) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to remove this course?");
        builder.setMessage("This will not delete the course, only remove it from this term.");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Continue", (dialog, id) -> {
            dialog.dismiss();
            mViewModel.overwriteCourse(course, -1);
            mCourseAdapter.notifyDataSetChanged();
//                mCourses.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, mCourses.size());
        });

        mViewModel.overwriteCourse(course, -1);
        mCourseAdapter.notifyDataSetChanged();
    }
}
