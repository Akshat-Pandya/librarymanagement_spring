package com.pandastudios.librarymanagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pandastudios.librarymanagement.dto.UserDto;
import com.pandastudios.librarymanagement.entity.User;
import com.pandastudios.librarymanagement.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    // admin controlled
    public User signup(@RequestBody UserDto userDto) {
        return userService.createAdmin(userDto);
    }
    
}
