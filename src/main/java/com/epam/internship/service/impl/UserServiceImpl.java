package com.epam.internship.service.impl;

import com.epam.internship.dto.UserDto;
import com.epam.internship.entity.User;
import com.epam.internship.exception.*;
import com.epam.internship.mapper.UserMapper;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.UserService;
import com.epam.internship.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public UserDto findUserByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username).get();
            return userMapper.userToUserDto(user);
        } catch (EmptyResultDataAccessException | NullPointerException exception) {
            throw new UsernameNotFoundException(exception.getMessage());
        }
    }

    @Override
    public String removeUser(Integer id) {
        try {
            userRepository.deleteById(id);
            return MessageUtils.removed_message;
        }catch (EmptyResultDataAccessException e) {
            log.info(e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
    }


    public UserDto findUser(Integer id) {
        try {
            if (id <= 0) throw new InvalidParameterException(MessageUtils.invalid_id);
            return userRepository.findById(id)
                    .map(userMapper::userToUserDto)
                    .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.info(emptyResultDataAccessException.getMessage());
            throw new UserNotFoundException(MessageUtils.user_notFound_message, emptyResultDataAccessException);
        }
    }

    public List<UserDto> findAllUsers() {
        try {
            return  userRepository.findAll()
                    .stream()
                    .map(userMapper::userToUserDto)
                    .toList();
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.info(emptyResultDataAccessException.getMessage());
            throw new UserNotFoundException(MessageUtils.user_notFound_message, emptyResultDataAccessException);
        }
    }
}

