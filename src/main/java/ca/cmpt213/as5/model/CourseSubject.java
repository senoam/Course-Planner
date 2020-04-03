package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

public class CourseSubject {
    private List<CourseCatalog> catalogList = new ArrayList<>();
    private String subjectName;

    public CourseSubject(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CourseSubject)) {
            return false;
        }

        CourseSubject c = (CourseSubject) obj;

        return c.getSubjectName().equals(subjectName);
    }

    public List<CourseCatalog> getCatalogList() {
        return catalogList;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getIndex(CourseCatalog myCatalog) {
        return catalogList.indexOf(myCatalog);
    }
}
