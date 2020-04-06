package ca.cmpt213.as5.model;

/** Offering details class contains the class details, such as the capacity
 *  and whether it's a lecture/tutorial/lab/etc */
public class OfferingDetails {
    private String type;
    private int enrollmentTotal;
    private int enrollmentCap;

    public OfferingDetails(String type, int enrollmentTotal, int enrollmentCap) {
        this.type = type;
        this.enrollmentTotal = enrollmentTotal;
        this.enrollmentCap = enrollmentCap;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OfferingDetails)) {
            return false;
        }

        OfferingDetails c = (OfferingDetails) obj;

        return c.getType().equals(type);
    }

    public String getType() {
        return type;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }
}