package ru.otus.spring.hw.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.repository.BookRepository;
import ru.otus.spring.hw.library.repository.WriterRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WriterServiceImpl implements WriterService {

    private WriterRepository writerRepository;
    private BookRepository bookRepository;

    public WriterServiceImpl(WriterRepository writerRepository, BookRepository bookRepository) {
        this.writerRepository = writerRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Set<Writer> findAll() {
        return (Set<Writer>) writerRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Writer ensureWriter(String name) {
        Writer w = writerRepository.findByName(name);
        if (w == null) {
            w = new Writer(name);
            writerRepository.save(w);
        }
        return writerRepository.findByName(name);
    }

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Set<Writer> authorsByGenre(String genre) {
        List<Book> books = bookRepository.findByGenresContaining(genre);
        final Set<Writer> writers = new HashSet<>();

        books.stream().map(book -> book.getAuthors()).forEach(writers1 -> writers.addAll(writers1));
        return writers;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Set<Writer> getAuthorsByBookId(String id) {
        Book book = bookRepository.findById(id).orElse(new Book());
        return book.getAuthors();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Writer findByName(String name) {
        return writerRepository.findByName(name);
    }
}
