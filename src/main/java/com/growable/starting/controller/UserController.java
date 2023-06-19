package com.growable.starting.controller;

import com.growable.starting.model.Mentee;
import com.growable.starting.model.Mentor;
import com.growable.starting.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @PostMapping("/users/{userId}/become-mentor")
    public ResponseEntity<?> becomeMentor(@PathVariable Long userId, @RequestBody Mentor mentorDetails) {
        userService.becomeMentor(userId, mentorDetails);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/become-mentee")
    public ResponseEntity<?> becomeMentee(@PathVariable Long userId, @RequestBody Mentee menteeDetails) {
        userService.becomeMentee(userId, menteeDetails);
        return ResponseEntity.ok().build();
    }


}
