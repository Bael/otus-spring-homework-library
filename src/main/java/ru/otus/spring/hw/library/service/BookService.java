package ru.otus.spring.hw.library.service;

import ru.otus.spring.hw.library.domain.Book;

import java.util.List;

public interface BookService {
    void createBook(Book book);

    void deleteBook(String bookTitle);

    List<Book> findAll();

    List<Book> findByTitle(String title);

    List<Book> findByAuthorName(String title);

    List<Book> findByGenreName(String title);

    Book findById(int id);

    void updateBook(Book book);

}
