package com.taylorvories.c196.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.R;
import com.taylorvories.c196.models.Course;

import java.util.List;

public class ItemDropdownMenu extends PopupWindow {
    private Context mContext;
    private List<Course> mCourses;
    private RecyclerView rvPopup;
    private CoursePopupAdapter courseAdapter;

    public ItemDropdownMenu(Context mContext, List<Course> mCourses) {
        super(mContext);
        this.mContext = mContext;
        this.mCourses = mCourses;
        setupView();
    }

    public void setCourseSelectedListener(CoursePopupAdapter.CourseSelectedListener courseSelectedListener) {
        courseAdapter.setCourseSelectedListener(courseSelectedListener);
    }

    private void setupView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_menu, null);

        rvPopup = view.findViewById(R.id.rv_popup);
        rvPopup.setHasFixedSize(true);
        rvPopup.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvPopup.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        courseAdapter = new CoursePopupAdapter(mCourses);
        rvPopup.setAdapter(courseAdapter);

        setContentView(view);
    }


}
