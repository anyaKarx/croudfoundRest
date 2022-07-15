package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.croudfounding.payload.request.SignupRequest;
import ru.cft.croudfounding.payload.response.UserInfoResponse;
import ru.cft.croudfounding.repository.UserRepository;
import ru.cft.croudfounding.repository.mapper.CrowdfundingMapper;
import ru.cft.croudfounding.repository.model.User;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CrowdfundingMapper mapper;
    private final PasswordEncoder encoder;

    public UserInfoResponse registerUser(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already taken");
        }
        user = this.saveUser(user);
        return mapper.exportUser(user);
    }

    private User saveUser(User user) {
        user.setEmail(user.getEmail().toLowerCase(Locale.ENGLISH));
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public UserInfoResponse updateUserInfo(SignupRequest updateInfo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findUserByEmailIgnoreCase(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("User with email \"%s\" not found", auth.getName())));

        this.checkPasswordsForMismatch(user.getPassword(), updateInfo.getPassword());

        user.setName(updateInfo.getName());
        user.setPassword(updateInfo.getPassword());

        user = this.saveUser(user);

        return mapper.exportUser(user);
    }

    private void checkPasswordsForMismatch(String oldPassword, String newPassword) {
        if (encoder.matches(newPassword, oldPassword)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "New and old passwords must be different"
            );
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("User with email \"%s\" not found", email)));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("User with id=%d not found", id)));
    }

    public UserInfoResponse findUserDTOByAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.findUserByEmail(auth.getName());
        return mapper.exportUser(user);
    }
}
