package com.taylorvories.c196.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import androidx.lifecycle.LiveData;

import com.taylorvories.c196.models.Term;
import com.taylorvories.c196.utilities.SampleData;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<Term>> mTerms;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInsance(Context context) {
        if(ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mTerms = getAllTerms();
    }

    public void addSampleData() {
        executor.execute(() -> mDb.termDao().insertAll(SampleData.getTerms()));
        executor.execute(() -> mDb.courseDao().insertAll(SampleData.getCourses()));
    }

    private LiveData<List<Term>> getAllTerms() {
        return mDb.termDao().getAll();
    }

    public void deleteAllData() {
        executor.execute(() -> mDb.termDao().deleteAll());
        executor.execute(() -> mDb.courseDao().deleteAll());
    }

    public Term getTermById(int termId) {
        return mDb.termDao().getTermById(termId);
    }

    public void insertTerm(final Term term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().insertTerm(term);
            }
        });
    }

    public void deleteTerm(final Term term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().deleteTerm(term);
            }
        });
    }
}
