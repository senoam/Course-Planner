package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**Course catalog stores the catalog number and has a list of offerings*/
public class CourseCatalog {
    public long courseId;
    private String catalogNumber;
    private List<CourseOffering> offeringList = new ArrayList<>();

    public CourseCatalog(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public void setOfferingList(List<CourseOffering> offeringList) {
        this.offeringList = offeringList;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public void sortOfferingList() {
        //Reference for sorting https://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
        Comparator<CourseOffering> comparator = Comparator.comparing(CourseOffering::getSemesterCode);
        comparator = comparator.thenComparing(Comparator.comparing(CourseOffering::getInstructors));
        Stream<CourseOffering> personStream = offeringList.stream().sorted(comparator);
        List<CourseOffering> sortedList = personStream.collect(Collectors.toList());
        offeringList = sortedList;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CourseCatalog)) {
            return false;
        }

        CourseCatalog c = (CourseCatalog) obj;

        return c.getCatalogNumber().equals(catalogNumber);
    }

    public List<CourseOffering> getOfferingList() {
        return offeringList;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public int getIndex(CourseOffering myOffering) {
        return offeringList.indexOf(myOffering);
    }
}
