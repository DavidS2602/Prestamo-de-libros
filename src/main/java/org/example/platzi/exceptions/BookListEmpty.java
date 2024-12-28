package org.example.platzi.exceptions;

public class BookListEmpty extends RuntimeException {
  public BookListEmpty(String message) {
    super(message);
  }
}
