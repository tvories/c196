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

/**
 * Taylor Vories
 * WGU C196
 * This class is part of the MVVM Lifecycle components.  It is the only class that has
 * direct access to the database.  If this app used a web service, it would also handle that access.
 */

public class AppRepository {
    private static AppRepository ourInstance;
    // LiveData lists of the models to observe
    public LiveData<List<Term>> mTerms;
    public LiveData<List<Course>> mCourses;
    public LiveData<List<Assessment>> mAssessments;
    public LiveData<List<Mentor>> mMentors;

    private AppDatabase mDb;
    // To execute database queries off the main thread
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
        mCourses = getAllCourses();
        mAssessments = getAllAssessments();
        mMentors = getAllMentors();
    }

    /**
     * Adds sample data to the app to make it easier to troubleshoot and visualize
     */
    public void addSampleData() {
        executor.execute(() -> mDb.termDao().insertAll(SampleData.getTerms()));
        executor.execute(() -> mDb.courseDao().insertAll(SampleData.getCourses()));
        executor.execute(() -> mDb.assessmentDao().insertAll(SampleData.getAssessments()));
        executor.execute(() -> mDb.mentorDao().insertAll(SampleData.getMentors()));
    }

    public LiveData<List<Term>> getAllTerms() {
        return mDb.termDao().getAll();
    }

    /**
     * Deletes all database data so you can start fresh
     */
    public void deleteAllData() {
        executor.execute(() -> mDb.termDao().deleteAll());
        executor.execute(() -> mDb.courseDao().deleteAll());
        executor.execute(() -> mDb.assessmentDao().deleteAll());
        executor.execute(() -> mDb.mentorDao().deleteAll());
    }

    /**
     * Gets term by unique term ID
     * @param termId ID in database for term
     * @return Term found in database
     */
    public Term getTermById(int termId) {
        return mDb.termDao().getTermById(termId);
    }

    /**
     * Inserts (and overwrites) term in db
     * @param term Term to insert.
     */
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

    public LiveData<List<Course>> getCoursesByTerm(final int termId) {
        return mDb.courseDao().getCoursesByTerm(termId);
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

    public LiveData<List<Assessment>> getAssessmentsByCourse(final int courseId) {
        return mDb.assessmentDao().getAssessmentsByCourse(courseId);
    }

    public LiveData<List<Mentor>> getMentorsByCourse(final int courseId) {
        return mDb.mentorDao().getMentorsByCourse(courseId);
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
