package ru.otus.spring.hw.library.dao;

import ru.otus.spring.hw.library.domain.Writer;

import java.util.List;

public interface WriterDAO {

    long createWriter(Writer writer);
    void updateWriter(Writer writer);

    void deleteById(long id);
    List<Writer> findByName(String name);

    List<Writer> findAll();

    Writer findById(long id);

    List<Writer> authorsByBookId(long bookId);


}
