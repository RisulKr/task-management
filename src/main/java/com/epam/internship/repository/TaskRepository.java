package com.epam.internship.repository;

import com.epam.internship.entity.Task;
import com.epam.internship.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByIdAndUser_UsernameAndIsDeletedFalse(Long id, String username);
    Page<Task> getAllByUser_UsernameAndIsDeletedFalse(String username, Pageable pageable);
    Page<Task> getAllByUser_UsernameAndStatusAndIsDeletedFalseOrderByDueDateAsc(String username, Status status, Pageable pageable);
    Page<Task> getAllByUser_UsernameAndIsFavouriteTrueAndIsDeletedFalse(String username, Pageable pageable);
}
