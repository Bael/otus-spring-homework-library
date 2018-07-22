package ru.otus.spring.hw.library.domain;

import java.util.ArrayList;
import java.util.List;

public class Book {

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
        genres = new ArrayList<>();
        authors = new ArrayList<>();
    }

    public Book() {
    }

    public Book(long id, String title, List<Genre> genres, List<Writer> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String title, List<Writer> authors, List<Genre> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Writer> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Writer> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    private long id;
    private String title;
    private List<Writer> authors;
    private List<Genre> genres;

}
