package com.taylorvories.c196.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import androidx.lifecycle.LiveData;

import com.taylorvories.c196.models.Assessment;
import com.taylorvories.c196.models.Course;
import com.taylorvories.c196.models.Mentor;
import com.taylorvories.c196.models.Term;
import com.taylorvories.c196.utilities.SampleData;

public class AppRepository {
    private static AppRepository ourInstance;
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
    }

    public void addSampleData() {
        executor.execute(() -> mDb.termDao().insertAll(SampleData.getTerms()));
        executor.execute(() -> mDb.courseDao().insertAll(SampleData.getCourses()));
        executor.execute(() -> mDb.assessmentDao().insertAll(SampleData.getAssessments()));
        executor.execute(() -> mDb.mentorDao().insertAll(SampleData.getMentors()));
    }

    public LiveData<List<Term>> getAllTerms() {
        return mDb.termDao().getAll();
    }

    public void deleteAllData() {
        executor.execute(() -> mDb.termDao().deleteAll());
        executor.execute(() -> mDb.courseDao().deleteAll());
        executor.execute(() -> mDb.assessmentDao().deleteAll());
        executor.execute(() -> mDb.mentorDao().deleteAll());
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

    // Course methods
    public LiveData<List<Course>> getAllCourses() {
        return mDb.courseDao().getAll();
    }

    public Course getCourseById(int courseId) {
        return mDb.courseDao().getCourseById(courseId);
    }

    public void insertCourse(final Course course) {
        executor.execute(() -> mDb.courseDao().insertCourse(course));
    }

    public void deleteCourse(final Course course) {
        executor.execute(() -> mDb.courseDao().deleteCourse(course));
    }

    // Assessment methods
    public LiveData<List<Assessment>> getAllAssessments() {
        return mDb.assessmentDao().getAll();
    }

    public Assessment getAssessmentById(int assessmentId) {
        return mDb.assessmentDao().getAssessmentById(assessmentId);
    }

    public void insertAssessment(final Assessment assessment) {
        executor.execute(() -> mDb.assessmentDao().insertAssessment(assessment));
    }

    public void deleteAssessment(final Assessment assessment) {
        executor.execute(() -> mDb.assessmentDao().deleteAssessment(assessment));
    }

    // Mentor methods
    public LiveData<List<Mentor>> getAllMentors() {
        return mDb.mentorDao().getAll();
    }

    public Mentor getMentorById(int mentorId) {
        return mDb.mentorDao().getMentorById(mentorId);
    }

    public void insertMentor(final Mentor mentor) {
        executor.execute(() -> mDb.mentorDao().insertMentor(mentor));
    }

    public void deleteMentor(final Mentor mentor) {
        executor.execute(() -> mDb.mentorDao().deleteMentor(mentor));
    }
}
