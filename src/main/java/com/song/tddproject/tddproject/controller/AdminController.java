package com.song.tddproject.tddproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AdminController {
    @GetMapping("/admin")
    public String admin(){
        return "<h1>Admin</h1>";
    }
}
