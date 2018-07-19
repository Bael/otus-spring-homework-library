package ru.otus.spring.hw.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw.library.domain.Book;
import ru.otus.spring.hw.library.domain.Genre;
import ru.otus.spring.hw.library.domain.Writer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class BookDAOImpl implements BookDAO {


    private static final String SELECT_STATEMENT = "SELECT * FROM BOOKS ";

    private NamedParameterJdbcOperations jdbc;
    private GenreDAO genreDAO;
    private WriterDAO writerDAO;


    public BookDAOImpl(NamedParameterJdbcOperations jdbc, GenreDAO genreDAO, WriterDAO writerDAO) {
        this.jdbc = jdbc;
        this.genreDAO = genreDAO;
        this.writerDAO = writerDAO;
    }


    @Override
    @Transactional
    public long createBook(Book book) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());

        final SqlParameterSource ps = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update("Insert into books (`title`) values (:title)", ps, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        insertGenres(id, book.getGenres());
        insertAuthors(id, book.getAuthors());

        return id;

    }

    /* genres of book */


    private void deleteGenres(long bookId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", bookId);
        jdbc.update("delete from BOOK_GENRE  where bookid = :id", params);
    }

    private void insertGenres(long bookId, List<Genre> genres) {
        Optional.ofNullable(genres)
                .ifPresent(genres1 ->
                        genres1.forEach(genre -> insertGenre(bookId, genre.getId())));

    }

    private void insertGenre(long bookId, long genreId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("genreId", genreId);
        jdbc.update("Insert into BOOK_GENRE (bookid, genreid) values (:bookId, :genreId)", params);
    }

    private void ensureGenres(long bookId, List<Genre> genres) {
        deleteGenres(bookId);
        insertGenres(bookId, genres);
    }

    /* authors of book */
    private void deleteAuthors(long bookId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", bookId);
        jdbc.update("delete from BOOK_AUTHOR where bookid = :id", params);
    }

    private void insertAuthors(long bookId, List<Writer> authors) {
        if (authors != null) {
            authors.forEach(
                    author ->
                            insertAuthor(bookId,
                                    author.getId()));
        }
    }

    private void insertAuthor(long bookId, long authorId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("authorId", authorId);
        jdbc.update("Insert into BOOK_AUTHOR (bookid, authorId) values (:bookId, :authorId)", params);
    }

    private void ensureAuthors(long bookId, List<Writer> authors) {
        deleteAuthors(bookId);
        insertAuthors(bookId, authors);
    }


    @Override
    public List<Book> findAll() {
        return jdbc.query(SELECT_STATEMENT, new BookMapper());
    }


    @Override
    public List<Book> findByTitle(String title) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("title", "%" + title + "%");
        return jdbc.query(SELECT_STATEMENT + "where title like :title", params, new BookMapper());
    }

    @Override
    public List<Book> findByAuthorName(String name) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("authorName", name);
        return jdbc.query("select books.* books join book_author ba books.id = ba.bookid  "
                        + " join authors on authors.id = ba.authorid where authors.name like :authorName",
                params, new BookMapper());

    }

    @Override
    public List<Book> findByGenreName(String name) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("genreName", name);
        return jdbc.query("select books.* books join book_genre ga books.id = ga.bookid  "
                        + " join genres on genres.id = ga.genreid where genres.name like :genreName",
                params, new BookMapper());
    }

    @Override
    public Book findById(long id) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(SELECT_STATEMENT + "where id = :id ", params, new BookMapper());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbc.update("delete from books where id = :id ", params);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {

        final HashMap<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());
        params.put("id", book.getId());
        jdbc.update("update books set title = :title where id = :id", params);
        System.out.println(book.getAuthors());
        ensureAuthors(book.getId(), book.getAuthors());
        ensureGenres(book.getId(), book.getGenres());
    }

    private class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {

            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            List<Genre> genres = genreDAO.genresByBookId(id);
            List<Writer> authors = writerDAO.authorsByBookId(id);
            return new Book(id, title, genres, authors);
        }
    }


}
