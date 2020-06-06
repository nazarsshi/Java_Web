package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.dto.authorization.AuthDto;
import com.example.error.BadCredentialsException;
import com.example.error.EntityNotFountException;
import com.example.model.TrustLevel;
import com.example.model.User;
import com.example.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

import static com.example.utils.EncodingUtils.encode;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserDao userDao;

    private final ModelMapper modelMapper;

    @Override
    public void register(AuthDto authDto) {
        try {
            User user = getByEmail(authDto.getEmail());
            if (user != null) {
                throw new BadCredentialsException("User already exists");
            }
        } catch (EntityNotFountException ignored) {
        }
        authDto.setPassword(encode(authDto.getPassword()));
        User registered = modelMapper.map(authDto, User.class);
        registered.setTrustLevel(TrustLevel.NOVICE);
        userDao.save(registered);
    }

    private User getByEmail(String email) {
        try {
            return userDao.getByEmail(email);
        } catch (NoResultException | EmptyResultDataAccessException ex) {
            throw new EntityNotFountException("User was not found with email: " + email);
        }
    }

}