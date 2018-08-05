package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Writer;
import ru.otus.spring.hw.library.repository.BookRepository;
import ru.otus.spring.hw.library.repository.WriterRepository;

import java.util.ArrayList;
import java.util.List;

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
    public void createWriter(Writer writer) {
        writerRepository.createWriter(writer);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Writer> findAll() {
        return writerRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Writer ensureWriter(String name) {
        return writerRepository.ensureByName(name);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Writer> authorsByGenre(String genre) {

        return writerRepository.authorsByGenre(genre);
    }

    @Override
    public List<Writer> getAuthorsByBookId(long id) {
        Book book = bookRepository.findById(id);
        if (book != null) {
            return new ArrayList<>(book.getAuthors());
        } else {
            return new ArrayList<>();
        }
    }
}
