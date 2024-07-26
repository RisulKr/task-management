package com.epam.internship.dto;

import com.epam.internship.enums.Priority;
import com.epam.internship.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    @Size(min = 3, message = "Title must be at least 3 characters long")
    @NotEmpty(message = "Title can't be empty")
    private String title;
    @Size(min = 4, message = "Title must be at least 4 characters long")
    @NotEmpty(message = "Content can't be empty")
    private String content;
    @NotNull(message = "Content can't be null")
    private Status status;
    @NotNull(message = "Priority can't be null")
    private Priority priority;
    @NotNull(message = "Due date can't be null")
    private String dueDate;
    private boolean isFavourite;
}
