package com.taylorvories.c196;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.taylorvories.c196.models.CourseStatus;
import com.taylorvories.c196.models.Term;
import com.taylorvories.c196.utilities.TextFormatting;
import com.taylorvories.c196.viewmodel.EditorViewModel;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taylorvories.c196.utilities.Constants.COURSE_ID_KEY;
import static com.taylorvories.c196.utilities.Constants.EDITING_KEY;
import static com.taylorvories.c196.utilities.Constants.TERM_ID_KEY;

public class CourseEditActivity extends AppCompatActivity {
    @BindView(R.id.course_edit_title)
    EditText tvCourseTitle;

    @BindView(R.id.course_edit_start)
    EditText tvCourseStartDate;

    @BindView(R.id.course_edit_end)
    EditText tvCourseEndDate;

    @BindView(R.id.course_edit_start_btn)
    ImageButton btnStartDate;

    @BindView(R.id.course_edit_end_btn)
    ImageButton btnEndDate;

    @BindView(R.id.course_edit_status_dropdown)
    Spinner spCourseStatus;

    @BindView(R.id.course_edit_note)
    EditText tvNote;

    private EditorViewModel mViewModel;
    private boolean mNewCourse, mEditing;
    private int termId = -1;
    private ArrayAdapter<CourseStatus> courseStatusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }
        initViewModel();
        // Set up spinner object
        addSpinnerItems();
    }

    private void addSpinnerItems() {
        courseStatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CourseStatus.values());
        spCourseStatus.setAdapter(courseStatusAdapter);
    }

    private CourseStatus getSpinnerValue() {
        return (CourseStatus) spCourseStatus.getSelectedItem();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveCourse.observe(this, course -> {
            if(course != null && !mEditing) {
                tvCourseTitle.setText(course.getTitle());
                tvCourseStartDate.setText(TextFormatting.fullDateFormat.format(course.getStartDate()));
                tvCourseEndDate.setText(TextFormatting.fullDateFormat.format(course.getAnticipatedEndDate()));
                tvNote.setText(course.getNote());
                int position = getSpinnerPosition(course.getCourseStatus());
                spCourseStatus.setSelection(position);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            setTitle(getString(R.string.new_course));
            mNewCourse = true;
        } else if (extras.containsKey(TERM_ID_KEY)){ // Check if this is adding a course to a term
            termId = extras.getInt(TERM_ID_KEY);
            Log.v("DEBUG", "Extras term ID: " + termId);
            setTitle(getString(R.string.new_course));
        } else {
            setTitle(getString(R.string.edit_course));
            int courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadCourseData(courseId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewCourse) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private int getSpinnerPosition(CourseStatus courseStatus) {
        return courseStatusAdapter.getPosition(courseStatus);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if(item.getItemId() == R.id.action_delete) {
            mViewModel.deleteCourse();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    public void saveAndReturn() {
        try {
            Date startDate = TextFormatting.fullDateFormat.parse(tvCourseStartDate.getText().toString());
            Date endDate = TextFormatting.fullDateFormat.parse(tvCourseEndDate.getText().toString());
            mViewModel.saveCourse(tvCourseTitle.getText().toString(), startDate, endDate, getSpinnerValue(), termId, tvNote.getText().toString());
            Log.v("Saved Course", tvCourseTitle.toString());
        } catch (ParseException e) {
            Log.v("Exception", e.getLocalizedMessage());
        }
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.course_edit_start_btn)
    public void courseStartDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            tvCourseStartDate.setText(TextFormatting.fullDateFormat.format(myCalendar.getTime()));
        };
        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.course_edit_end_btn)
    public void courseEndDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            tvCourseEndDate.setText(TextFormatting.fullDateFormat.format(myCalendar.getTime()));
        };
        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
