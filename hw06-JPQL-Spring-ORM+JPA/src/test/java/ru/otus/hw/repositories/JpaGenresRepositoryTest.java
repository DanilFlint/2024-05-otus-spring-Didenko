package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(JpaGenreRepository.class)
public class JpaGenresRepositoryTest {

    private static final long FIRST_GENRE_ID = 1L;

    @Autowired
    private GenreRepository repositoryJpa;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp() {
        dbGenres = getDbGenres();
    }

    @Test
    public void shouldFindAuthorById() {
        var optionalGenre = repositoryJpa.findById(FIRST_GENRE_ID);
        var expectedGenre = dbGenres.get((int) (FIRST_GENRE_ID - 1L));
        assertThat(optionalGenre).isPresent()
                .get().usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    public void shouldFindAllAuthors() {
        var actualGenres = repositoryJpa.findAll();
        var expectedGenres = dbGenres;
        assertThat(actualGenres).containsExactlyElementsOf(expectedGenres);
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }
}
