package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.*;
import ca.cmpt213.as5.restapi.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ca.cmpt213.as5.restapi.ApiAboutWrapper;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseController {
    ApiAboutWrapper aboutWrapper;
    ReadCSVFile read = new ReadCSVFile();
    CourseManager manager = new CourseManager();
    List<ApiSubjectWrapper> subjectWrapperList = new ArrayList<>();
    List<ApiCourseWrapper> courseWrappers = new ArrayList<>();
    List<ApiCourseOfferingWrapper> courseOfferingWrapperList = new ArrayList<>();
    List<ApiOfferingDetailsWrapper> offeringDetailsWrapperList = new ArrayList<>();


    @GetMapping("/api/about")
    public ApiAboutWrapper about() {
        aboutWrapper = new ApiAboutWrapper("Course Planner",
                "Christofer Calvin Kurniawan & Seno Adhi Muhammad");
        return aboutWrapper;
    }

    @GetMapping("/api/departments")
    public List<ApiSubjectWrapper> getCourseList() {
        sortCourseData();
        if (subjectWrapperList.size() >= manager.getSubjects().size()) {
            return subjectWrapperList;
        }
        for (int i = 0; i < manager.getSubjects().size(); i++) {
            subjectWrapperList.add(ApiSubjectWrapper.makeCourse(manager.getSubjects().get(i), i, manager.getSubjects().get(i).getCatalogList()));

        }
        return subjectWrapperList;
    }

    @GetMapping("api/departments/{id}/courses")
    public List<ApiCourseWrapper> getCourseAtId(@PathVariable("id") long id) {
        if (id > subjectWrapperList.size()) {
            throw new NotFound("Cannot find id");
        }
        courseWrappers = subjectWrapperList.get((int) id).courseWrapperList;
        return courseWrappers;
    }

    @GetMapping("api/departments/{id}/courses/{courseId}/offerings")
    public List<ApiCourseOfferingWrapper> getCourseAtOffering(@PathVariable("id") long id, @PathVariable("courseId") long courseId) {
        if (id > subjectWrapperList.size() || courseId > courseWrappers.size()) {
            throw new NotFound("Cannot find id");
        }
        courseOfferingWrapperList = subjectWrapperList.get((int) id).courseWrapperList.get((int) courseId).courseOfferingWrapperList;
        return courseOfferingWrapperList;
    }

    @GetMapping("/api/departments/{id}/courses/{courseId}/offerings/{offeringId}")
    public List<ApiOfferingDetailsWrapper> getCourseAtOfferingDetails(@PathVariable("id") long id, @PathVariable("courseId") long courseId,
                                                                      @PathVariable("offeringId") long offeringId) {
        if (id > subjectWrapperList.size() || courseId > courseWrappers.size() || offeringId > courseOfferingWrapperList.size()) {
            throw new NotFound("Cannot find id");
        }
        offeringDetailsWrapperList = subjectWrapperList.get((int) id).courseWrapperList.get((int) courseId).
                courseOfferingWrapperList.get((int) offeringId).offeringDetailsWrapperList;
        return offeringDetailsWrapperList;
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

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus addOffering(@RequestBody ApiOfferingDataWrapper wrapper) {
        String input =
                wrapper.semester +
                        "," + wrapper.subjectName +
                        "," + wrapper.catalogNumber +
                        "," + wrapper.location +
                        "," + wrapper.enrollmentCap +
                        "," + wrapper.enrollmentTotal +
                        "," + wrapper.instructor +
                        "," + wrapper.component;
        manager.add(input);
        return HttpStatus.CREATED;
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
        public NotFound() {
        }

        public NotFound(String str) {
            super((str));
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    static class BadRequest extends RuntimeException {
        public BadRequest() {
        }

        public BadRequest(String str) {
            super((str));
        }

    }


}
