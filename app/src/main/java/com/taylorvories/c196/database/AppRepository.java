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
    private TermDao termDao;
    private CourseDao courseDao;
    public LiveData<List<Term>> mTerms;

    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mTerms = getAllTerms();
        termDao = mDb.termDao();
        courseDao = mDb.courseDao();
    }

    public void addSampleData() {
        executor.execute(() -> mDb.termDao().insertAll(SampleData.getTerms()));
        executor.execute(() -> mDb.courseDao().insertAll(SampleData.getCourses()));
    }

    public LiveData<List<Term>> getAllTerms() {
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
        executor.execute(() -> mDb.termDao().insertTerm(term));
    }

    public void deleteTerm(final Term term) {
        executor.execute(() -> mDb.termDao().deleteTerm(term));
    }
}
