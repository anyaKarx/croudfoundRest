package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public UserDTO findUserByEmail(String email)
    {
        User tmp = userRepository.findUserByName(email).orElseThrow(() ->
            new NotFoundDataException("Пользователя с таким email нет."));
         return mapper.exportUser(tmp);

    }

    public UserDTO prepareAndSave(String email, @Valid UserDTO newUser) {
        User tmp = userRepository.findUserByName(email).orElseThrow(() ->
                new NotFoundDataException("Пользователя с таким email нет."));
        tmp = mapper.importUser(newUser);
        userRepository.save(tmp);
        return newUser;
    }
}
