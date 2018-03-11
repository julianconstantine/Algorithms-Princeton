package com.javacodegeeks.java.util.comparator;

import java.util.Comparator;

public class GradeComparator implements Comparator {
    @Override
    public int compare(Student o1, Student o2) {
        // Sort students by grade in descending order
        return o2.getGrade () - o1.getGrade();
    }
}
