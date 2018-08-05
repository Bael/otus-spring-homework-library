package ru.otus.spring.hw.library.repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Writer;

import java.util.List;

public interface WriterRepository {

    void createWriter(Writer writer);
    void updateWriter(Writer writer);

    void deleteById(long id);
    List<Writer> findByName(String name);

    List<Writer> findAll();

    Writer findById(long id);

    Writer ensureByName(String name);

    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    Writer findByExactName(String name);

    //List<Writer> authorsByBookId(long bookId);

    List authorsByGenre(String genre);


}
