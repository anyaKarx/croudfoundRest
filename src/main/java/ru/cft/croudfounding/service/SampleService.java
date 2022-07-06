package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.croudfounding.repository.UserRepository;
import ru.cft.croudfounding.repository.model.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final UserRepository sampleRepository;

    public List<UserEntity> getAllSample() {
        return sampleRepository.findAll();
    }
}
