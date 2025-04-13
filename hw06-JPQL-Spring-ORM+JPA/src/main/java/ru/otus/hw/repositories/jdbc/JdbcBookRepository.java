package ru.otus.hw.repositories.jdbc;

import lombok.Getter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
//@Repository
public class JdbcBookRepository implements BookRepository {

    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    private JdbcOperations jdbcOperations;

    public JdbcBookRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.jdbcOperations = namedParameterJdbcOperations.getJdbcOperations();
    }

    @Override
    public Optional<Book> findById(long id) {
        var sql = "select " +
                        "books.id as id, books.title as title, " +
                        "authors.id as authorId, authors.full_name as authorFullName, " +
                        "genres.id as genreId, genres.name as genreName from books " +
                "join authors on books.author_id = authors.id " +
                "join genres on books.genre_id = genres.id " +
                "where books.id = ?";

        Book result;

        try {
            result = jdbcOperations.queryForObject(sql, new BookRowMapper(), id);
        } catch (DataAccessException e) {
            result = null;
        }

        return Optional.ofNullable(result);
    }

    @Override
    public List<Book> findAll() {
        var sql = "select " +
                "books.id as id, books.title as title, " +
                "authors.id as authorId, authors.full_name as authorFullName, " +
                "genres.id as genreId, genres.name as genreName from books " +
                "join authors on books.author_id = authors.id " +
                "join genres on books.genre_id = genres.id";

        return jdbcOperations.query(sql, new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        jdbcOperations.update("delete from books where id = ?", id);
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        var sql = "insert into books (title, author_id, genre_id) values (:title, :author_id, :genre_id)";

        var mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("title", book.getTitle());
        mapSqlParameterSource.addValue("author_id", book.getAuthor().getId());
        mapSqlParameterSource.addValue("genre_id", book.getGenre().getId());

        namedParameterJdbcOperations.update(sql, mapSqlParameterSource, keyHolder, new String[]{"id"});

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKey().longValue());
        return book;
    }

    private Book update(Book book) {
        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        var sql = "update books set title = :title, author_id = :authorId, genre_id = :genreId where id = :id";

        var params = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId()
        );

        var result = namedParameterJdbcOperations.update(sql, params);
        if (result == 0) {
            throw new EntityNotFoundException("Book with id %d not found".formatted(book.getId()));
        }

        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var author = new Author(rs.getLong("authorId"), rs.getString("authorFullName"));
            var genre = new Genre(rs.getLong("genreId"), rs.getString("genreName"));

            return new Book(rs.getLong("id"), rs.getString("title"), author, genre);
        }
    }
}
