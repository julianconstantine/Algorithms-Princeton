package com.javacodegeeks.java.util.comparator;

import java.util.Arrays;

public class MainComparatorExample {
    Student[] student = new Student[3];

    student[0] = new Student();
    student[0].setName("Nick");
    student[0].setGrade(19);

    student[1] = new Student();
    student[1].setName("Helen");
    student[1].setGrade(12);

    student[2] = new Student();
    student[2].setName("Ross");
    student[2].setGrade(16);

    // Print out the students in the original order
    System.out.println("Order of students before sorting is: ");

    for (int i = 0; i < student.length; i++) {
        System.out.println(student[i].getName() + "\t" + student[i].getGrade());
    }

    // Use Arrays.osrt() method to sort student by grade using the GradeComparator
    Arrays.sort(student, new GradeComparator());

    // Print out the students sorted in descending order by grade
    System.out.println("Order of students after sorting by student grade is: ");

    for (int i = 0; i < student.length; i++) {
        System.out.println(student[i].getName() + "\t" + student[i].getGrade());
    }

    // Use Arrays.sort() method to sort student by name using NameComparator
    Arrays.sort(student, new NameComparator());

    // Print out students sorted in ascending order by name
    System.out.println("Order of students after sorting by name is: ");

    for (int i = 0; i < student.length; i++) {
        System.out.println(student[i].getName() + "\t" + student[i].getGrade());
    }
}
