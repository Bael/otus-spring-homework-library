package ru.otus.spring.hw.library.service;

import ru.otus.spring.hw.library.domain.Book;

import java.util.List;

public interface BookService {
    void createBook(Book book);

    void deleteBookById(long bookId);

    List<Book> findAll();

}
