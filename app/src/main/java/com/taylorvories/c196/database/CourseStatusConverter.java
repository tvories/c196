package com.taylorvories.c196.database;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import com.taylorvories.c196.models.CourseStatus;

public class CourseStatusConverter {

    @TypeConverter
    public static String fromCourseStatusToString(CourseStatus courseStatus) {
        if(courseStatus == null) {
            return null;
        }
        return courseStatus.name();
    }

    @TypeConverter
    public static CourseStatus fromStringToCourseStatus(String courseStatus) {
        if(TextUtils.isEmpty(courseStatus)) {
            return CourseStatus.IN_PROGRESS;
        }
        return CourseStatus.valueOf(courseStatus);
    }
}
