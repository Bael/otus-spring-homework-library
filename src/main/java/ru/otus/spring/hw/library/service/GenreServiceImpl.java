package ru.otus.spring.hw.library.service;

import org.springframework.stereotype.Service;
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
    public Genre ensureGenre(String name) {
        return genreRepository.ensureGenre(name);
    }

    @Override
    public List<Genre> getGenresByBookId(long id) {
        return genreRepository.genresByBookId(id);
    }
}
