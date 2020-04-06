package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.*;
import ca.cmpt213.as5.restapi.ApiAboutWrapper;
import ca.cmpt213.as5.restapi.ApiOfferingDataWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CourseController {
    ReadCSVFile read = new ReadCSVFile();
    CourseManager manager = new CourseManager();

    @GetMapping("/api/about")
    public ApiAboutWrapper about() {
        ApiAboutWrapper aboutWrapper = new ApiAboutWrapper("Course Planner",
                "Christofer Calvin Kurniawan & Seno Adhi Muhammad");
        return aboutWrapper;
    }


    @GetMapping("/api/dump-model")
    public void dumpModel() {
        read.readCSV(manager);

        //Sort by course offering
        for (int i = 0; i < manager.getSubjects().size(); i++) {
            manager.sortByCourseName();
            for (int j = 0; j < manager.getSubjects().get(i).getCatalogList().size(); j++) {
                manager.getSubjects().get(i).sortByCatalogNumber();
                for (int k = 0; k < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().size(); k++) {
                    manager.getSubjects().get(i).getCatalogList().get(j).sortOfferingList();
                }
            }
        }

        for (int i = 0; i < manager.getSubjects().size(); i++) {

            for (int j = 0; j < manager.getSubjects().get(i).getCatalogList().size(); j++) {
                System.out.print(manager.getSubjects().get(i).getSubjectName());
                System.out.println(" " + manager.getSubjects().get(i).getCatalogList().get(j).getCatalogNumber());

                for (int k = 0; k < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().size(); k++) {
                    System.out.println("\t" + manager.getSubjects().get(i).getCatalogList().get(j).
                            getOfferingList().get(k).getSemester() + " in " +
                            manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getLocation() +
                            " by " + manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getInstructor());

                    for (int l = 0;
                         l < manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).getOfferingDetailsList().size();
                         l++) {
                        System.out.println("\t\t" + "Type=" + manager.getSubjects().get(i).getCatalogList().get(j).
                                getOfferingList().get(k).getOfferingDetailsList().get(l).getComponentCode() + ", Enrollment=" +
                                manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).
                                        getOfferingDetailsList().get(l).getTotal() + "/" +
                                manager.getSubjects().get(i).getCatalogList().get(j).getOfferingList().get(k).
                                        getOfferingDetailsList().get(l).getCapacity());
                    }
                }
            }
        }

    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus addOffering(@RequestBody String input) {
        //ApiOfferingDataWrapper wrapper = new ApiOfferingDataWrapper();


        manager.add(input);
        return HttpStatus.CREATED;
    }

}
