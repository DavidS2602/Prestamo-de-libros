package org.example.platzi.exceptions;

public class LoanListEmpty extends RuntimeException {
    public LoanListEmpty(String message) {
        super(message);
    }
}
