package ru.otus.spring.hw.library.repository;

import ru.otus.spring.hw.library.domain.Book;

import java.util.List;

public interface BookRepository {

    void createBook(Book book);

    List<Book> findByTitle(String title);

    List<Book> findByTitleLike(String title);

    List<Book> findAll();

    List<Book> findByAuthorName(String title);

    List<Book> findByGenreName(String title);

    Book findById(long id);

    void deleteById(long id);

    void updateBook(Book book);



}
