package com.taylorvories.c196.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.taylorvories.c196.database.AppRepository;
import com.taylorvories.c196.models.Course;
import com.taylorvories.c196.models.CourseStatus;
import com.taylorvories.c196.models.Term;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {
    public MutableLiveData<Term> mLiveTerm = new MutableLiveData<>();
    public MutableLiveData<Course> mLiveCourse = new MutableLiveData<>();
    public LiveData<List<Term>> mTerms;
    public LiveData<List<Course>> mCourses;
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
        mTerms = mRepository.mTerms;
        mCourses = mRepository.mCourses;
    }

    public void loadTermData(final int termId) {
        executor.execute(() -> {
            Term term = mRepository.getTermById(termId);
            mLiveTerm.postValue(term);
        });
    }

    public void loadCourseData(final int courseId) {
        executor.execute(() -> {
            Course course = mRepository.getCourseById(courseId);
            mLiveCourse.postValue(course);
        });
    }

    public void saveTerm(String termTitle, Date startDate, Date endDate) {
        Term term = mLiveTerm.getValue();

        if (term == null) {
            if (TextUtils.isEmpty(termTitle.trim())) {
                return;
            }
            term = new Term(termTitle.trim(), startDate, endDate);
        } else {
            term.setTitle(termTitle.trim());
            term.setStartDate(startDate);
            term.setEndDate(endDate);
        }
        mRepository.insertTerm(term);
    }

    public void saveCourse(String courseTitle, Date startDate, Date endDate, CourseStatus courseStatus) {
        Course course = mLiveCourse.getValue();

        if(course == null) {
            if (TextUtils.isEmpty(courseTitle.trim())) {
                return;
            }
            course = new Course(courseTitle.trim(), startDate, endDate, courseStatus);
        } else {
            course.setTitle(courseTitle.trim());
            course.setStartDate(startDate);
            course.setAnticipatedEndDate(endDate);
            course.setCourseStatus(courseStatus);
        }
        mRepository.insertCourse(course);
    }

    public void deleteTerm() {
        mRepository.deleteTerm(mLiveTerm.getValue());
    }

    public void deleteCourse() {
        mRepository.deleteCourse(mLiveCourse.getValue());
    }

    public LiveData<List<Course>> getCoursesInTerm(int termId) {
        return (mRepository.getCourseByTerm(termId));
    }

    public List<Course> getAllCourses() {
        LiveData<List<Course>> courses = mRepository.getAllCourses();
        return courses.getValue();
    }
}
