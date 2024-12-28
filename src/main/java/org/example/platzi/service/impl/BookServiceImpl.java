package org.example.platzi.service.impl;

import org.example.platzi.exceptions.BookListEmpty;
import org.example.platzi.exceptions.BookNotFoundException;
import org.example.platzi.exceptions.DuplicateBookException;
import org.example.platzi.exceptions.ErrorMessages;
import org.example.platzi.model.Book;
import org.example.platzi.model.Loan;
import org.example.platzi.service.IBookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements IBookService {
    private final List<Book> books = new ArrayList<>();

    @Override
    public void registerBook(Book book, Loan loan) {
        boolean isDuplicateBooks = books.stream()
                .anyMatch(b -> b.getIsbn().equals(book.getIsbn()));


        if (isDuplicateBooks) {
            throw new DuplicateBookException(
                    ErrorMessages.DUPLICATE_BOOK.formatMessage(book.getIsbn())
            );
        }
        books.add(book);
    }

    @Override
    public List<Book> getAllBooks(Loan loan) {

        if (books.isEmpty()) {
            throw new BookListEmpty(
                    ErrorMessages.BOOK_LIST_EMPTY.formatMessage()
            );
        }

        return books;
    }

    @Override
    public Optional<Book> findBookByISBN(String isbn, Loan loan) {
        if (books.isEmpty()) {
            throw new BookListEmpty(
                    ErrorMessages.BOOK_LIST_EMPTY.formatMessage()
            );
        }

        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();

        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException(
                    ErrorMessages.BOOK_NOT_FOUND.formatMessage(isbn)
            );
        }
        return optionalBook;
    }
}
