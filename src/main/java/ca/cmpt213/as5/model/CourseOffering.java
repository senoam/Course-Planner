package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Course Offering contains the semester, instruction and location of a class and also a list of offering details
 */
public class CourseOffering {
    private long courseOfferingId;
    private long semesterCode;
    private String instructors;
    private String location;
    private String term;
    private int year;
    private List<OfferingDetails> offeringDetailsList = new ArrayList<>();

    public void calculateYearTerm(long semester) {
        int x, y, z, remainder, total;

        total = (int)semester;
        x = total / 1000;
        remainder = total % 1000;
        total = remainder;
        y = total / 100;
        remainder = remainder % 100;
        total = remainder;
        z = total / 10;
        remainder = remainder % 10;

        calculateTerm(remainder);
        this.year = 1900 + (100 * x) + (10 * y) + (z);

    }

    private void calculateTerm(int remainder) {
        int a;
        a = remainder;
        if (a == 1) {
            term = "Spring";
        } else if (a == 4) {
            term = "Summer";
        } else if (a == 7) {
            term = "Fall";
        } else {
            throw new IllegalArgumentException("Invalid term");
        }
    }

    public String getTerm() {
        return term;
    }

    public int getYear() {
        return year;
    }

    public void setOfferingDetailsList(List<OfferingDetails> offeringDetailsList) {
        this.offeringDetailsList = offeringDetailsList;
    }

    public void setSemesterCode(long semesterCode) {
        this.semesterCode = semesterCode;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CourseOffering(long semesterCode, String instructors, String location) {
        this.semesterCode = semesterCode;
        this.instructors = instructors;
        this.location = location;
        calculateYearTerm(semesterCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CourseOffering)) {
            return false;
        }

        CourseOffering c = (CourseOffering) obj;

        return c.getSemesterCode() == semesterCode && c.getInstructors().equals(instructors)
                && c.getLocation().equals(location);
    }

    public List<OfferingDetails> getOfferingDetailsList() {
        return offeringDetailsList;
    }

    public long getSemesterCode() {
        return semesterCode;
    }

    public String getInstructors() {
        return instructors;
    }

    public String getLocation() {
        return location;
    }

    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

}
