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

    }

    @Override
    public void deleteBookById(long bookId) {

    }

    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }
}
