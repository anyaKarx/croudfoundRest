package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.croudfounding.payload.request.SignupRequest;
import ru.cft.croudfounding.payload.response.UserInfoResponse;
import ru.cft.croudfounding.repository.UserRepository;
import ru.cft.croudfounding.repository.mapper.CrowdfundingMapper;
import ru.cft.croudfounding.repository.model.User;

import javax.validation.Valid;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CrowdfundingMapper mapper;
    private final PasswordEncoder encoder;

    public UserInfoResponse updateUserInfo(@Valid SignupRequest updateInfo) {
        User tmp = userRepository.findUserByEmailIgnoreCase(updateInfo.getEmail()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("User with email \"%s\" not found", updateInfo.getEmail())));
        var newUser = mapper.importUser(updateInfo);
        newUser.setId(tmp.getId());
        return mapper.exportUser(userRepository.save(newUser));
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("User with email \"%s\" not found", email)));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "User not found"));
    }

    public void save(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already taken");
        }
        user.setEmail(user.getEmail().toLowerCase(Locale.ENGLISH));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public UserInfoResponse findUserDTOByAuth() {
        var authUser = SecurityContextHolder.getContext().getAuthentication();
        User user = findUserByEmail(authUser.getName());
        return mapper.exportUser(user);
    }
}
