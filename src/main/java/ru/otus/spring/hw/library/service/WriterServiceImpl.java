package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.library.dao.WriterDAO;
import ru.otus.spring.hw.library.domain.Writer;

import java.util.List;

@Service
public class WriterServiceImpl implements WriterService {

    private WriterDAO writerDAO;

    public WriterServiceImpl(WriterDAO writerDAO) {
        this.writerDAO = writerDAO;
    }

    @Override
    public void createWriter(Writer writer) {
        writerDAO.createWriter(writer);
    }

    @Override
    public List<Writer> findAll() {
        return writerDAO.findAll();
    }

    @Override
    public Writer ensureWriter(String name) {
        return writerDAO.ensureByName(name);
    }

    @Override
    public List<Writer> authorsByGenre(String genre) {
        return writerDAO.authorsByGenre(genre);
    }
}
