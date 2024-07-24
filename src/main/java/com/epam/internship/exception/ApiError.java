package com.epam.internship.exception;

import java.time.LocalDateTime;

public record ApiError(
        String path,
        String message,
        String status,
        LocalDateTime localDateTime
){}
