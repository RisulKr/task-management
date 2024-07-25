package com.epam.internship.service.impl;

import com.epam.internship.converter.UserDTOConverter;
import com.epam.internship.dto.UserDTO;
import com.epam.internship.entity.User;
import com.epam.internship.exception.*;
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
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private UserDTOConverter userDTOConverter;

    @Override
    public String removeUser(Integer id) {
        try {
            User user = userRepository.findByIdAndEnabledIsTrue(id).get();
            user.getRoles().clear();
            user.setEnabled(false);
            userRepository.save(user);
            userRepository.flush();
            return MessageUtils.removed_message;
        }catch (EmptyResultDataAccessException e) {
            log.info(e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
    }


    public UserDTO findUser(Integer id) {
        try {
            if (id <= 0) throw new InvalidParameterException(MessageUtils.invalid_id);
            return userRepository.findByIdAndEnabledIsTrue(id)
                    .map(userDTOConverter::toDto)
                    .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.info(emptyResultDataAccessException.getMessage());
            throw new UserNotFoundException(MessageUtils.user_notFound_message, emptyResultDataAccessException);
        }
    }

    public List<UserDTO> findAllUsers() {
        try {
            return  userRepository.findAllByEnabledIsTrue()
                    .stream()
                    .map(userDTOConverter::toDto)
                    .toList();
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.info(emptyResultDataAccessException.getMessage());
            throw new UserNotFoundException(MessageUtils.user_notFound_message, emptyResultDataAccessException);
        }
    }
}

