package ca.cmpt213.as5.restapi;

import ca.cmpt213.as5.model.CourseCatalog;
import ca.cmpt213.as5.model.CourseSubject;

import java.util.ArrayList;
import java.util.List;

public class ApiSubjectWrapper {
    public long deptId;
    public String name;
    public List<ApiCourseWrapper> courseWrapperList = new ArrayList<>();

    public static ApiSubjectWrapper makeCourse(CourseSubject courseSubject, long index, List<CourseCatalog> catalogList) {
        ApiSubjectWrapper wrapper = new ApiSubjectWrapper();
        wrapper.name = courseSubject.getSubjectName();
        wrapper.deptId = index;

        for (int i = 0; i < catalogList.size() ; i++) {
            wrapper.courseWrapperList.add(new ApiCourseWrapper(i, catalogList.get(i).getCatalogNumber(),
                    catalogList.get(i).getOfferingList()));
        }

        return wrapper;
    }

}