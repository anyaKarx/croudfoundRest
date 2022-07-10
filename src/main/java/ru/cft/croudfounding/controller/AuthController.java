package ru.cft.croudfounding.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.croudfounding.repository.model.User;
import ru.cft.croudfounding.service.UserService;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User newUser) {
        if (userService.findUserByEmail(newUser.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already taken");
        }
        userService.save(newUser);
        return newUser;
    }

    @PostMapping("/login")
    public User login(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findUserByEmail(userDetails.getUsername());
    }
}
