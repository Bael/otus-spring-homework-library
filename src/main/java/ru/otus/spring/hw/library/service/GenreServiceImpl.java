package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw.library.dao.GenreDAO;
import ru.otus.spring.hw.library.domain.Genre;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDAO genreDAO;

    public GenreServiceImpl(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    @Override
    public Genre ensureGenre(String name) {
        return genreDAO.ensureGenre(name);
    }
}
