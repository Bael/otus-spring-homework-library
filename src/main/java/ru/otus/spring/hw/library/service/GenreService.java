package ru.otus.spring.hw.library.service;

import ru.otus.spring.hw.library.domain.Genre;

public interface GenreService {

    Genre ensureGenre(String name);
}
