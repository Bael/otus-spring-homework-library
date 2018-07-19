package ru.otus.spring.hw.library.dao;

import ru.otus.spring.hw.library.domain.Book;

import java.util.List;

public interface BookDAO {

    long createBook(Book book);

    List<Book> findByTitle(String title);

    List<Book> findAll();

    List<Book> findByAuthorName(String title);

    List<Book> findByGenreName(String title);
    Book findById(long id);

    void deleteById(long id);
    void updateBook(Book book);



}
