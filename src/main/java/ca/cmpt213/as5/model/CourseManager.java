package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    List<CourseSubject> subjects = new ArrayList<>();

    public List<CourseSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<CourseSubject> subjects) {
        this.subjects = subjects;
    }

    public void add(String strCurrentLine) {
        //Reference to split : https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
        String[] result = strCurrentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        int semester = Integer.parseInt(result[0]);
        String subjectName = result[1].replace(" ", "");
        String catalogNumber = result[2].replace(" ", "");
        String location = result[3].replace(" ", "");
        int capacity = Integer.parseInt(result[4]);
        int total = Integer.parseInt(result[5]);
        String instructor = setInstructor(result[6]);
        String componentCode = result[7].replace(" ", "");

        CourseSubject newSubject = new CourseSubject(subjectName);
        CourseCatalog newCatalog = new CourseCatalog(catalogNumber);
        CourseOffering newOffering = new CourseOffering(semester, instructor, location);
        OfferingDetails newDetails = new OfferingDetails(componentCode, total, capacity);
        for (CourseSubject c : subjects) {
            //Check Duplicate Subject
            if (checkSubjectDuplicate(newSubject, newCatalog, newOffering, newDetails, c)) {
                return;
            }
        }
        subjects.add(newSubject);
        subjects.get(getIndex(newSubject)).getCatalogList().add(newCatalog);
        subjects.get(getIndex(newSubject)).getCatalogList().
                get(subjects.get(getIndex(newSubject)).getIndex(newCatalog)).getOfferingList().add(newOffering);
        subjects.get(getIndex(newSubject)).getCatalogList().
                get(subjects.get(getIndex(newSubject)).getIndex(newCatalog)).
                getOfferingList().get(subjects.get(getIndex(newSubject)).getIndex(newCatalog)).
                getOfferingDetailsList().add(newDetails);


    }

    private boolean checkSubjectDuplicate(CourseSubject newSubject, CourseCatalog newCatalog, CourseOffering newOffering,
                                          OfferingDetails newDetails, CourseSubject c) {
        if (c.equals(newSubject)) {
            for (CourseCatalog catalog : subjects.get(getIndex(c)).getCatalogList()) {
                //Check duplicate catalog
                if (checkCatalogDuplicate(newCatalog, newOffering, newDetails, c, catalog)) {
                    return true;
                }
            }
            subjects.get(getIndex(c)).getCatalogList().add(newCatalog);
            int catalogIndex = subjects.get(getIndex(c)).getCatalogList().size() - 1;
            subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).getOfferingList().add(newOffering);
            int offeringIndex = subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).getOfferingList().size() - 1;
            subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).getOfferingList().get(offeringIndex).
                    getOfferingDetailsList().add(newDetails);
            return true;
        }
        return false;
    }

    private boolean checkCatalogDuplicate(CourseCatalog newCatalog, CourseOffering newOffering, OfferingDetails newDetails,
                                          CourseSubject c, CourseCatalog catalog) {
        if (catalog.equals(newCatalog)) {
            int catalogIndex = subjects.get(getIndex(c)).getCatalogList().indexOf(catalog);
            for (CourseOffering offering : subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).getOfferingList()) {
                if (checkOfferingDuplicate(newOffering, newDetails, c, catalogIndex, offering)) {
                    return true;
                }
            }
            subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).getOfferingList().add(newOffering);
            int offeringIndex = subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).getOfferingList().size() - 1;
            subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).getOfferingList().get(offeringIndex).
                    getOfferingDetailsList().add(newDetails);
            return true;
        }
        return false;
    }

    private boolean checkOfferingDuplicate(CourseOffering newOffering, OfferingDetails newDetails, CourseSubject c, int catalogIndex,
                                           CourseOffering offering) {
        if (offering.equals(newOffering)) {
            int offeringIndex = subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).getOfferingList().
                    indexOf(offering);
            for (OfferingDetails details : subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).
                    getOfferingList().get(offeringIndex).getOfferingDetailsList()) {
                if (checkDetailsDuplicate(newDetails, details)) {
                    return true;
                }
            }
            subjects.get(getIndex(c)).getCatalogList().get(catalogIndex).getOfferingList().get(offeringIndex).
                    getOfferingDetailsList().add(newDetails);
            return true;
        }
        return false;
    }

    private boolean checkDetailsDuplicate(OfferingDetails newDetails, OfferingDetails details) {
        if (details.equals(newDetails)) {
            details.setTotal(newDetails.getTotal() + details.getTotal());
            details.setCapacity(newDetails.getCapacity() + details.getCapacity());
            return true;
        }
        return false;
    }

    public int getIndex(CourseSubject mySubject) {
        return subjects.indexOf(mySubject);
    }












    public String setInstructor(String dataInstructor) {
        String instructor;
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
        return instructor;
    }


}
