package com.laps.backend.controllers;

import com.laps.backend.models.User;
import com.laps.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
In the "Leave Application Processing System" project, the UserController is responsible for managing user-related functionalities. This includes creating, modifying, and deleting user accounts. The controller enables the assignment and modification of user roles (such as employee, admin, and manager). It plays a crucial role in maintaining the integrity and functionality of the user aspect of the system, which is a key component in managing leave applications, approvals, and overall system administration. For more detailed information and specific functionalities, you may refer to the document. */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    // RESTful endpoints for managing users
}


