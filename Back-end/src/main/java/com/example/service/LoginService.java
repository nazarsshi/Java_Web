package com.example.service;

import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.login.LoginDto;
import com.example.dto.login.MainLoginDto;

public interface LoginService {

    MainLoginDto login(LoginDto loginDto);

    ApiKeyDto getApiKeyByUserId(int id);

    void checkUserByIdAndApiKey(int userId, ApiKeyDto apiKeyDto);

}
