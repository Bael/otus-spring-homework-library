package ru.otus.spring.hw.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.hw.library.domain.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    List<Genre> findGenresByName(String name);

    List<Genre> findGenresByNameLike(String name);

    Genre findByName(String name);


    @Query(value = "SELECT g.* FROM BOOKS b join BOOK_GENRE bg on bg.bookid = b.id "
            + " join GENRES g on g.id = bg.genreid WHERE b.id = ?", nativeQuery = true)
    List<Genre> genresByBookId(long bookId);


}
