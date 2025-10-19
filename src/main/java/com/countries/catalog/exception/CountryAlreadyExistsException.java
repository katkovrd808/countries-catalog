package com.countries.catalog.exception;

public class CountryAlreadyExistsException extends RuntimeException {
  public CountryAlreadyExistsException(String message) {
    super(message);
  }
}
