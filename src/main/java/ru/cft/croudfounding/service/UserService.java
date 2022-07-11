package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.croudfounding.exception.NotFoundDataException;
import ru.cft.croudfounding.model.UserDTO;
import ru.cft.croudfounding.repository.UserRepository;
import ru.cft.croudfounding.repository.mapper.croudfoundingMapper;
import ru.cft.croudfounding.repository.model.User;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final croudfoundingMapper mapper;

    public UserDTO findUserByEmail(String email) {
        User tmp = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "" // message???
        ));
        return mapper.exportUser(tmp);

    }

    public UserDTO prepareAndSave(String email, @Valid UserDTO newUser) {
        User tmp = userRepository.findUserByEmail(email).orElseThrow(() ->
                new NotFoundDataException("Пользователя с таким email нет."));
        tmp = mapper.importUser(newUser);
        userRepository.save(tmp);
        return newUser;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already taken");
        }
        userRepository.save(user);
    }
}
