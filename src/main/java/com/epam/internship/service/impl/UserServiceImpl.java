package com.epam.internship.service.impl;

import com.epam.internship.converter.UserDTOConverter;
import com.epam.internship.dto.UserDTO;
import com.epam.internship.entity.Role;
import com.epam.internship.entity.User;
import com.epam.internship.exception.*;
import com.epam.internship.repository.RoleRepository;
import com.epam.internship.repository.UserRepository;
import com.epam.internship.service.UserService;
import com.epam.internship.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

import static com.epam.internship.utils.MessageUtils.role_assignment_message;
import static com.epam.internship.utils.MessageUtils.role_failedAssignment_message;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private UserDTOConverter userDTOConverter;
    private final RoleRepository roleRepository;


    @Override
    public String assignAdmin(String username,Integer id) {
        if (isInvalidID(id) || Objects.isNull(username))
            throw new InvalidParameterException(role_failedAssignment_message);
        User user = userRepository.findByUsernameAndId(username, id)
                    .orElseThrow(() -> new UserNotFoundException(username));
        Role role = roleRepository.findByRoleName("ADMIN")
                    .orElseThrow(() -> new RoleNotFoundException(username));
        user.getRoles().add(role);

        userRepository.save(user);
        log.info("User with 'ADMIN' role assigned, {}" , user.getRoles());
        return role_assignment_message;
    }

    @Override
    public String removeUser(Integer id) {
            User user = userRepository.findByIdAndEnabledIsTrue(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            UserDTO userToBeDeleted = findUser(id);
            if (currentUsername.equals(userToBeDeleted.getUsername())) {
                throw new IllegalOperationException("You cannot delete yourself");
            }
            boolean isDeletingAdmin = user.getRoles().stream()
                .anyMatch(role -> "ADMIN".equals(role.getRoleName()));

            boolean isAuthenticatedAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
            if (isDeletingAdmin && isAuthenticatedAdmin) {
                    throw new IllegalOperationException("You cannot delete another ADMIN");
            }
            user.getRoles().clear();
            user.setEnabled(false);
            userRepository.save(user);
            userRepository.flush();
            return MessageUtils.removed_message;
    }


    public UserDTO findUser(Integer id) {
            if (isInvalidID(id)) throw new InvalidParameterException(MessageUtils.invalid_id);
            return userRepository.findByIdAndEnabledIsTrue(id)
                    .map(userDTOConverter::toDto)
                    .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
    }

    public List<UserDTO> findAllUsers() {
       List<User> users = userRepository.findAllByEnabledIsTrue()
               .orElseThrow(UserNotFoundException::new);
       return userDTOConverter.toDtoList(users);
    }

    private boolean isInvalidID(Integer id) {
        return id == null || id <= 0;
    }
}

