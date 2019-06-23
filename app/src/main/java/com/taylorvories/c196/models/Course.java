package com.taylorvories.c196.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "courses", foreignKeys = @ForeignKey(entity = Term.class,
                                                        parentColumns = "id",
                                                        childColumns = "termId",
                                                        onDelete = CASCADE))
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date startDate;
    private Date anticipatedEndDate;
    private CourseStatus courseStatus;
    private int termId;

    @Ignore
    public Course() {

    }

    public Course(String title, Date startDate, Date anticipatedEndDate, CourseStatus courseStatus, int termId) {
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.courseStatus = courseStatus;
        this.termId = termId;
    }

}
