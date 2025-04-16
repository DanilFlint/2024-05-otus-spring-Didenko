package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import(JpaAuthorRepository.class)
public class JpaAuthorRepositoryTest {

    private static final long FIRST_AUTHOR_ID = 1L;

    @Autowired
    private AuthorRepository repositoryJpa;

    private List<Author> dbAuthors;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
    }

    @Test
    public void shouldFindAuthorById() {
        var optionalAuthor = repositoryJpa.findById(FIRST_AUTHOR_ID);
        var expectedAuthor = dbAuthors.get((int) (FIRST_AUTHOR_ID - 1L));
        assertThat(optionalAuthor).isPresent()
                .get().usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    public void shouldFindAllAuthors() {
        var actualAuthors = repositoryJpa.findAll();
        var expectedAuthors = dbAuthors;
        assertThat(actualAuthors).containsExactlyElementsOf(expectedAuthors);
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }
}
