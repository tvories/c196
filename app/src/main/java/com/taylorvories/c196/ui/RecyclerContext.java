package com.taylorvories.c196.ui;

public enum RecyclerContext {
    PARENT {
        @Override
        public String toString() {
            return "Parent";
        }
    },

    CHILD {
        @Override
        public String toString() {
            return "Child";
        }
    }
}
