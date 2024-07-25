package com.epam.internship.repository;

import com.epam.internship.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

        Optional<User> findByUsernameAndEnabledIsTrue(String username);
        List<User> findAllByEnabledIsTrue();
        Optional<User> findByIdAndEnabledIsTrue(Integer id);

}
