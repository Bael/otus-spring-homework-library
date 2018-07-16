package ru.otus.spring.hw.library.dao;

import ru.otus.spring.hw.library.domain.Book;

import java.util.List;

public interface BookDAO {

    long createBook(Book book);
    List<Book> findBooksByTitle(String title);
    List<Book> findBooksByAuthor(String title);
    List<Book> findBooksByGenres(String title);
    Book findById(long id);
    Book deleteById(long id);
    void updateBook(Book book);



}
