package ru.otus.spring.hw.library.service;

import ru.otus.spring.hw.library.domain.Book;

import java.util.List;
import java.util.Set;

public interface BookService {
    void createBook(Book book, Set<String> genreNames, Set<String> authorsNames);

    void deleteBook(String bookTitle);

    List<Book> findAll();

    List<Book> findByTitle(String title);

    Set<Book> findByAuthorName(String title);

    Set<Book> findByGenreName(String title);

    Book findById(int id);

    void updateBook(long bookId, String bookTitle, Set<String> genresList, Set<String> authorsList);

}
