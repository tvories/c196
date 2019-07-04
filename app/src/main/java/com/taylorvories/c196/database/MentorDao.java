package com.taylorvories.c196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taylorvories.c196.models.Mentor;

import java.util.List;

@Dao
public interface MentorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMentor(Mentor mentor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Mentor> mentors);

    @Delete
    void deleteMentor(Mentor mentor);

    @Query("SELECT * FROM mentors WHERE id = :id")
    Mentor getMentorById(int id);

    @Query("SELECT * FROM mentors ORDER BY name DESC")
    LiveData<List<Mentor>> getAll();

    @Query("Select * FROM mentors WHERE courseId = :courseId")
    LiveData<List<Mentor>> getMentorsByCourse(final int courseId);

    @Query("DELETE FROM mentors")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM mentors")
    int getCount();
}
