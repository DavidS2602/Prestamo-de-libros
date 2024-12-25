package org.example.platzi.model;

import java.time.LocalDate;

public record LoanReport(
        String title,
        LocalDate loanDate,
        LocalDate returnDate,
        String studentName
) {
}
