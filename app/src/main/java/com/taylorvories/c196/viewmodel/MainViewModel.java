package com.taylorvories.c196.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.taylorvories.c196.database.AppRepository;
import com.taylorvories.c196.database.Term;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<Term>> mTerms;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInsance(application.getApplicationContext());
        mTerms = mRepository.mTerms;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllTerms() {
        mRepository.deleteAllTerms();
    }
}
