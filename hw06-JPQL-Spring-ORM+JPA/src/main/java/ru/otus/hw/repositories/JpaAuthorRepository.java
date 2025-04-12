package ru.otus.hw.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaAuthorRepository implements AuthorRepository {
    @Override
    public List<Author> findAll() {
        return List.of();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.empty();
    }
}
