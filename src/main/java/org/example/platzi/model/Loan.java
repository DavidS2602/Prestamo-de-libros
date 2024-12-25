package org.example.platzi.model;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    private String id;
    private Student student;
    private Book book;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean isLoan;

    public Loan(Student student, Book book, boolean isLoan) {
        this.id = UUID.randomUUID().toString();
        this.student = student;
        this.book = book;
        this.isLoan = isLoan;
        this.loanDate = LocalDate.now();
        this.returnDate = loanDate.plusDays(7);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isLoan() {
        return isLoan;
    }

    public void setLoan(boolean loan) {
        isLoan = loan;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", student=" + student +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                ", isLoan=" + isLoan +
                '}';
    }
}
