package ca.cmpt213.as5.restapi;

public class ApiOfferingDetailsWrapper {
    public String type;
    public int enrollmentCap;
    public int enrollmentTotal;

    public ApiOfferingDetailsWrapper(String type, int enrollmentCap, int enrollmentTotal) {
        this.type = type;
        this.enrollmentCap = enrollmentCap;
        this.enrollmentTotal = enrollmentTotal;
    }
}