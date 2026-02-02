package com.pandastudios.librarymanagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pandastudios.librarymanagement.dto.UserDto;
import com.pandastudios.librarymanagement.entity.User;
import com.pandastudios.librarymanagement.security.JwtUtil;
import com.pandastudios.librarymanagement.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody UserDto userdto) {
        return userService.createUser(userdto);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        System.out.println("Sample Test Log");
        return "Test endpoint is working and live!";
    }
    @GetMapping("/test2")
    public String testEndpoint2() {
        System.out.println("Sample Test Log 2");
        return "Test endpoint is working and live 2!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        return jwtUtil.generateToken(auth.getName());
    }
    
}
