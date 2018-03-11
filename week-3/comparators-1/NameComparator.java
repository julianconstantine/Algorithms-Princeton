package com.javacodegeeks.java.util.comparator;

import java.util.Comparator;

public class NameComparator implements Comparator {
    @Override
    public int compare(Student o1, Student o2) {
        String name1 = o1.getName();
        String name2 = o2.getName();

        // Sort students by name in ascending order
        // Use compareTo() method for Strings
        return name1.compareTo(name2);
    }
}
