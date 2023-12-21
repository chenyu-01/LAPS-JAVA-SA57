package com.laps.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebAppController {

    // Redirect all routes to index.html
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

   
    @RequestMapping(value = "/test")
    public String test() {
        return "Test";
    }

    @RequestMapping(value = "/leave")
    public String leave() {
        return "LeaveApplicationSubmit";
    }
}
