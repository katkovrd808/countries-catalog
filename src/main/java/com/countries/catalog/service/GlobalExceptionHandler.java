package com.countries.catalog.service;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleJsonParseError(HttpMessageNotReadableException e) {
    return ResponseEntity.badRequest().body("JSON parse error: " + e.getMessage());
  }
}
