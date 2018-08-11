package ru.otus.spring.hw.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.hw.library.domain.Writer;

import java.util.List;

public interface WriterRepository extends CrudRepository<Writer, Long> {

    List<Writer> findByNameLike(String name);

    Writer findByName(String name);

    @Query(value = "select writers.* from writers inner join book_author ba on writers.id = ba.authorid  " +
            " inner join books on books.id = ba.bookid " +
            " inner join book_genre bg on books.id = bg.bookid " +
            " inner join genres on genres.id = bg.genreid where genres.name = ? "
            , nativeQuery = true)
    List<Writer> authorsByGenre(String genre);



}
