package com.taylorvories.c196.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessments", foreignKeys = @ForeignKey(entity = Course.class,
                                                            parentColumns = "id",
                                                            childColumns = "courseId",
                                                            onDelete = CASCADE))
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date date;
    private AssessmentType assessmentType;
    private int courseId;

    @Ignore
    public Assessment() {

    }

    public Assessment(String title, Date date, AssessmentType assessmentType, int courseId) {
        this.title = title;
        this.date = date;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
