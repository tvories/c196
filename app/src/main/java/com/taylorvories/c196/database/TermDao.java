package com.taylorvories.c196.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.taylorvories.c196.models.Term;

@Dao
public interface TermDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(Term term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Term> terms);

    @Delete
    void deleteTerm(Term term);

    @Query("SELECT * FROM terms WHERE id = :id")
    Term getTermById(int id);

    @Query("SELECT * FROM terms ORDER BY startDate DESC")
    LiveData<List<Term>> getAll();

    @Query("DELETE FROM terms")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM terms")
    int getCount();
}
