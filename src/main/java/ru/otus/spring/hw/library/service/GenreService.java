package ru.otus.spring.hw.library.service;

import ru.otus.spring.hw.library.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre ensureGenre(String name);

    List<Genre> getGenresByBookId(long id);
}
