package ru.otus.spring.hw.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.hw.library.domain.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    List<Genre> findGenresByName(String name);

    List<Genre> findGenresByNameLike(String name);

    Genre findByName(String name);


    @Query(value = "SELECT distinct g FROM Book b join b.genres g WHERE b.id = :bookId")
    List<Genre> genresByBookId(@Param("bookId") long bookId);




}
