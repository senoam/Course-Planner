package ca.cmpt213.as5.model;

/** Offering details class contains the class details, such as the capacity
 *  and whether it's a lecture/tutorial/lab/etc */
public class OfferingDetails {
    private String component;
    private int enrollmentTotal;
    private int enrollmentCap;

    public OfferingDetails(String component, int enrollmentTotal, int enrollmentCap) {
        this.component = component;
        this.enrollmentTotal = enrollmentTotal;
        this.enrollmentCap = enrollmentCap;
    }

    public void setComponent(String component) {
        this.component = component;
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

        return c.getComponent().equals(component);
    }

    public String getComponent() {
        return component;
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