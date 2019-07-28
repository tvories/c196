package com.taylorvories.c196;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.taylorvories.c196.models.AssessmentType;
import com.taylorvories.c196.utilities.TextFormatting;
import com.taylorvories.c196.viewmodel.EditorViewModel;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taylorvories.c196.utilities.Constants.ASSESSMENT_ID_KEY;
import static com.taylorvories.c196.utilities.Constants.COURSE_ID_KEY;
import static com.taylorvories.c196.utilities.Constants.EDITING_KEY;

public class AssessmentEditActivity extends AppCompatActivity {
    @BindView(R.id.ass_edit_title)
    EditText tvAssessmentTitle;

    @BindView(R.id.ass_edit_date)
    EditText tvAssessmentDate;

    @BindView(R.id.ass_edit_type_dropdown)
    Spinner spAssessmentType;

    private EditorViewModel mViewModel;
    private boolean mNewAssessment, mEditing;
    private int courseId = -1;
    private ArrayAdapter<AssessmentType> assessmentTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assessment_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();
        addSpinnerItems();
    }

    private void addSpinnerItems() {
        assessmentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AssessmentType.values());
        spAssessmentType.setAdapter(assessmentTypeAdapter);
    }

    private AssessmentType getSpinnerValue() {
        return (AssessmentType) spAssessmentType.getSelectedItem();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveAssessment.observe(this, assessment -> {
            if(assessment != null && !mEditing) {
                tvAssessmentTitle.setText(assessment.getTitle());
                tvAssessmentDate.setText(TextFormatting.fullDateFormat.format(assessment.getDate()));
                int position = getSpinnerPosition(assessment.getAssessmentType());
                spAssessmentType.setSelection(position);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            setTitle(getString(R.string.new_assessment));
            mNewAssessment = true;
        } else if (extras.containsKey(COURSE_ID_KEY)) {
            courseId = extras.getInt(COURSE_ID_KEY);
            Log.v("DEBUG", "Extras course ID: " + courseId);
            setTitle(getString(R.string.new_assessment));
        } else {
            setTitle(R.string.edit_assessment);
            int assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
            mViewModel.loadAssessmentData(assessmentId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewAssessment) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private int getSpinnerPosition(AssessmentType assessmentType) {
        return assessmentTypeAdapter.getPosition(assessmentType);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if(item.getItemId() == R.id.action_delete) {
            mViewModel.deleteAssessment();
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
            Date date = TextFormatting.fullDateFormat.parse(tvAssessmentDate.getText().toString());
            mViewModel.saveAssessment(tvAssessmentTitle.getText().toString(), date, getSpinnerValue(), courseId);
            Log.v("Saved Assessment", tvAssessmentTitle.toString());
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

    @OnClick(R.id.ass_edit_date_btn)
    public void assessmentDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            tvAssessmentDate.setText(TextFormatting.fullDateFormat.format(myCalendar.getTime()));
        };
        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
