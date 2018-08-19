package ru.otus.spring.hw.library.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Writer {

    @Id
    private String id;

    private String name;

    public Writer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Writer() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public Writer(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
