package ru.otus.spring.hw.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.hw.library.domain.Writer;

import java.util.List;

public interface WriterRepository extends CrudRepository<Writer, Long> {

    List<Writer> findByNameLike(String name);

    Writer findByName(String name);

    @Query(value = "select distinct w from Book b join b.authors w join b.genres g where g.name = :name ")
    List<Writer> authorsByGenre(@Param("name") String genre);


}
