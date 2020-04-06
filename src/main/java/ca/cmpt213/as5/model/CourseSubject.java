package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** CourseSubject holds subject name and the list of course catalog (e.g CMPT 213, CMPT 225)*/
public class CourseSubject {
    private String name;
    private long deptId;
    private List<CourseCatalog> catalogList = new ArrayList<>();
    private List<CourseCatalog> sortedList;
    public CourseSubject(String name) {
        this.name = name;
    }

    class sortByName implements Comparator<CourseSubject> {

        @Override
        public int compare(CourseSubject o1, CourseSubject o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public List<CourseCatalog> getSortedList() {
        return sortedList;
    }

    public void setSortedList(List<CourseCatalog> sortedList) {
        this.sortedList = sortedList;
    }

    public void setCatalogList(List<CourseCatalog> catalogList) {
        this.catalogList = catalogList;
    }

    public void setName(String name) {
        this.name = name;
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

        return c.getName().equals(name);
    }

    public List<CourseCatalog> getCatalogList() {
        return catalogList;
    }

    public String getName() {
        return name;
    }

    public int getIndex(CourseCatalog myCatalog) {
        return catalogList.indexOf(myCatalog);
    }
}
