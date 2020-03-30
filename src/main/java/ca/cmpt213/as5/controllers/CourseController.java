package ca.cmpt213.as5.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    String appName;

    @GetMapping("/api/about")
    public String about() {
        String appName = "CoursePlanner";
        return appName;
    }


    @GetMapping("/dump-model")
    public void dumpModel() {

    }

}
