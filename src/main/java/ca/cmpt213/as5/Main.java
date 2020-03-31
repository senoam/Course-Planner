package ca.cmpt213.as5;

import ca.cmpt213.as5.model.Course;
import ca.cmpt213.as5.model.ReadCSVFile;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Course> courseList = new ArrayList<>();
        ReadCSVFile read = new ReadCSVFile();
        read.readCSV(courseList);
        for(Course s : courseList){
            System.out.println(s.getInstructor());
        }
    }
}
