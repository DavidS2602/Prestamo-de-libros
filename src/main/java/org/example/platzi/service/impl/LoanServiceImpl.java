package org.example.platzi.service.impl;

import org.example.platzi.exceptions.ErrorMessages;
import org.example.platzi.model.Loan;
import org.example.platzi.model.LoanReport;
import org.example.platzi.model.Student;
import org.example.platzi.service.ILoanService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanServiceImpl implements ILoanService {
    private final List<Loan> loans = new ArrayList<>();

    @Override
    public void registerLoan(Loan loan, Student student, LocalDate date) {
        boolean isDuplicateLoan = loans.stream()
                .anyMatch(l -> l.getStudent().getDNI().equals(student.getDNI())
                        && l.getBook().getTitle().equals(loan.getBook().getTitle())
                        && l.getLoanDate().equals(date)
                );
        if (isDuplicateLoan) {
            ErrorMessages.DUPLICATE_LOAN.formatMessage(student.getDNI());
        }
        loans.add(loan);
        System.out.println("Load register successful");
    }

    @Override
    public List<Loan> listAllLoans() {
        return List.of();
    }

    @Override
    public List<Loan> filterLoansByDNIStudents(String studentDNI) {
        return loans.stream()
                .filter(loan -> loan.getStudent().getDNI().equals(studentDNI))
                .toList();
    }

    @Override
    public List<LoanReport> reportLoans(LocalDate startDate, LocalDate endDate) {
        return loans.stream()
                .filter(
                        loan -> (loan.getLoanDate().isEqual(startDate) ||
                                loan.getLoanDate().isAfter(startDate))
                                && ((loan.getReturnDate().isEqual(endDate) ||
                                loan.getLoanDate().isBefore(endDate))
                        )).map(
                        loan -> new LoanReport(
                                loan.getBook().getTitle(),
                                loan.getLoanDate(),
                                loan.getReturnDate(),
                                loan.getStudent().getName()
                        )).toList();
    }
}
