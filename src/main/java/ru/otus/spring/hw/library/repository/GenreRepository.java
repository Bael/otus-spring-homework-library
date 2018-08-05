package ru.otus.spring.hw.library.repository;

import ru.otus.spring.hw.library.domain.Genre;

import java.util.List;

public interface GenreRepository {

    void createGenre(Genre genre);

    Genre findById(long id);

    void updateGenre(Genre genre);

    void deleteById(long id);

    List<Genre> findGenres(String name);

    Genre findGenre(String name);

    List<Genre> findAll();

    List<Genre> genresByBookId(long bookId);

    Genre ensureGenre(String name);

}
