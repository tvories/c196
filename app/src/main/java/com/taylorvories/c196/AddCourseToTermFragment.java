package com.taylorvories.c196;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.viewmodel.EditorViewModel;

import butterknife.ButterKnife;

public class AddCourseToTermFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private EditorViewModel mViewModel;

    public AddCourseToTermFragment() {

    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_add_course_to_term, container, false);

        // RecyclerView reference
        mRecyclerView = rootView.findViewById(R.id.course_cardview)
    }
}
