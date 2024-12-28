package org.example.platzi.exceptions;

public class StudentListEmpty extends RuntimeException {
    public StudentListEmpty(String message) {
        super(message);
    }
}
