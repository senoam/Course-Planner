package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**Course Offering contains the semester, instruction and location of a class and also a list of offering details */
public class CourseOffering {
    private List<OfferingDetails> offeringDetailsList = new ArrayList<>();
    private int semester;
    private String instructor;
    private String location;

    public CourseOffering(int semester, String instructor, String location) {
        this.semester = semester;
        this.instructor = instructor;
        this.location = location;
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

        return c.getSemester() == semester && c.getInstructor().equals(instructor)
                && c.getLocation().equals(location);
    }

    public List<OfferingDetails> getOfferingDetailsList() {
        return offeringDetailsList;
    }

    public int getSemester() {
        return semester;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getLocation() {
        return location;
    }

}
