package com.taylorvories.c196.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.R;
import com.taylorvories.c196.models.Assessment;

import java.util.List;

public class AssessmentDropdownMenu extends PopupWindow {
    private Context mContext;
    private List<Assessment> mAssessments;
    private RecyclerView rvPopup;
    private AssessmentPopupAdapter assessmentAdapter;

    public AssessmentDropdownMenu(Context mContext, List<Assessment> mAssessments) {
        super(mContext);
        this.mContext = mContext;
        this.mAssessments = mAssessments;
        setupView();
    }

    public void setAssessmentSelectedListener(AssessmentPopupAdapter.AssessmentSelectedListener assessmentSelectedListener) {
        assessmentAdapter.setAssessmentSelectedListener(assessmentSelectedListener);
    }

    private void setupView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_menu, null);

        rvPopup = view.findViewById(R.id.rv_popup);
        rvPopup.setHasFixedSize(true);
        rvPopup.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvPopup.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        assessmentAdapter = new AssessmentPopupAdapter(mAssessments);
        rvPopup.setAdapter(assessmentAdapter);

        setContentView(view);
    }
}
