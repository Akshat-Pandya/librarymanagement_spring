package com.pandastudios.librarymanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pandastudios.librarymanagement.entity.User;
import com.pandastudios.librarymanagement.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public User getCurrentUser(Principal principal) {
        String email = principal.getName();
        log.info("Fetching user {}", email);
        User user=userService.getUserByEmail(email);
        if(user!=null) return user;
        log.info("User not found: {}", email);
        return null;
    }

    // admin controlled
    @GetMapping("/id/{id}")
    public User getUserByID(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @PutMapping
    public String updateUser(@RequestBody User user, Principal principal) {
        String email = principal.getName();
        User existingUser = userService.getUserByEmail(email);
        if (existingUser == null) {
            return "User not found";
        }
        
        return userService.updateUser(user,existingUser) != null ? "User updated successfully" : "User not found";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    // admin controlled
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
 
}
