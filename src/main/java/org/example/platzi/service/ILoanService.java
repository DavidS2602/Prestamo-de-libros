package org.example.platzi.service;

import org.example.platzi.model.Loan;
import org.example.platzi.model.LoanReport;
import org.example.platzi.model.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ILoanService {
    void registerLoan(Loan loan, Student student, LocalDate date);
    List<Loan> listAllLoans();
    List<Loan> filterLoansByDNIStudents(String studentDNI);
    List<LoanReport> reportLoans(LocalDate startDate, LocalDate endDate);
}
