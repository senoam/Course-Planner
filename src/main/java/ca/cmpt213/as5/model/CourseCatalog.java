package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CourseCatalog {
    private List<CourseOffering> offeringList = new ArrayList<>();
    private List<CourseOffering> sortedList;
    private String catalogNumber;

    public CourseCatalog(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }


    public void sortOfferingList() {
        //Reference for sorting https://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
        Comparator<CourseOffering> comparator = Comparator.comparing(CourseOffering::getSemester);
        comparator = comparator.thenComparing(Comparator.comparing(CourseOffering::getInstructor));
        Stream<CourseOffering> personStream = offeringList.stream().sorted(comparator);
        sortedList = personStream.collect(Collectors.toList());
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
