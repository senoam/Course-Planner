package ca.cmpt213.as5.restapi;

import ca.cmpt213.as5.model.OfferingDetails;

import java.util.ArrayList;
import java.util.List;

public class ApiCourseOfferingWrapper {
    public long courseOfferingId;
    public long semesterCode;
    public String instructors;
    public String location;
    public String term;
    public int year;
    public List<ApiOfferingDetailsWrapper> offeringDetailsWrapperList = new ArrayList<>();


    public ApiCourseOfferingWrapper(long courseOfferingId, String location, String instructors, String term, long semesterCode, int year,
                                    List<OfferingDetails> offeringDetailsList) {
        this.courseOfferingId = courseOfferingId;
        this.location = location;
        this.instructors = instructors;
        this.term = term;
        this.semesterCode = semesterCode;
        this.year = year;
        for (OfferingDetails o : offeringDetailsList) {
            offeringDetailsWrapperList.add(new ApiOfferingDetailsWrapper(o.getType(), o.getEnrollmentCap(), o.getEnrollmentTotal()));
        }

    }
}
