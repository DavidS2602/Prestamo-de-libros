package org.example.platzi.model;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    private String id;
    private Book book;
    private Student student;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(Book book, Student student, boolean returned) {
        this.id = UUID.randomUUID().toString();
        this.book = book;
        this.student = student;
        this.returned = returned;
        this.loanDate = LocalDate.now();
        this.returnDate = loanDate.plusDays(7);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", book=" + book +
                ", student=" + student +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                ", returned=" + returned +
                '}';
    }
}
