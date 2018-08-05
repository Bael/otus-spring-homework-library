package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Genre;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.repository.BookRepository;
import ru.otus.spring.hw.library.repository.GenreRepository;
import ru.otus.spring.hw.library.repository.WriterRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private WriterRepository writerRepository;

    public BookServiceImpl(BookRepository bookRepository, GenreRepository genreRepository, WriterRepository writerRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.writerRepository = writerRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Book findById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBook(long bookId, String bookTitle, Set<String> genresNames, Set<String> authorsNames) {
        Book book = bookRepository.findById(bookId);
        book.setTitle(bookTitle);
        prepareBookLinks(book, genresNames, authorsNames);
        bookRepository.updateBook(book);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void prepareBookLinks(Book book, Set<String> genresNames, Set<String> authorsNames) {
        Set<Genre> genres = genresNames.stream()
                .map(s -> genreRepository.ensureGenre(s))
                .collect(Collectors.toSet());
        Set<Writer> authors = authorsNames.stream()
                .map(s -> writerRepository.ensureByName(s))
                .collect(Collectors.toSet());
        genres.forEach(book::addGenre);
        authors.forEach(book::addAuthor);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createBook(Book book, Set<String> genreNames, Set<String> authorsNames) {
        prepareBookLinks(book, genreNames, authorsNames);
        bookRepository.createBook(book);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBook(String title) {
        List<Book> books = bookRepository.findByTitle(title);
        books.forEach(book -> bookRepository.deleteById(book.getId()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Book> findAll() {
        List<Book> list = bookRepository.findAll();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleLike(title);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Set<Book> findByAuthorName(String name) {
        Writer writer = writerRepository.findByExactName(name);
        if (writer != null) {
            return writer.getBooks();
        } else {
            return new HashSet<>();
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Set<Book> findByGenreName(String name) {

        Genre genre = genreRepository.findGenre(name);
        if (genre == null) {
            return new HashSet<>();
        } else {
            return genre.getBooks();
        }
    }
}
