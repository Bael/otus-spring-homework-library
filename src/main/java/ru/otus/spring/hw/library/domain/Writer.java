package ru.otus.spring.hw.library.domain;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Writers")
@NamedNativeQuery(query = "select distinct writers.* from writers inner join book_author ba on writers.id = ba.authorid  "
        + " inner join books on books.id = ba.bookid "
        + " inner join book_genre bg on books.id = bg.bookid "
        + " inner join genres on genres.id = bg.genreid where genres.name = :genre "
        , name = "Writers.queryAuthorsByGenre", resultClass = Writer.class)
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "authors")
    private Set<Book> books;

    public Writer() {
    }

    public Set<Book> getBooks() {
        return (books == null ? new HashSet<>() : books);
    }

    public Writer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Writer(String name) {
        this.name = name;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
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
