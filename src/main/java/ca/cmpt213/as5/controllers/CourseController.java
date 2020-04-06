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
    public List<CourseSubject> getCourseList() {
        sortCourseData();
//        if (subjectWrapperList.size() >= manager.getSubjects().size()) {
//            return subjectWrapperList;
//        }
//        for (int i = 0; i < manager.getSubjects().size(); i++) {
//            subjectWrapperList.add(ApiSubjectWrapper.makeCourse(manager.getSubjects().get(i), i, manager.getSubjects().get(i).getCatalogList()));
//
//        }
        return manager.getSubjects();
    }

    @GetMapping("api/departments/{id}/courses")
    public List<CourseCatalog> getCourseAtId(@PathVariable("id") long id) {
//        if (id > subjectWrapperList.size()) {
//            throw new NotFound("Cannot find id");
//        }
//        courseWrappers = subjectWrapperList.get((int) id).courseWrapperList;
        System.out.println("deptId " + id);
        return manager.getSubjects().get((int)id).getCatalogList();
    }

    @GetMapping("api/departments/{id}/courses/{courseId}/offerings")
    public List<CourseOffering> getCourseAtOffering(@PathVariable("id") long id, @PathVariable("courseId") int courseId) {
//        if (id > subjectWrapperList.size() || courseId > courseWrappers.size()) {
//            throw new NotFound("Cannot find id");
//        }
//        courseOfferingWrapperList = subjectWrapperList.get((int) id).courseWrapperList.get((int) courseId).courseOfferingWrapperList;
        System.out.println(courseId);
        return manager.getSubjects().get((int)id).getCatalogList().get(courseId).getOfferingList();
    }

    @GetMapping("/api/departments/{id}/courses/{courseId}/offerings/{offeringId}")
    public List<OfferingDetails> getCourseAtOfferingDetails(@PathVariable("id") long id, @PathVariable("courseId") int courseId,
                                                                      @PathVariable("offeringId") long offeringId) {
//        if (id > subjectWrapperList.size() || courseId > courseWrappers.size() || offeringId > courseOfferingWrapperList.size()) {
//            throw new NotFound("Cannot find id");
//        }
//        offeringDetailsWrapperList = subjectWrapperList.get((int) id).courseWrapperList.get((int) courseId).
//                courseOfferingWrapperList.get((int) offeringId).offeringDetailsWrapperList;
        return manager.getSubjects().get((int)id).getCatalogList().get(courseId).getOfferingList().get((int)offeringId).getOfferingDetailsList();
    }


    @GetMapping("/api/dump-model")
    public void dumpModel() {
        read.readCSV(manager);
        //Sort by course offering
        sortCourseData();

        for (int i = 0; i < manager.getSubjects().size(); i++) {

            for (int j = 0; j < manager.getSubjects().get(i).getCatalogList().size(); j++) {
                System.out.print(manager.getSubjects().get(i).getName());
                System.out.println(" " + manager.getSubjects().get(i).getCatalogList().get(j).getCatalogNumber());

                for (int k = 0; k < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().size(); k++) {
                    System.out.println("\t" + manager.getSubjects().get(i).getCatalogList().get(j).
                            getOfferingList().get(k).getSemesterCode() + " in " +
                            manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getLocation() +
                            " by " + manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getInstructors());

                    for (int l = 0;
                         l < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getOfferingDetailsList().size();
                         l++) {
                        System.out.println("\t\t" + "Type=" + manager.getSubjects().get(i).getCatalogList().get(j).
                                getOfferingList().get(k).getOfferingDetailsList().get(l).getType() + ", Enrollment=" +
                                manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).
                                        getOfferingDetailsList().get(l).getEnrollmentTotal() + "/" +
                                manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).
                                        getOfferingDetailsList().get(l).getEnrollmentCap());
                    }
                }
            }
        }
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus addOffering(@RequestBody ApiOfferingDataWrapper wrapper) {
//        List<CourseCatalog> list = new ArrayList<>();
        String input =
                wrapper.semester +
                        "," + wrapper.subjectName +
                        "," + wrapper.catalogNumber +
                        "," + wrapper.location +
                        "," + wrapper.enrollmentCap +
                        "," + wrapper.enrollmentTotal +
                        "," + wrapper.instructor +
                        "," + wrapper.component;
//        list.add(new CourseCatalog(wrapper.catalogNumber));
        manager.add(input);
//        long id;
//        for (int i = 0; i < subjectWrapperList.size(); i++) {
//            if (subjectWrapperList.get(i).name.equals(wrapper.subjectName)) {
//                id = subjectWrapperList.get(i).deptId;
//                subjectWrapperList.add(ApiSubjectWrapper.makeCourse(new CourseSubject(wrapper.subjectName), id-1, list));
//                break;
//            } else {
//                subjectWrapperList.add(ApiSubjectWrapper.makeCourse(new CourseSubject(wrapper.subjectName), subjectWrapperList.size(), list));
//                break;
//            }
//        }
        return HttpStatus.CREATED;
    }


    private void sortCourseData() {
        for (int i = 0; i < manager.getSubjects().size(); i++) {
            manager.sortByCourseName();
            manager.getSubjects().get(i).setDeptId(i);
            for (int j = 0; j < manager.getSubjects().get(i).getCatalogList().size(); j++) {
                manager.getSubjects().get(i).sortByCatalogNumber();
                manager.getSubjects().get(i).getCatalogList().get(j).setCourseId(j);
                for (int k = 0; k < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().size(); k++) {
                    manager.getSubjects().get(i).getCatalogList().get(j).sortOfferingList();
                    manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).setCourseOfferingId(k);
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
