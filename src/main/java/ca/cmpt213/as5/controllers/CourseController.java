package ca.cmpt213.as5.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @GetMapping("/dump-model")
    public void dumpModel() {

    }

}
