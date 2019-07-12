package com.taylorvories.c196;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.taylorvories.c196.viewmodel.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taylorvories.c196.utilities.Constants.MENTOR_ID_KEY;

public class MentorDetailsActivity extends AppCompatActivity {
    @BindView(R.id.mentor_detail_email)
    TextView tvMentorEmail;

    @BindView(R.id.mentor_detail_phone)
    TextView tvMentorPhone;

    private Toolbar toolbar;
    private int mentorId;
    private EditorViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveMentor.observe(this, mentor -> {
            tvMentorEmail.setText(mentor.getEmail());
            tvMentorPhone.setText(mentor.getPhone());
            toolbar.setTitle(mentor.getName());
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            mentorId = extras.getInt(MENTOR_ID_KEY);
            mViewModel.loadMentorData(mentorId);
        } else {
            finish();
        }
    }

    @OnClick(R.id.fab_edit_mentor)
    public void openEditActivity() {
        Intent intent = new Intent(this, MentorEditActivity.class);
        intent.putExtra(MENTOR_ID_KEY, mentorId);
        this.startActivity(intent);
        finish();
    }
}
