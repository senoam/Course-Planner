package ca.cmpt213.as5;

import ca.cmpt213.as5.model.Course;
import ca.cmpt213.as5.model.CourseManager;
import ca.cmpt213.as5.model.ReadCSVFile;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Course> courseList = new ArrayList<>();
        CourseManager manager = new CourseManager();
        ReadCSVFile read = new ReadCSVFile();
        read.readCSV(manager);
        for(Course s : courseList){
            System.out.println(s.getInstructor());
        }
        System.out.println("ghehe");
    }
}
