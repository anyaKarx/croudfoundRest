package ru.cft.croudfounding.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.croudfounding.payload.request.LoginRequest;
import ru.cft.croudfounding.payload.request.SignupRequest;
import ru.cft.croudfounding.payload.response.UserInfoResponse;
import ru.cft.croudfounding.repository.mapper.CrowdfundingMapper;
import ru.cft.croudfounding.repository.model.User;
import ru.cft.croudfounding.service.UserService;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final CrowdfundingMapper mapper;

    @PostMapping("/register")
    public UserInfoResponse register(@RequestBody SignupRequest signupRequest) {
        User newUser = mapper.importUser(signupRequest);
        userService.save(newUser);
        return mapper.exportUser(newUser);
    }

    @PostMapping("/login")
    public UserInfoResponse login(@RequestBody LoginRequest loginRequest) {
        User authUser = userService.findUserByEmail(loginRequest.getUsername());
        return mapper.exportUser(authUser);
    }

}
