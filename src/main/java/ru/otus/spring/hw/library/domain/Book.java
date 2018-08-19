package ru.otus.spring.hw.library.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
public class Book {

    @Id
    private String id;

    private String title;
    @DBRef(lazy = false)
    private Set<Writer> authors = new HashSet<>();

    public Book(String id, String title) {
        this.id = id;
        this.title = title;
    }

    private Set<Genre> genres = new HashSet<>();
    private List<Comment> comments = new ArrayList<>();

    public Book(String id, String title, Set<Genre> genres, Set<Writer> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                ", comments=" + comments +
                '}';
    }

    public Book(String title, Set<Writer> authors, Set<Genre> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Writer> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Writer> authors) {
        this.authors = authors;
    }

    public void addAuthor(Writer author) {
        if (authors == null) {
            authors = new HashSet<>();
        }
        authors.add(author);
    }

    public void addGenre(Genre genre) {
        if (genres == null) {
            genres = new HashSet<>();
        }
        genres.add(genre);
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

}
