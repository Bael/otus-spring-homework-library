package ru.otus.spring.hw.library.domain;

public class Writer {

    private long id;
    private String name;

    public Writer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Writer(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
