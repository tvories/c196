package com.taylorvories.c196.utilities;

import com.taylorvories.c196.models.Term;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {
    private static final String SAMPLE_TITLE = "Sample Term";

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public static List<Term> getTerms() {
        List<Term> terms = new ArrayList<>();
        terms.add(new Term(SAMPLE_TITLE + " 1", getDate(0), (getDate(10))));
        terms.add(new Term(SAMPLE_TITLE + " 2", getDate(-100), (getDate(10))));
        terms.add(new Term(SAMPLE_TITLE + " 3", getDate(-1000), (getDate(10))));
        return terms;
    }

}
