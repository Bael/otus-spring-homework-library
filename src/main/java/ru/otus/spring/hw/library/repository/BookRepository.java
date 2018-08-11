package ru.otus.spring.hw.library.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.hw.library.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {


    List<Book> findByTitle(String title);

    List<Book> findByTitleLike(String title);

    List<Book> findAll();

}
