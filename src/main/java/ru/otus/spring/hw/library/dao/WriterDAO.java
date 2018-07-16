package ru.otus.spring.hw.library.dao;

import ru.otus.spring.hw.library.domain.Writer;

import java.util.List;

public interface WriterDAO {

    long createWriter(Writer writer);
    void updateWriter(Writer writer);
    void deleteWriter(Writer writer);
    List<Writer> findByName(String name);



}
