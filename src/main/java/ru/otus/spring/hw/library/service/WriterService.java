package ru.otus.spring.hw.library.service;

import ru.otus.spring.hw.library.domain.Writer;

import java.util.Set;

public interface WriterService {

    Set<Writer> findAll();

    Writer ensureWriter(String name);

    Set<Writer> authorsByGenre(String genre);

    Set<Writer> getAuthorsByBookId(long id);

    Writer findByName(String name);
}
