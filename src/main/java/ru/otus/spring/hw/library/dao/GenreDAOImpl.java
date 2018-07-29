package ru.otus.spring.hw.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class GenreDAOImpl implements GenreDAO {

    private NamedParameterJdbcOperations jdbc;

    private final GenreMapper genreMapper;

    public GenreDAOImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
        genreMapper = new GenreMapper();
    }

    @Override
    public long createGenre(Genre genre) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("name", genre.getName());

        final SqlParameterSource ps = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update("Insert into genres (`name`) values (:name)", ps, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Genre findById(long id) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(" select * from genres where id = :id ", params, genreMapper);
    }

    @Override
    public void updateGenre(Genre genre) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        jdbc.update("update genres set `name` = :name where id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbc.update("delete from genres where id = :id ", params);

    }

    @Override
    public List<Genre> findGenres(String name) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("name", "%" + name + "%");

        return jdbc.query(" select * from genres where name like :name", params, genreMapper);

    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query(" select * from genres  ", genreMapper);
    }

    @Override
    public List<Genre> genresByBookId(long bookId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        return jdbc.query("select genres.* from books inner join book_genre bg on books.id = bg.bookid  "
                + " inner join genres on genres.id = bg.genreid where books.id = :bookId", params, genreMapper);

    }

    @Override
    public Genre ensureGenre(String name) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        List<Genre> genres = jdbc.query(" select * from genres where name = :name", params, genreMapper);
        if (genres.size() == 0) {
            long id = createGenre(new Genre(name));
            return findById(id);
        }
        return genres.get(0);

    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }

}
