package ru.otus.spring.hw.library.domain;

public class Genre {

    private Long id;

    private final String name;

    public Genre(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Genre(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
