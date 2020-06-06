package com.example.controller;

import com.example.dto.authorization.AuthDto;
import com.example.error.ApiError;
import com.example.service.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/Unity")
@RequiredArgsConstructor
@Api(tags = "Registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    @ApiOperation(value = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "New user created"),
            @ApiResponse(code = 400, message = "Validation error", response = ApiError.class)
    })
    public void register(@Valid @RequestBody AuthDto authDto) {
        registrationService.register(authDto);
    }

}
