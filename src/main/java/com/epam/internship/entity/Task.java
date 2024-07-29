package com.epam.internship.entity;


import com.epam.internship.enums.Priority;
import com.epam.internship.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @CreationTimestamp()
    @Column(name = "created_datetime", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "due_datetime", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "deleted_datetime")
    private LocalDateTime deleteDate;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "is_favourite", nullable = false)
    private boolean isFavourite;

}
