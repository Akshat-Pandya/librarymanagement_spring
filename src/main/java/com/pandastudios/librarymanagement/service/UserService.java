package com.pandastudios.librarymanagement.service;

import java.security.Principal;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pandastudios.librarymanagement.dto.UserDto;
import com.pandastudios.librarymanagement.entity.User;
import com.pandastudios.librarymanagement.entity.enums.Role;
import com.pandastudios.librarymanagement.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User createUser(UserDto userDto) {
        User newUser = new User();
        newUser.setName(userDto.getName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(encoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        return userRepository.save(newUser);
    }

    public User createAdmin(UserDto userDto) {
        User newUser = new User();
        newUser.setName(userDto.getName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(encoder.encode(userDto.getPassword()));
        newUser.setRole(Role.ADMIN);
        return userRepository.save(newUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByName(String name){
        return userRepository.findByName(name).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getCurrentUser(Principal principal) {
        String email = principal.getName();
        return getUserByEmail(email);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User user,User existingUser) {
        
        if(user.getName() != null && !user.getName().isEmpty())
            existingUser.setName(user.getName());

        if(user.getPassword() != null && !user.getPassword().isEmpty())
            existingUser.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(existingUser);
        
    }


}
