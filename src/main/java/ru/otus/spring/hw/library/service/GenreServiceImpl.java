package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Genre;
import ru.otus.spring.hw.library.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Genre ensureGenre(String name) {
        Genre g = genreRepository.findByName(name);
        if (g == null) {
            g = new Genre(name);
            genreRepository.save(g);
        }
        return g;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Genre> getGenresByBookId(long id) {
        return genreRepository.genresByBookId(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }
}
