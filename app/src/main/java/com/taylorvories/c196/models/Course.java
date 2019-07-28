package com.taylorvories.c196.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date startDate;
    private Date anticipatedEndDate;
    private CourseStatus courseStatus;
    private String note;

    private int termId;

    @Ignore
    public Course() {

    }

    @Ignore
    public Course(int id, String title, Date startDate, Date anticipatedEndDate, CourseStatus courseStatus, int termId, String note) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.courseStatus = courseStatus;
        this.termId = termId;
        this.note = note;
    }

    @Ignore
    public Course(String title, Date startDate, Date anticipatedEndDate, CourseStatus courseStatus, int termId) {
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.courseStatus = courseStatus;
        this.termId = termId;
    }

    public Course(String title, Date startDate, Date anticipatedEndDate, CourseStatus courseStatus) {
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.courseStatus = courseStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getAnticipatedEndDate() {
        return anticipatedEndDate;
    }

    public void setAnticipatedEndDate(Date anticipatedEndDate) {
        this.anticipatedEndDate = anticipatedEndDate;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getTermId() {
        return termId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
