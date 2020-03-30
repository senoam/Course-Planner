package ca.cmpt213.as5.model;

public class Course {
    private int semester;
    private String subject;
    private int catalogNumber;
    private String location;
    private int capacity;
    private int total;
    private String instructor;
    private String componentCode;

    public Course(int semester, String subject, int catalogNumber, String location, int capacity,
                  int total, String instructor, String componentCode) {
        this.semester = semester;
        this.subject = subject;
        this.catalogNumber = catalogNumber;
        this.location = location;
        this.capacity = capacity;
        this.total = total;
        this.instructor = instructor;
        this.componentCode = componentCode;
    }
}
