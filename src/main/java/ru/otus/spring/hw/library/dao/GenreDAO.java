package ru.otus.spring.hw.library.dao;

import ru.otus.spring.hw.library.domain.Genre;

import java.util.List;

public interface GenreDAO {

    long createGenre(Genre genre);
    void updateGenre(Genre genre);
    void deleteGenre(Genre genre);
    List<Genre> findGenres(String name);
    List<Genre> findAll(String name);


}
