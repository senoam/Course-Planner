package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.Course;
import ca.cmpt213.as5.model.ReadCSVFile;
import ca.cmpt213.as5.restapi.ApiAboutWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CourseController {
    ApiAboutWrapper aboutWrapper = new ApiAboutWrapper("Course Planner",
                                    "Christofer Calvin Kurniawan & Seno Adhi Muhammad");
    List<Course> courseList = new ArrayList<>();
    List<Course> sortedCourse;
    ReadCSVFile read = new ReadCSVFile();

    class sortByCourse implements Comparator<Course> {
        @Override
        public int compare(Course o1, Course o2) {
            return o1.getInstructor().compareTo(o2.getInstructor());
        }
    }

    @GetMapping("/api/about")
    public ApiAboutWrapper about() {
        return aboutWrapper;
    }


    @GetMapping("/dump-model")
    public void dumpModel() {
        read.readCSV(courseList);

//        courseList.sort(new sortByCourse());
        //Reference for sorting https://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
        Comparator<Course> comparator = Comparator.comparing(Course::getSubject);
        comparator = comparator.thenComparing(Comparator.comparing(Course::getCatalogNumber));
        comparator = comparator.thenComparing(Comparator.comparing(Course::getSemester));
        comparator = comparator.thenComparing(Comparator.comparing(Course::getInstructor));
        comparator = comparator.thenComparing(Comparator.comparing(Course::getComponentCode));
        Stream<Course> personStream = courseList.stream().sorted(comparator);
        sortedCourse = personStream.collect(Collectors.toList());

        String currentSubject = "";
        String currentCatalogNum = "";
        int currentSemester = 0;
        String currentInstructor = "";
        String currentLocation = "";
        String currentType = "";
        int labCapacity = 0;
        int totalLab = 0;
        String currentComponent = "";
        for (Course c : sortedCourse) {
            if (!c.getSubject().equals(currentSubject) && !c.getCatalogNumber().equals(currentCatalogNum)) {
                currentSubject = c.getSubject();
                currentCatalogNum = c.getCatalogNumber();
                System.out.println(c.getSubject() + " " + c.getCatalogNumber());
            }
            if (c.getSemester() != currentSemester || !c.getInstructor().equals(currentInstructor) || !c.getLocation().equals(currentLocation)) {
                currentSemester = c.getSemester();
                currentInstructor = c.getInstructor();
                currentLocation = c.getLocation();
                System.out.println("\t" + c.getSemester() + " in " + c.getLocation() + " by " +
                        c.getInstructor());
            }
            if (!c.getComponentCode().equals(currentComponent)) {
                if (currentComponent.equals("LAB")) {
                    System.out.println("\t\t" + "Type=" + currentComponent + ", Enrollment=" +
                            totalLab + "/" + labCapacity);
                    totalLab = 0;
                    labCapacity = 0;
                }
                currentComponent = c.getComponentCode();
                System.out.println("\t\t" + "Type=" + c.getComponentCode() + ", Enrollment=" +
                        c.getTotal() + "/" + c.getCapacity());
            } else {
                currentComponent = c.getComponentCode();
                labCapacity = labCapacity + c.getCapacity();
                totalLab = totalLab + c.getTotal();
            }

        }

    }

}
