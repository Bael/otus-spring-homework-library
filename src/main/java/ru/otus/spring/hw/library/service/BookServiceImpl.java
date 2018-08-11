package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Comment;
import ru.otus.spring.hw.library.domain.Genre;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.exceptions.NotFoundException;
import ru.otus.spring.hw.library.repository.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreService genreService;
    private WriterService writerService;

    public BookServiceImpl(BookRepository bookRepository, GenreService genreService, WriterService writerService) {
        this.bookRepository = bookRepository;
        this.genreService = genreService;
        this.writerService = writerService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<Book> findById(int id) {

        return bookRepository.findById((long) id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBook(long bookId, String bookTitle, Set<String> genresNames, Set<String> authorsNames) throws NotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Not found book"));

        book.setTitle(bookTitle);
        prepareBookLinks(book, genresNames, authorsNames);
        bookRepository.save(book);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCommentByBookTitle(String bookTitle, String commentContent) throws Exception {

        Comment comment = new Comment();
        comment.setContent(commentContent);

        List<Book> books = bookRepository.findByTitle(bookTitle);
        if (books.size() > 0) {
            Book book = books.get(0);
            book.addComment(comment);
            bookRepository.save(book);
        } else {
            throw new Exception("Not sure where to add Comment! " + books.size() + " books founded by title" + bookTitle);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void prepareBookLinks(Book book, Set<String> genresNames, Set<String> authorsNames) {
        book.getAuthors().clear();
        book.getGenres().clear();

        Set<Genre> genres = genresNames.stream()
                .map(s -> genreService.ensureGenre(s))
                .collect(Collectors.toSet());

        genres.forEach(book::addGenre);
        Set<Writer> authors = authorsNames.stream()
                .map(s -> writerService.ensureWriter(s))
                .collect(Collectors.toSet());

        authors.forEach(book::addAuthor);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createBook(Book book, Set<String> genreNames, Set<String> authorsNames) {
        prepareBookLinks(book, genreNames, authorsNames);
        bookRepository.save(book);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBook(String title) {
        List<Book> books = bookRepository.findByTitle(title);
        books.forEach(book -> bookRepository.delete(book));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleLike(title);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Set<Book> findByAuthorName(String name) {
        Writer writer = writerService.findByName(name);
        if (writer != null) {
            return writer.getBooks();
        } else {
            return new HashSet<>();
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Set<Book> findByGenreName(String name) {

        Genre genre = genreService.findByName(name);
        if (genre == null) {
            return new HashSet<>();
        } else {
            return genre.getBooks();
        }
    }
}
