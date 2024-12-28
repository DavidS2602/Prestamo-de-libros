package org.example.platzi.service;

import org.example.platzi.model.Book;
import org.example.platzi.model.Loan;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    void registerBook(Book book, Loan loan);
    List<Book> getAllBooks(Loan loan);
    Optional<Book> findBookByISBN(String isbn, Loan loan);
}
