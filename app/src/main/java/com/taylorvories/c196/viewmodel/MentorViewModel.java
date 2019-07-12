package com.taylorvories.c196.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.taylorvories.c196.database.AppRepository;
import com.taylorvories.c196.models.Mentor;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {
    public LiveData<List<Mentor>> mMentors;
    private AppRepository mRepository;

    public MentorViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mMentors = mRepository.mMentors;
    }
}
