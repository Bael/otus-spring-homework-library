package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.library.dao.BookDAO;
import ru.otus.spring.hw.library.domain.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookDAO bookDAO;


    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public void createBook(Book book) {
        bookDAO.createBook(book);

    }

    @Override
    public Book findById(int id) {
        return bookDAO.findById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDAO.updateBook(book);
    }

    @Override
    public void deleteBook(String title) {
        List<Book> books = findByTitle(title);
        books.forEach(book -> bookDAO.deleteById(book.getId()));
    }

    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookDAO.findByTitle(title);
    }

    @Override
    public List<Book> findByAuthorName(String title) {
        return bookDAO.findByAuthorName(title);
    }

    @Override
    public List<Book> findByGenreName(String name) {
        return bookDAO.findByGenreName(name);
    }
}
