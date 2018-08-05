package ru.otus.spring.hw.library.service;

import ru.otus.spring.hw.library.domain.Writer;

import java.util.List;

public interface WriterService {

    void createWriter(Writer writer);

    List<Writer> findAll();

    Writer ensureWriter(String name);

    List<Writer> authorsByGenre(String genre);

    List<Writer> getAuthorsByBookId(long id);
}
