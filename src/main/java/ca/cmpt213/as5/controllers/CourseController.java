package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.*;
import ca.cmpt213.as5.restapi.ApiAboutWrapper;
import ca.cmpt213.as5.restapi.ApiSubjectWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CourseController {
    ApiAboutWrapper aboutWrapper;
    ReadCSVFile read = new ReadCSVFile();
    CourseManager manager = new CourseManager();
    List<ApiSubjectWrapper> subjectWrapper = new ArrayList<>();



    @GetMapping("/api/about")
    public ApiAboutWrapper about() {
        aboutWrapper = new ApiAboutWrapper("Course Planner",
                "Christofer Calvin Kurniawan & Seno Adhi Muhammad");
        return aboutWrapper;
    }

    @GetMapping("/api/departments")
    public List<ApiSubjectWrapper> getCourseList() {
        sortCourseData();
        if (subjectWrapper.size() >= manager.getSubjects().size()) {
            return subjectWrapper;
        }
        for (int i = 0; i < manager.getSubjects().size(); i++) {
            subjectWrapper.add(ApiSubjectWrapper.makeCourse(manager.getSubjects().get(i),i));
        }
        return subjectWrapper;
    }

    @GetMapping("api/departments/{id}/courses")
    public ApiSubjectWrapper getCourseAtId(@PathVariable("id")long id) {
        if (id > subjectWrapper.size()) {
            throw new NotFound("Cannot find id");
        }
        return subjectWrapper.get((int) id);
    }


    @GetMapping("/api/dump-model")
    public void dumpModel() {
        read.readCSV(manager);
        //Sort by course offering
        sortCourseData();

        for (int i = 0; i < manager.getSubjects().size(); i++) {

            for (int j = 0; j < manager.getSubjects().get(i).getCatalogList().size(); j++) {
                System.out.print(manager.getSubjects().get(i).getSubjectName());
                System.out.println(" " + manager.getSubjects().get(i).getCatalogList().get(j).getCatalogNumber());

                for (int k = 0; k < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().size(); k++) {
                    System.out.println("\t" + manager.getSubjects().get(i).getCatalogList().get(j).
                            getOfferingList().get(k).getSemester() + " in " +
                            manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getLocation() +
                            " by " + manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getInstructor());

                    for (int l = 0;
                         l < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getOfferingDetailsList().size();
                         l++) {
                        System.out.println("\t\t" + "Type=" + manager.getSubjects().get(i).getCatalogList().get(j).
                                getOfferingList().get(k).getOfferingDetailsList().get(l).getComponentCode() + ", Enrollment=" +
                                manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).
                                        getOfferingDetailsList().get(l).getTotal() + "/" +
                                manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).
                                        getOfferingDetailsList().get(l).getCapacity());
                    }
                }
            }
        }
    }

    private void sortCourseData() {
        for (int i = 0; i < manager.getSubjects().size(); i++) {
            manager.sortByCourseName();
            for (int j = 0; j < manager.getSubjects().get(i).getCatalogList().size(); j++) {
                manager.getSubjects().get(i).sortByCatalogNumber();
                for (int k = 0; k < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().size(); k++) {
                    manager.getSubjects().get(i).getCatalogList().get(j).sortOfferingList();
                }
            }
        }
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    static class NotFound extends RuntimeException {
        public NotFound(){}
        public NotFound(String str) {
            super((str));
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    static class BadRequest extends RuntimeException {
        public BadRequest(){}
        public BadRequest(String str) {super((str));}
    }


}
