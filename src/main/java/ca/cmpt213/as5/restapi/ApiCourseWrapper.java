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
        String term = "";
        int year = 0;
        int x, y, z, remainder, a;
        int i = 0;
        for (CourseOffering c : offeringList) {
            remainder = c.getSemester() % 1000;
            x = c.getSemester() / 1000;
            remainder = remainder % 100;
            y = remainder / 100;
            remainder = remainder % 10;
            z = remainder / 10;
            term = getTerm(remainder);
            year = 1900 + (100 * x) + (10 * y) + (z);
            courseOfferingWrapperList.add(new ApiCourseOfferingWrapper(i, c.getLocation(), c.getInstructor(), term,
                    c.getSemester(), year, c.getOfferingDetailsList()));
            i++;
        }

    }

    private String getTerm(int remainder) {
        int a;
        String term;
        a = remainder;
        if (a == 1) {
            term = "Spring";
        } else if (a == 4) {
            term = "Summer";
        } else if (a == 7) {
            term = "Fall";
        } else  {
            throw new IllegalArgumentException("Invalid term");
        }
        return term;
    }
}