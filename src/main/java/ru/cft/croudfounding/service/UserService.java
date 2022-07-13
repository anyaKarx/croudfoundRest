package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.croudfounding.exception.NotFoundDataException;
import ru.cft.croudfounding.model.UserDTO;
import ru.cft.croudfounding.repository.UserRepository;
import ru.cft.croudfounding.repository.mapper.crowdfundingMapper;
import ru.cft.croudfounding.repository.model.User;

import javax.validation.Valid;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final crowdfundingMapper mapper;
    private final PasswordEncoder encoder;

    public UserDTO findUserDTOByEmail(String email) {
        User tmp = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "" // message???
        ));
        return mapper.exportUser(tmp);

    }

    public UserDTO prepareAndSave(String email, @Valid UserDTO newUser) {
        User tmp = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new NotFoundDataException("Пользователя с таким email нет."));
        tmp = mapper.importUser(newUser);
        userRepository.save(tmp);
        return newUser;
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("User with email \"%s\" not found", email)));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
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
}
