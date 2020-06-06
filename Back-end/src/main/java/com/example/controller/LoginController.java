package com.example.controller;

import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.login.LoginDto;
import com.example.dto.login.MainLoginDto;
import com.example.error.ApiError;
import com.example.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/Unity")
@RequiredArgsConstructor
@Api(tags = "Login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "Login user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User log in", response = MainLoginDto.class),
            @ApiResponse(code = 400, message = "Validation error", response = ApiError.class)
    })
    public MainLoginDto register(@Valid @RequestBody LoginDto loginDto) {
        return loginService.login(loginDto);
    }

    @GetMapping("/apiKey/user/{userId}")
    @ApiOperation(value = "Get ApiKey by user id")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ApiKey found", response = ApiKeyDto.class),
            @ApiResponse(code = 404, message = "Non-existing user id", response = ApiError.class)
    })
    public ApiKeyDto getApiKeyByUserId(@PathVariable("userId") int userId) {
        return loginService.getApiKeyByUserId(userId);
    }

    @PostMapping("check/apiKey/user/{userId}")
    @ApiOperation(value = "Check if ApiKey exists")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ApiKey found"),
            @ApiResponse(code = 404, message = "Non-existing user id", response = ApiError.class)
    })
    public void checkUserByIdAndApiKey(@PathVariable("userId") int userId, @RequestBody ApiKeyDto apiKeyDto) {
        loginService.checkUserByIdAndApiKey(userId, apiKeyDto);
    }

}
