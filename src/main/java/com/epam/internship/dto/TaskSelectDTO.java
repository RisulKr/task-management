package com.epam.internship.dto;

import com.epam.internship.enums.Priority;
import com.epam.internship.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskSelectDTO {
    private long id;
    private String title;
    private String content;
    private Status status;
    private Priority priority;
    private String dueDate;
    private String createDate;
    private boolean isFavourite;
}
