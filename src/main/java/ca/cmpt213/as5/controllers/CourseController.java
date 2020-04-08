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
import java.util.Calendar;
import java.util.List;
/**Controller to manage REST API commands*/
@RestController
public class CourseController {
    ApiAboutWrapper aboutWrapper;
    CourseManager manager = new CourseManager();
    List<Watcher> watcherList = new ArrayList<>();


    @GetMapping("/api/about")
    public ApiAboutWrapper about() {
        aboutWrapper = new ApiAboutWrapper("Course Planner",
                "Christofer Calvin Kurniawan & Seno Adhi Muhammad");
        return aboutWrapper;
    }

    @GetMapping("/api/departments")
    public List<CourseSubject> getCourseList() {
        sortCourseData();
        return manager.getSubjects();
    }

    @GetMapping("api/departments/{id}/courses")
    public List<CourseCatalog> getCourseAtId(@PathVariable("id") long id) {
        if (id >= manager.getSubjects().size()) {
            throw new NotFound("Cannot find id");
        }
        return manager.getSubjects().get((int)id).getCatalogList();
    }

    @GetMapping("api/departments/{id}/courses/{courseId}/offerings")
    public List<CourseOffering> getCourseAtOffering(@PathVariable("id") long id, @PathVariable("courseId") int courseId) {
        if (id >= manager.getSubjects().size() || courseId >= manager.getSubjects().get((int) id).getCatalogList().size()) {
            throw new NotFound("Cannot find id");
        }
        return manager.getSubjects().get((int)id).getCatalogList().get(courseId).getOfferingList();
    }

    @GetMapping("/api/departments/{id}/courses/{courseId}/offerings/{offeringId}")
    public List<OfferingDetails> getCourseAtOfferingDetails(@PathVariable("id") long id, @PathVariable("courseId") int courseId,
                                                                      @PathVariable("offeringId") long offeringId) {
        if (id >= manager.getSubjects().size() || courseId >= manager.getSubjects().get((int) id).getCatalogList().size()
                || offeringId >= manager.getSubjects().get((int) id).getCatalogList().get((int) courseId).getOfferingList().size()) {
            throw new NotFound("Cannot find id");
        }

        return manager.getSubjects().get((int)id).getCatalogList().get(courseId).getOfferingList().get((int)offeringId).getOfferingDetailsList();
    }


    @GetMapping("/api/dump-model")
    public void dumpModel() {
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
        for(Watcher w : watcherList) {
            if (wrapper.subjectName.equals(w.getName()) && wrapper.catalogNumber.equals(w.getCourse().getCatalogNumber())) {
                long deptId = w.getDeptId();
                long courseId = w.getCourseId();
                manager.getSubjects().get((int) deptId).getCatalogList().get((int) courseId).addObserver(w);
                manager.getSubjects().get((int) deptId).getCatalogList().get((int) courseId).notifyWatchers();
                Calendar now = Calendar.getInstance();
                String msg = now.getTime().toString();
                CourseOffering myOffering = new CourseOffering(wrapper.semester, wrapper.instructor, wrapper.location);
                w.getEvents().add(msg + ": Added section " + wrapper.component + " with enrollment (" + wrapper.enrollmentTotal +
                " / " + wrapper.enrollmentCap + ") to offering " + myOffering.getTerm() + " " + myOffering.getYear());
            }
        }
        sortCourseData();
        return HttpStatus.CREATED;
    }

    @GetMapping("/api/watchers/{id}")
    public Watcher getWatchers(@PathVariable("id")long id) {
        if (id >= watcherList.size()) {
            throw new NotFound("Invalid watcher id!");
        }
        return watcherList.get((int) id);
    }

    @GetMapping("/api/watchers")
    public List<Watcher> getWatchers() {
        return watcherList;
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus addWatchers(@RequestBody Watcher watcher) {

        watcher.setId(watcherList.size());
        long deptId = watcher.getDeptId();
        if(deptId >= manager.getSubjects().size()){
            throw new NotFound("Invalid ID");
        }
        long courseId = watcher.getCourseId();
        if(courseId >= manager.getSubjects().get((int)deptId).getCatalogList().size()){
            throw new NotFound("Invalid ID");
        }
        String name = manager.getSubjects().get((int) deptId).getName();
        String catalogNumber = manager.getSubjects().get((int) deptId).getCatalogList().get((int) courseId).getCatalogNumber();
        watcher.setName(name);
        watcher.setDepartment(manager.getSubjects().get((int) deptId));
        watcher.setCourse(manager.getSubjects().get((int) deptId).getCatalogList().get((int) courseId));
        watcherList.add(watcher);
        manager.getSubjects().get((int) deptId).getCatalogList().get((int) courseId).addObserver(watcher);
        manager.getSubjects().get((int) deptId).getCatalogList().get((int) courseId).notifyWatchers();
        return HttpStatus.CREATED;
    }

    @DeleteMapping("/api/watchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteWatcher(@PathVariable("id")long id) {
        if (id >= watcherList.size()) {
            throw new NotFound("Invalid watcher id!");
        }
        watcherList.remove((int) id);
        for (int i = 0; i < watcherList.size(); i++) {
            watcherList.get(i).setId(i);
        }
        sortCourseData();
        return HttpStatus.NO_CONTENT;
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
