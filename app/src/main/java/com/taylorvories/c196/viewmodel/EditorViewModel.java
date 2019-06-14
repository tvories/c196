package com.taylorvories.c196.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.taylorvories.c196.database.AppRepository;
import com.taylorvories.c196.models.Term;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {
    public MutableLiveData<Term> mLiveTerm = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInsance(getApplication());
    }

    public void loadData(final int termId) {
        executor.execute(() -> {
            Term term = mRepository.getTermById(termId);
            mLiveTerm.postValue(term);
        });
    }

    public void saveTerm(String termTitle) {
        Term term = mLiveTerm.getValue();

        if (term == null) {
            if (TextUtils.isEmpty(termTitle.trim())) {
                return;
            }
            term = new Term(termTitle.trim(), new Date(), new Date());
        } else {
            term.setTitle(termTitle.trim());
        }
        mRepository.insertTerm(term);
    }

    public void deleteTerm() {
        mRepository.deleteTerm(mLiveTerm.getValue());
    }
}
