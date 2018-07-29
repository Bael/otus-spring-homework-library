package ru.otus.spring.hw.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw.library.domain.Writer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class WriterDAOImpl implements WriterDAO {

    private NamedParameterJdbcOperations jdbc;

    public WriterDAOImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public long createWriter(Writer writer) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("name", writer.getName());

        final SqlParameterSource ps = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update("Insert into writers (`name`) values (:name)", ps, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void updateWriter(Writer writer) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", writer.getId());
        params.put("name", writer.getName());
        jdbc.update("update writers set `name` = :name where id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbc.update("delete from writers where id = :id ", params);
    }

    @Override
    public List<Writer> findByName(String name) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("name", "%" + name + "%");
        return jdbc.query(" select * from writers where name like :name", params, new WriterMapper());
    }

    @Override
    public List<Writer> findAll() {
        return jdbc.query(" select * from writers ", new WriterMapper());
    }

    @Override
    public Writer findById(long id) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(" select * from writers where id = :id ", params, new WriterMapper());
    }

    @Override
    public Writer ensureByName(String name) {
        Writer writer = findByExactName(name);
        if (writer == null) {
            writer = new Writer(name);
            long id = createWriter(writer);
            writer = findById(id);
        }
        return writer;


    }


    private Writer findByExactName(String name) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        List<Writer> writers = jdbc.query(" select * from writers where name = :name ", params, new WriterMapper());
        if (writers.size() == 0) {
            return null;
        } else {
            return writers.get(0);
        }
    }

    @Override
    public List<Writer> authorsByBookId(long bookId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        return jdbc.query("select writers.* from writers inner join book_author ba on writers.id = ba.authorid  "
                        + " inner join books on books.id = ba.bookid where books.id = :bookId",
                params, new WriterMapper());
    }

    @Override
    public List<Writer> authorsByGenre(String genre) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("genre", genre);
        return jdbc.query("select writers.* from writers inner join book_author ba on writers.id = ba.authorid  "
                        + " inner join books on books.id = ba.bookid "
                        + " inner join book_genre bg on books.id = bg.bookid "
                        + " inner join genres on genres.id = bg.genreid where genres.name = :genre "
                ,
                params, new WriterMapper());

    }

    private static class WriterMapper implements RowMapper<Writer> {

        @Override
        public Writer mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Writer(id, name);
        }
    }
}
