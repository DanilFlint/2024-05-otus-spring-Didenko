package ru.otus.hw.repositories.jdbc;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    private JdbcOperations jdbcOperations;

    public JdbcAuthorRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.jdbcOperations = namedParameterJdbcOperations.getJdbcOperations();
    }

    @Override
    public List<Author> findAll() {
        return jdbcOperations.query("select id, fullName from authors", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        var params = Map.of("id", id);
        return Optional.ofNullable(namedParameterJdbcOperations
                .queryForObject("select id, fullName from authors where id = :id", params, new AuthorRowMapper()));
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("fullName"));
        }
    }
}
