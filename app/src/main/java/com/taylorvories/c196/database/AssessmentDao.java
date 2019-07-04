package com.taylorvories.c196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taylorvories.c196.models.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assessment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Assessment> assessments);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("SELECT * FROM assessments WHERE id = :id")
    Assessment getAssessmentById(int id);

    @Query("SELECT * FROM assessments ORDER BY date DESC")
    LiveData<List<Assessment>> getAll();

    @Query("SELECT * FROM assessments WHERE courseId = :courseId")
    LiveData<List<Assessment>> getAssessmentByCourse(final int courseId);

    @Query("DELETE FROM assessments")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM assessments")
    int getCount();
}
