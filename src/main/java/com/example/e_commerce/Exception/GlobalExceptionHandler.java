package com.example.e_commerce.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<String> handleResourceAlreadyExists(
          ResourceAlreadyExistsException ex) {

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDuplicate(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body("Data integrity violation: duplicate value or invalid reference");
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleUserNotFound(UsernameNotFoundException ex) {
    return "User not found";
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public String handleBadCredentials(BadCredentialsException ex) {
    return "Invalid username or password";
  }


}