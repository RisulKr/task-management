package com.epam.internship.service.impl;

import com.epam.internship.dto.UserDto;
import com.epam.internship.entity.Role;
import com.epam.internship.entity.User;
import com.epam.internship.exception.RoleNotFoundException;
import com.epam.internship.exception.UserRegistrationException;
import com.epam.internship.mapper.UserMapper;
import com.epam.internship.repository.RoleRepository; // Ensure you have a repository for roles
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.RegisterService;
import com.epam.internship.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class RegisterImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public String registerUser(UserDto userDto) {
        try {
            User userEntity = userMapper.userDtoToUser(userDto);
            Role userRole = roleRepository.findByRoleName("USER")
                    .orElseThrow(() -> new RoleNotFoundException("Role 'USER' not found"));
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            userEntity.setRoles(roles);
            userRepository.save(userEntity);
            return MessageUtils.register_success;
        } catch (DataIntegrityViolationException | JpaSystemException | RoleNotFoundException exception) {
            log.info(exception.getMessage());
            throw new UserRegistrationException(exception.getMessage(), exception);
        }
    }
}
