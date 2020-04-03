package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

public class CourseCatalog {
    private List<CourseOffering> offeringList = new ArrayList<>();
    private String catalogNumber;

    public CourseCatalog(String catalogNumber) {
        this.catalogNumber = catalogNumber;
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
