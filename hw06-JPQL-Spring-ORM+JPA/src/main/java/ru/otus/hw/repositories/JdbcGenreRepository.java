package ru.otus.hw.repositories;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Repository
public class JdbcGenreRepository implements GenreRepository {

    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    private JdbcOperations jdbcOperations;

    public JdbcGenreRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.jdbcOperations = namedParameterJdbcOperations.getJdbcOperations();
    }

    @Override
    public List<Genre> findAll() {
        return jdbcOperations.query("select id, name from genres", new JdbcGenreRepository.GnreRowMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        var sql = "select id, name from genres where id = :id";
        var params = Map.of("id", id);
        return Optional.ofNullable(namedParameterJdbcOperations
                .queryForObject(sql, params, new JdbcGenreRepository.GnreRowMapper()));
    }

    private static class GnreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }
}
