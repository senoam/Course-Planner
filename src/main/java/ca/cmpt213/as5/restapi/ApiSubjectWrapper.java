package ca.cmpt213.as5.restapi;

import ca.cmpt213.as5.model.CourseCatalog;
import ca.cmpt213.as5.model.CourseSubject;

public class ApiSubjectWrapper {
    public long deptId;
    public String name;

    public static ApiSubjectWrapper makeCourse(CourseSubject courseSubject, long index) {
        ApiSubjectWrapper wrapper = new ApiSubjectWrapper();
        wrapper.name = courseSubject.getSubjectName();
        wrapper.deptId = index;
        return wrapper;
    }

}