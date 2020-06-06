package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.login.LoginDto;
import com.example.dto.login.MainLoginDto;
import com.example.error.BadCredentialsException;
import com.example.error.EntityNotFountException;
import com.example.model.User;
import com.example.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.UUID;

import static com.example.utils.EncodingUtils.decode;
import static com.example.utils.EncodingUtils.encode;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserDao userDao;

    @Override
    public MainLoginDto login(LoginDto loginDto) {
        User user;

        try {
            user = getByEmail(loginDto.getEmail());
        } catch (EntityNotFountException exception) {
            throw new BadCredentialsException("User credentials are incorrect");
        }

        UUID uuid = null;

        if (loginDto.getPassword().equals(decode(user.getPassword()))) {
            boolean checker = true;
            while (checker) {
                uuid = UUID.randomUUID();
                try {
                    userDao.getByApiKey(encode(uuid.toString()));
                } catch (NoResultException | EmptyResultDataAccessException exception) {
                    checker = false;
                }
            }
            user.setApiKey(encode(uuid.toString()));
        }
        user = userDao.update(user);
        assert uuid != null;
        return MainLoginDto
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .apiKey(uuid.toString())
                .build();
    }

    @Override
    public ApiKeyDto getApiKeyByUserId(int id) {
        User user = getById(id);
        return ApiKeyDto
                .builder()
                .apiKey(decode(user.getApiKey()))
                .build();
    }

    @Override
    public void checkUserByIdAndApiKey(int userId, ApiKeyDto apiKeyDto) {
        if (userId != getByApiKey(apiKeyDto.getApiKey())) {
            throw new BadCredentialsException("ApiKey is not valid for user with id:" + userId);
        }
    }

    private User getById(int id) {
        User user = userDao.getById(id);
        if (user == null) {
            throw new EntityNotFountException("User is not found with id = " + id);
        }
        return user;
    }

    private User getByEmail(String email) {
        try {
            return userDao.getByEmail(email);
        } catch (NoResultException | EmptyResultDataAccessException ex) {
            throw new EntityNotFountException("User wa not found with email: " + email);
        }
    }

    public int getByApiKey(String apiKey) {
        User user;
        try {
            user = userDao.getByApiKey(encode(apiKey));
        } catch (NoResultException | EmptyResultDataAccessException exception) {
            throw new BadCredentialsException("API key is invalid");
        }
        return user.getId();
    }

}