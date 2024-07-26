package com.epam.internship.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    public static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    public static final HttpStatus CONFLICT = HttpStatus.CONFLICT;
    public static final HttpStatus SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleTaskNotFound(Throwable exception, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getLocalizedMessage(),
                NOT_FOUND.name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(Throwable exception, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getLocalizedMessage(),
                NOT_FOUND.name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNameNotFound(Throwable exception, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getLocalizedMessage(),
                NOT_FOUND.name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ApiError> handleUserRegistration(Throwable exception, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getLocalizedMessage(),
                BAD_REQUEST.name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument( HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "Bad request",
                BAD_REQUEST.name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiError> handleRoleNotFound(Throwable exception, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getLocalizedMessage(),
                NOT_FOUND.name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(Throwable exception, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getLocalizedMessage(),
                CONFLICT.name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, CONFLICT);
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ApiError> handleJpaSystemException(Throwable exception, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception.getLocalizedMessage(),
                SERVER_ERROR.name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, SERVER_ERROR);
    }
}
