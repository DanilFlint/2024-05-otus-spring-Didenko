package ru.otus.hw.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaGenreRepository implements GenreRepository {
    @Override
    public List<Genre> findAll() {
        return List.of();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.empty();
    }
}
