package com.song.tddproject.tddproject.controller;

import com.song.tddproject.tddproject.config.exception.ErrorCode;
import com.song.tddproject.tddproject.config.exception.RestApiException;
import com.song.tddproject.tddproject.config.exception.UserErrorCode;
import com.song.tddproject.tddproject.dto.JoinRequestDto;
import com.song.tddproject.tddproject.entity.User;
import com.song.tddproject.tddproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public String user(){
        return "<h1>User</h1>";
    }

    @PostMapping("/join")
    public User join(@RequestBody JoinRequestDto dto) {
        userService.validatePhone(dto.getPhone());
        userService.validateUsername(dto.getUsername());
        return userService.addUser(dto.toEntity());
    }
}
