package ru.otus.spring.hw.library.domain;

import java.util.Set;

public class Genre {


    private String name;
    private Set<Book> books;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
