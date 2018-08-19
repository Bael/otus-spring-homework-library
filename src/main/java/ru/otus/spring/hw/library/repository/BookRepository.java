package ru.otus.spring.hw.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.hw.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {


    Optional<Book> findByTitle(String title);

    List<Book> findByTitleLike(String title);

    List<Book> findAll();

    @Query(value = "{'genres.name' : ?0 }")
    List<Book> findByGenresContaining(String genre);

}
