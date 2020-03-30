package ca.cmpt213.as5.model;

public class Course {
    private int semester;
    private String subject;
    private String catalogNumber;
    private String location;
    private int capacity;
    private int total;
    private String instructor;
    private String componentCode;

    public Course(String strCurrentLine) {
        String[] result = strCurrentLine.replace(" ","").split(",");
        semester = Integer.parseInt(result[0]);
        subject = result[1];
        catalogNumber = result[2];
        location = result[3];
        capacity = Integer.parseInt(result[4]);
        total = Integer.parseInt(result[5]);
        instructor = result[6];
        componentCode = result[7];
    }

    public String getSubject() {
        return subject;
    }

    public Course(int semester) {
        this.semester = semester;
    }
}