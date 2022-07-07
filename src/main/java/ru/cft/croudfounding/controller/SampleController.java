package ru.cft.croudfounding.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.croudfounding.model.ErrorDTO;
import ru.cft.croudfounding.repository.model.User;
import ru.cft.croudfounding.service.SampleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class SampleController {

    private SampleService sampleService;

    @Operation(summary = "пример описания",
            responses = {
                     @ApiResponse(responseCode = "404",
                            description = "Невалидная схема документа или входные данные не верны.",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            })
    @GetMapping("/get/all")
    public List<User> getAll() {
        return sampleService.getAllSample();
    }



}
