package ca.cmpt213.as5.restapi;

import ca.cmpt213.as5.model.CourseOffering;

import java.util.ArrayList;
import java.util.List;

public class ApiCourseWrapper {
    public long courseId;
    public String catalogNumber;
    public List<ApiCourseOfferingWrapper> courseOfferingWrapperList = new ArrayList<>();

    public ApiCourseWrapper(long courseId, String catalogNumber, List<CourseOffering> offeringList) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;


    }


}