package ru.otus.spring.hw.library.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Books")
@NamedNativeQuery(query = "select books.* from books join book_author as ba on books.id = ba.bookid  "
        + " join writers on writers.id = ba.authorid where writers.name like :authorName"
        , name = "Books.queryBooksByAuthorName", resultClass = Book.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String title;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "bookid"),
            inverseJoinColumns = @JoinColumn(name = "authorid"))
    private Set<Writer> authors = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)

    @JoinTable(name = "BOOK_GENRE",
            joinColumns = @JoinColumn(name = "bookid"),
            inverseJoinColumns = @JoinColumn(name = "genreid"))
    private Set<Genre> genres = new HashSet<>();


    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(long id, String title, Set<Genre> genres, Set<Writer> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String title, Set<Writer> authors, Set<Genre> genres) {
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
        author.getBooks().add(this);
    }

    public void addGenre(Genre genre) {
        if (genres == null) {
            genres = new HashSet<>();
        }
        genres.add(genre);
        genre.getBooks().add(this);
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                '}';
    }
}
