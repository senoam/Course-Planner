package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.*;
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
    CourseManager manager = new CourseManager();

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
        read.readCSV(manager);
        //        courseList.sort(new sortByCourse());
        //Reference for sorting https://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
//        Comparator<CourseSubject> comparator = Comparator.comparing(CourseSubject::get);
//        comparator = comparator.thenComparing(Comparator.comparing(CourseSubject::getCatalogNumber));
//        comparator = comparator.thenComparing(Comparator.comparing(CourseSubject::getSemester));
//        comparator = comparator.thenComparing(Comparator.comparing(CourseSubject::getInstructor));
////        comparator = comparator.thenComparing(Comparator.comparing(Course::getComponentCode));
//        Stream<Course> personStream = courseList.stream().sorted(comparator);
//        sortedCourse = personStream.collect(Collectors.toList());


        for (int i = 0; i < manager.getSubjects().size(); i++) {
            for (int j = 0; j < manager.getSubjects().get(i).getCatalogList().size(); j++) {
                System.out.print(manager.getSubjects().get(i).getSubjectName());
                System.out.println(" " + manager.getSubjects().get(i).getCatalogList().get(j).getCatalogNumber());
                for (int k = 0; k < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().size(); k++) {
                    System.out.println("\t" + manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getSemester() +
                            " in " + manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getLocation() +
                            " by " + manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getInstructor());
                    for (int l = 0; l < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getOfferingDetailsList().size(); l++) {
                        System.out.println("\t\t" + "Type=" + manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getOfferingDetailsList().get(l).getComponentCode() + ", Enrollment=" +
                                manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getOfferingDetailsList().get(l).getTotal() + "/" +
                                manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getOfferingDetailsList().get(l).getCapacity());
                    }
                }
            }
        }

//        for (CourseSubject c : manager.getSubjects()) {
//            for (CourseCatalog catalog : manager.getSubjects().get())
//        }



//        courseList.sort(new sortByCourse());
        //Reference for sorting https://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
        Comparator<Course> comparator = Comparator.comparing(Course::getSubject);
        comparator = comparator.thenComparing(Comparator.comparing(Course::getCatalogNumber));
        comparator = comparator.thenComparing(Comparator.comparing(Course::getSemester));
        comparator = comparator.thenComparing(Comparator.comparing(Course::getInstructor));
//        comparator = comparator.thenComparing(Comparator.comparing(Course::getComponentCode));
        Stream<Course> personStream = courseList.stream().sorted(comparator);
        sortedCourse = personStream.collect(Collectors.toList());

//        String currentSubject = "";
//        String currentCatalogNum = "";
//        int currentSemester = 0;
//        String currentInstructor = "";
//        String currentLocation = "";
//        String currentType = "";
//        int labCapacity = 0;
//        int totalLab = 0;
//        String currentComponent = "";
//        for (Course c : sortedCourse) {
//            if (!c.getSubject().equals(currentSubject) && !c.getCatalogNumber().equals(currentCatalogNum)) {
//                currentSubject = c.getSubject();
//                currentCatalogNum = c.getCatalogNumber();
//                System.out.println(c.getSubject() + " " + c.getCatalogNumber());
//            }
//            if (c.getSemester() != currentSemester || !c.getInstructor().equals(currentInstructor) || !c.getLocation().equals(currentLocation)) {
//                currentSemester = c.getSemester();
//                currentInstructor = c.getInstructor();
//                currentLocation = c.getLocation();
//                System.out.println("\t" + c.getSemester() + " in " + c.getLocation() + " by " +
//                        c.getInstructor());
//            }
//            if (!c.getComponentCode().equals("LAB")) {
//                if (currentComponent.equals("LAB")) {
//                    System.out.println("\t\t" + "Type=" + currentComponent + ", Enrollment=" +
//                            totalLab + "/" + labCapacity);
//                    totalLab = 0;
//                    labCapacity = 0;
//                }
//                currentComponent = c.getComponentCode();
//                System.out.println("\t\t" + "Type=" + c.getComponentCode() + ", Enrollment=" +
//                        c.getTotal() + "/" + c.getCapacity());
//            } else {
//                currentComponent = c.getComponentCode();
//                labCapacity = labCapacity + c.getCapacity();
//                totalLab = totalLab + c.getTotal();
//            }
//
//        }

//        for(Course s : sortedCourse){
//            System.out.println(s.getSubject() + " " + s.getCatalogNumber() + " " + s.getSemester());
//        }

    }

}
