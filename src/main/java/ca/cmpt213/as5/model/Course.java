package ca.cmpt213.as5.model;

/** Omit this class * */
public class Course {
    private int semester;
    private String subject;
    private String catalogNumber;
    private String location;
    private int capacity;
    private int total;
    private String instructor;
    private String componentCode;

    public String getInstructor() {
        return instructor;
    }

    public Course(String strCurrentLine) {
        //Reference to split : https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
        String[] result = strCurrentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        semester = Integer.parseInt(result[0]);
        subject = result[1].replace(" ", "");
        catalogNumber = result[2].replace(" ", "");
        location = result[3].replace(" ", "");
        capacity = Integer.parseInt(result[4]);
        total = Integer.parseInt(result[5]);
        setInstructor(result[6]);
        componentCode = result[7].replace(" ", "");
    }

    public void setInstructor(String dataInstructor) {
        if (dataInstructor.equals("(null)") || dataInstructor.equals("<null>")) {
            instructor = "(nothing)";
        }
        else {
            dataInstructor = dataInstructor.replace(" ", "");
            ;
            int size = dataInstructor.length();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < size - 1; i++) {
                result.append(dataInstructor.charAt(i));
                if (Character.isUpperCase(dataInstructor.charAt(i + 1)) && i != 0) {
                    result.append(" ");
                }
            }
            result.append(dataInstructor.charAt(size - 1));
            instructor = result.toString();
        }
    }

    public int getSemester() {
        return semester;
    }

    public String getSubject() {
        return subject;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getTotal() {
        return total;
    }

    public String getComponentCode() {
        return componentCode;
    }
}