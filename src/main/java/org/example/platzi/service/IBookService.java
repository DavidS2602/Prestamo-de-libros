package org.example.platzi.service;

import org.example.platzi.model.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    void registerBook(Book book);
    List<Book> getAllBooks();
    Optional<Book> findBookByISBN(String isbn);
}
