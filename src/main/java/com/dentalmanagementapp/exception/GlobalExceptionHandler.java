package com.dentalmanagementapp.exception;

import com.dentalmanagementapp.exception.custom.EntityAlreadyExistsException;
import com.dentalmanagementapp.exception.custom.NoAuthorizedUserException;
import com.dentalmanagementapp.exception.custom.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<Object> createResponseEntity(String message, HttpStatus status, Exception exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        body.put("status", status.value());
        logger.error("Error occurred: {}", message, exception);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.UNAUTHORIZED, exception);
    }

    @ExceptionHandler(NoAuthorizedUserException.class)
    public ResponseEntity<Object> handleNoAuthorizedUserException(NoAuthorizedUserException exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.UNAUTHORIZED, exception);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        return createResponseEntity(exception.getMessage(), HttpStatus.CONFLICT, exception);
    }
}
