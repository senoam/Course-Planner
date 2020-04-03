package ca.cmpt213.as5.model;

public class OfferingDetails {
    private String componentCode;
    private int total;
    private int capacity;

    public OfferingDetails(String componentCode, int total, int capacity) {
        this.componentCode = componentCode;
        this.total = total;
        this.capacity = capacity;
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

        return c.getComponentCode().equals(componentCode);
    }

    public String getComponentCode() {
        return componentCode;
    }

    public int getTotal() {
        return total;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}