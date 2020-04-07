package ca.cmpt213.as5.model;

import java.util.*;

public class Watcher implements Observer {
    private long id;
    private long deptId;
    private long courseId;
    private String name;
    private CourseSubject department;
    private CourseCatalog course;
    private List<String> events = new ArrayList<>();
    private int length;

    public Watcher(long deptId, long courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public Watcher(String input) {
        String numbers = input.replaceAll("[^0-9]", "");
        String[] num = numbers.split("");
        deptId = Long.parseLong(num[0]);
        courseId = Long.parseLong(num[1]);
        length = events.size();
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Observer added");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseSubject getDepartment() {
        return department;
    }

    public void setDepartment(CourseSubject department) {
        this.department = department;
    }

    public CourseCatalog getCourse() {
        return course;
    }

    public void setCourse(CourseCatalog course) {
        this.course = course;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDeptId() {
        return deptId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setDeptId(long deptId) {
        department.setDeptId(deptId);
    }

    public void setCourseId(long courseId) {
        course.setCourseId(courseId);
    }
}
