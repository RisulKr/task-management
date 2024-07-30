package com.epam.internship.loader;

import com.epam.internship.converter.RegisterUserDTOConverter;
import com.epam.internship.dto.RegisterUserDTO;
import com.epam.internship.entity.Role;
import com.epam.internship.entity.User;
import com.epam.internship.exception.RoleNotFoundException;
import com.epam.internship.repository.RoleRepository;
import com.epam.internship.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class AdminLoader {

    PasswordEncoder passwordEncoder;
    RegisterUserDTOConverter registerUserDTOConverter;
    RoleRepository roleRepository;
    UserRepository userRepository;

    @PostConstruct
    public void loadAdmin(){
        if (userRepository.findByUsernameAndEnabledIsTrue("admin").isPresent()){
            log.info("Admin user already exists");
            return;
        }
        RegisterUserDTO registerUserDTO = RegisterUserDTO.builder()
                .userName("admin")
                .password(passwordEncoder.encode("admin"))
                .number("+998(000)000-00-00")
                .build();

        User admin = registerUserDTOConverter.toEntity(registerUserDTO);
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByRoleName("ADMIN")
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        roles.add(userRole);
        admin.setRoles(roles);
        admin.setEnabled(true);
        userRepository.save(admin);
        log.info("Admin user added: username={}, enabled={}", admin.getUsername(), admin.isEnabled());
    }
}
