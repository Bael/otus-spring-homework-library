package ru.otus.spring.hw.library.dao;

import ru.otus.spring.hw.library.domain.Genre;

import java.util.List;

public interface GenreDAO {

    long createGenre(Genre genre);

    Genre findById(long id);

    void updateGenre(Genre genre);

    void deleteById(long id);

    List<Genre> findGenres(String name);

    List<Genre> findAll();

    List<Genre> genresByBookId(long bookId);

    Genre ensureGenre(String name);

}
