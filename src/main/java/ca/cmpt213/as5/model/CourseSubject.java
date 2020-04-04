package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CourseSubject {
    private List<CourseCatalog> catalogList = new ArrayList<>();
    private List<CourseCatalog> sortedList;
    private String subjectName;

    public CourseSubject(String subjectName) {
        this.subjectName = subjectName;
    }

    class sortByName implements Comparator<CourseSubject> {
        @Override
        public int compare(CourseSubject o1, CourseSubject o2) {
            return o1.getSubjectName().compareTo(o2.getSubjectName());
        }
    }

    public void sortByCatalogNumber() {
        //Reference for sorting https://stackoverflow.com/questions/4805606/how-to-sort-by-two-fields-in-java
        Comparator<CourseCatalog> comparator = Comparator.comparing(CourseCatalog::getCatalogNumber);
        Stream<CourseCatalog> personStream = catalogList.stream().sorted(comparator);
        sortedList = personStream.collect(Collectors.toList());
        catalogList = sortedList;
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
