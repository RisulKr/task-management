package com.epam.internship.repository;

import com.epam.internship.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByIdAndUser_UsernameAndIsDeletedFalse(Long id, String username);
    List<Task> getAllByUser_UsernameAndIsDeletedFalse(String username);
}
