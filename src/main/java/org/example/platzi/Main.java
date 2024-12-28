package org.example.platzi;


import org.example.platzi.exceptions.*;
import org.example.platzi.model.Book;
import org.example.platzi.model.Loan;
import org.example.platzi.model.LoanReport;
import org.example.platzi.model.Student;
import org.example.platzi.service.IBookService;
import org.example.platzi.service.ILoanService;
import org.example.platzi.service.IStudentService;
import org.example.platzi.service.impl.BookServiceImpl;
import org.example.platzi.service.impl.LoanServiceImpl;
import org.example.platzi.service.impl.StudentServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        boolean exit = false;
        IBookService bookService = new BookServiceImpl();
        IStudentService studentService = new StudentServiceImpl();
        ILoanService loanService = new LoanServiceImpl();

        do {
            System.out.println("Options Menu");
            System.out.println("1. Register Book");
            System.out.println("2. List Books");
            System.out.println("3. Search Book by ISBN");
            System.out.println("4. Register Student");
            System.out.println("5. List Students");
            System.out.println("6. Search Student by DNI");
            System.out.println("7. Register Loan");
            System.out.println("8. List Loans by Date Range");

            System.out.println("0. Exit");

            int choice = getIntInput("Enter an option: ");
            switch (choice) {
                case 1:
                    try {
                        System.out.println("Register Book");
                        System.out.print("Title: ");
                        String title = reader.readLine();

                        System.out.print("Author: ");
                        String author = reader.readLine();

                        System.out.print("Year: ");
                        int year = Integer.parseInt(reader.readLine());

                        System.out.print("ISBN: ");
                        String isbn = reader.readLine();

                        Book newBook = new Book(title, author, year, isbn);
                        bookService.registerBook(newBook, null);
                        System.out.println("Book registered successfully");
                    } catch (DuplicateBookException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("List Books");
                    try {
                        bookService.getAllBooks(null).forEach(System.out::println);
                    } catch (BookListEmpty e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Search Book by ISBN");
                    if (bookService.getAllBooks(null).isEmpty()) {
                        throw new BookListEmpty("No books registered");
                    }
                    System.out.println("Enter ISBN: ");
                    String isbn = reader.readLine();

                    try {
                        bookService.findBookByISBN(isbn, null)
                                .ifPresentOrElse(
                                        System.out::println,
                                        () -> {
                                            throw new BookNotFoundException(isbn);
                                        }
                                );
                    } catch (BookListEmpty e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.println("Register Student");
                        System.out.print("Name: ");
                        String name = reader.readLine();

                        System.out.print("DNI: ");
                        String dni = reader.readLine();

                        studentService.createStudent(new Student(name, dni));
                        System.out.println("Student registered successfully");
                    } catch (DuplicateStudentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("List Students");
                    try {
                        studentService.getAllStudents().forEach(System.out::println);
                    } catch (StudentListEmpty e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Search Student by DNI");
                    try {
                        if (studentService.getAllStudents().isEmpty()) {
                            throw new StudentListEmpty("No students registered");
                        }
                        System.out.print("Enter DNI: ");
                        String dni = reader.readLine();

                        studentService.findStudentByDNI(dni)
                                .ifPresentOrElse(
                                        System.out::println,
                                        () -> {
                                            throw new StudentNotFoundException(dni);
                                        });
                    } catch (StudentListEmpty e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 7:
                    System.out.println("Register Loan");
                    try {
                        System.out.print("Enter Student DNI: ");
                        String studentDNI = reader.readLine();

                        System.out.print("Enter Book ISBN: ");
                        String bookISBN = reader.readLine();

                        Student student = studentService.findStudentByDNI(studentDNI)
                                .orElseThrow(() -> new StudentNotFoundException(studentDNI));

                        Book book = bookService.findBookByISBN(bookISBN, null)
                                .orElseThrow(() -> new BookNotFoundException(bookISBN));

                        Loan loan = new Loan(student, book, true);
                        loanService.registerLoan(loan, student, LocalDate.now());

                        System.out.println("Loan registered successfully");

                    } catch (DuplicateLoanException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 8:
                    try {
                        System.out.println("List Loans by Date Range");

                        System.out.print("Enter start date (yyyy-MM-dd): ");
                        LocalDate startDate = LocalDate.parse(reader.readLine());

                        System.out.print("Enter end date (yyyy-MM-dd): ");
                        LocalDate endDate = LocalDate.parse(reader.readLine());

                        // Get loans within the range
                        List<LoanReport> reports = loanService.reportLoans(startDate, endDate);
                        if (reports.isEmpty()) {
                            System.out.println("No loans found in the specified range.");
                        } else {
                            reports.forEach(System.out::println);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 0:
                    break;
            }

        } while (true);
    }

    private static int getIntInput(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Enter a valid number");
            return getIntInput(message);
        }
    }
}