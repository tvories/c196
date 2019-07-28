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
import com.taylorvories.c196.models.Mentor;

import java.util.List;

public class MentorDropdownMenu extends PopupWindow {
    private Context mContext;
    private List<Mentor> mMentors;
    private RecyclerView rvPopup;
    private MentorPopupAdapter mentorAdapter;

    public MentorDropdownMenu(Context mContext, List<Mentor> mMentors) {
        super(mContext);
        this.mContext = mContext;
        this.mMentors = mMentors;
        setupView();
    }

    public void setMentorSelectedListener(MentorPopupAdapter.MentorSelectedListener mentorSelectedListener) {
        mentorAdapter.setMentorSelectedListener(mentorSelectedListener);
    }

    private void setupView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_menu, null);

        rvPopup = view.findViewById(R.id.rv_popup);
        rvPopup.setHasFixedSize(true);
        rvPopup.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvPopup.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        mentorAdapter = new MentorPopupAdapter(mMentors);
        rvPopup.setAdapter(mentorAdapter);

        setContentView(view);
    }
}
