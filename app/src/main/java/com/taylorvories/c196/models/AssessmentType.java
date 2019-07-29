package com.taylorvories.c196.models;

/**
 * Enum to assist with the type of assessments available.
 */

public enum AssessmentType {
    OA {
        @Override
        public String toString() {
            return "Objective Assessment";
        }
    },

    PA {
        @Override
        public String toString() {
            return "Performance Assessment";
        }
    }
}
