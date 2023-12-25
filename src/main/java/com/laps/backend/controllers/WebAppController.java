package com.laps.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebAppController {

    // Redirect all routes to index.html, let React Router handle the routing
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }


    @RequestMapping(value = "/leave/{id}")
    public String leave(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "LeaveApplicationSubmit";
    }
}
