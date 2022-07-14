package ru.cft.croudfounding.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
        userService.register(newUser);
        return newUser;
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.findUserByEmail(user.getEmail());
    }

}
