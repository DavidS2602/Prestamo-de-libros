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
            System.out.println("Menú de opciones");
            System.out.println("1. Registrar Libro");
            System.out.println("2. Listar Libros");
            System.out.println("3. Buscar Libro por ISBN");
            System.out.println("4. Registar Estudiante");
            System.out.println("5. Listar Estudiantes");
            System.out.println("6. Buscar Estudiante por DNI.");
            System.out.println("7. Registrar Préstamo.");
            System.out.println("8. Listar Préstamos por Rango de Fecha");

            System.out.println("0. Salir");

            int choice = getIntInput("Ingrese una opción: ");
            switch (choice) {
                case 1:
                    try {
                        System.out.println("Registrar Libro");
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
                        System.out.println("Libro registrado exitosamente");
                    } catch (DuplicateBookException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Listar Libros");
                    try {
                        bookService.getAllBooks(null).forEach(System.out::println);
                    } catch (BookListEmpty e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Ingrese ISBN: ");
                    String isbn = reader.readLine();

                    try {
                    bookService.findBookByISBN(isbn, null)
                            .ifPresentOrElse(System.out::println, () -> System.out.println("Libro no encontrado"));
                    } catch (BookListEmpty e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.println("Registrar Estudiante");
                        System.out.print("Nombre: ");
                        String name = reader.readLine();

                        System.out.print("DNI: ");
                        String dni = reader.readLine();

                        studentService.createStudent(new Student(name, dni));
                        System.out.println("Estudiante registrado exitosamente");
                    } catch (DuplicateStudentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Listar Estudiantes");
                    studentService.getAllStudents().forEach(System.out::println);
                    break;

                case 6:
                    try {
                        System.out.println("Buscar estudiante por DNI");
                        System.out.print("Ingrese DNI: ");
                        String dni = reader.readLine();

                        studentService.findStudentByDNI(dni)
                                .ifPresentOrElse(System.out::println, () -> System.out.println("Estudiante no encontrado"));
                    } catch (StudentNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 7:
                    System.out.println("Registrar Préstamo");
                    try {
                        System.out.print("Ingrese el DNI del estudiante: ");
                        String studentDNI = reader.readLine();

                        System.out.print("Ingrese el ISBN del libro: ");
                        String bookISBN = reader.readLine();

                        Student student = studentService.findStudentByDNI(studentDNI)
                                .orElseThrow(() -> new StudentNotFoundException(studentDNI));

                        Book book = bookService.findBookByISBN(bookISBN, null)
                                .orElseThrow(() -> new BookNotFoundException(bookISBN));

                        Loan loan = new Loan(student, book, true);
                        loanService.registerLoan(loan, student, LocalDate.now());

                        System.out.println("Préstamo registrado exitosamente");

                    } catch (DuplicateLoanException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 8:
                    try {
                        System.out.println("Listar Préstamos por Rango de Fecha");

                        System.out.print("Ingrese la fecha de inicio (yyyy-MM-dd): ");
                        LocalDate startDate = LocalDate.parse(reader.readLine());

                        System.out.print("Ingrese la fecha de fin (yyyy-MM-dd): ");
                        LocalDate endDate = LocalDate.parse(reader.readLine());

                        // Obtener préstamos dentro del rango
                        List<LoanReport> reports = loanService.reportLoans(startDate, endDate);
                        if (reports.isEmpty()) {
                            System.out.println("No se encontraron préstamos en el rango especificado.");
                        } else {
                            reports.forEach(System.out::println);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

            }

        } while (true);
    }

    private static int getIntInput(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Ingrese un número válido");
            return getIntInput(message);
        }
    }
}