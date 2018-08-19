package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Comment;
import ru.otus.spring.hw.library.domain.Genre;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.exceptions.NotFoundException;
import ru.otus.spring.hw.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private WriterService writerService;

    public BookServiceImpl(BookRepository bookRepository, WriterService writerService) {
        this.bookRepository = bookRepository;

        this.writerService = writerService;
    }


    @Override
    public void updateBook(String oldBookTitle, String bookTitle, Set<String> genresNames, Set<String> authorsNames) throws NotFoundException {
        Book book = bookRepository.findByTitle(oldBookTitle).orElseThrow(() -> new NotFoundException("Not found book"));

        book.setTitle(bookTitle);
        prepareBookLinks(book, genresNames, authorsNames);
        bookRepository.save(book);
    }

    @Override
    public void addCommentByBookTitle(String bookTitle, String commentContent) {

        Comment comment = new Comment();
        comment.setContent(commentContent);

        Book book = bookRepository.findByTitle(bookTitle).orElseThrow(() -> new NotFoundException("Not found book"));
        book.addComment(comment);
        bookRepository.save(book);
    }

    public void prepareBookLinks(Book book, Set<String> genresNames, Set<String> authorsNames) {


        Set<Genre> genres = genresNames.stream()
                .map(s -> new Genre(s))
                .collect(Collectors.toSet());
        book.setGenres(genres);

        book.getAuthors().clear();
        Set<Writer> authors = authorsNames.stream()
                .map(s -> writerService.ensureWriter(s))
                .collect(Collectors.toSet());

        authors.forEach(book::addAuthor);
    }


    @Override
    public void createBook(Book book, Set<String> genreNames, Set<String> authorsNames) {
        prepareBookLinks(book, genreNames, authorsNames);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
        }
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findByTitleLike(String title) {
        return bookRepository.findByTitleLike(title);
    }

    @Override
    public Set<Book> findByAuthorName(String name) {
        return null;
        /*
        Writer writer = writerService.findByName(name);
        if (writer != null) {
            return writer.getBooks();
        } else {
            return new HashSet<>();
        }*/

    }

    @Override
    public Set<Book> findByGenreName(String name) {
        return null;
    /*
        Genre genre = genreService.findByName(name);
        if (genre == null) {
            return new HashSet<>();
        } else {
            return genre.getBooks();
        }*/
    }


}
