package ru.otus.spring.hw.library.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.hw.library.domain.Writer;

import java.util.List;

public interface WriterRepository extends MongoRepository<Writer, String> {

    List<Writer> findByNameLike(String name);

    Writer findByName(String name);


}
