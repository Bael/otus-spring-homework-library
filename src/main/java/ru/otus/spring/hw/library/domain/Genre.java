package ru.otus.spring.hw.library.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Genres")
@NamedNativeQuery(query = "SELECT g.* FROM BOOKS b join BOOK_GENRE bg on bg.bookid = b.id join GENRES g on g.id = bg.genreid WHERE b.id = :bookId"
        , name = "Genres.queryGenresByBookId", resultClass = Genre.class)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 100)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "genres")
    private Set<Book> books;

    public Genre() {
    }

    public Genre(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Genre(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return (books == null ? new HashSet<>() : books);
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
