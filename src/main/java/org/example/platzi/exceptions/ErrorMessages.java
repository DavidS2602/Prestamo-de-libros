package org.example.platzi.exceptions;

public enum ErrorMessages {

    STUDENT_NOT_FOUND("Student WITH dni %s not found"),
    DUPLICATE_STUDENT("Student %s already exists"),
    BOOK_NOT_FOUND("Book with ISBN %s not found"),
    BOOK_LIST_EMPTY("Book list is empty, please add a book"),
    DUPLICATE_BOOK("Book with DNI %s already exists"),
    DUPLICATE_LOAN("Loan already exists for Book ID: $s and student ID: $s"),
    LOAN_NOT_FOUND("Loan with ID %s not found");

    private final String messageFormat;

    ErrorMessages(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String formatMessage(String message) {
        return String.format(messageFormat, message);
    }

    public String formatMessage() {
        return messageFormat;
    }
}
