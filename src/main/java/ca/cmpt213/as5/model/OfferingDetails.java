package ca.cmpt213.as5.model;

public class OfferingDetails {
    String componentCode;
    int total;
    int capacity;

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

        return c.getComponentCode().equals(componentCode) && c.getTotal() == total
                && c.getCapacity() == capacity;
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
}