package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaCommentRepository.class)
public class JpaCommentRepositoryTest {
    private static final long FIRST_ID = 1L;

    @Autowired
    private CommentRepository repositoryJpa;

    private List<Comment> dbComments;

    @BeforeEach
    void setUp() {
        dbComments = getDBComments();
    }

    @Test
    public void shouldFindCommentById() {
        var optionalComment = repositoryJpa.findById(FIRST_ID);
        var expectedComment = dbComments.get((int) (FIRST_ID - 1L));
        assertThat(optionalComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @Test
    public void shouldFindCommentByBookId() {
        var actualComments = repositoryJpa.findByBookId(FIRST_ID);
        var expectedComments = dbComments.stream().limit(1).toList();

        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
        actualComments.forEach(System.out::println);
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    private static List<Comment> getDBComments() {
        var books = getDbBooks(getDbAuthors(), getDbGenres());

        return IntStream.range(1, 4).boxed()
                .map(id -> new Comment(id, "Comment_" + id, books.get(id - 1)))
                .toList();
    }
}
