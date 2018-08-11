package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.repository.BookRepository;
import ru.otus.spring.hw.library.repository.WriterRepository;

import java.util.HashSet;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Set<Writer> authorsByGenre(String genre) {
        return new HashSet<>(writerRepository.authorsByGenre(genre));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Set<Writer> getAuthorsByBookId(long id) {
        Book book = bookRepository.findById(id).orElse(new Book());
        return book.getAuthors();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Writer findByName(String name) {
        return writerRepository.findByName(name);
    }
}
