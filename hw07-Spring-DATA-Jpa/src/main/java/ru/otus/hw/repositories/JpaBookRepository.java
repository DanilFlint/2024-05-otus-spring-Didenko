package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;
import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Getter
@Repository
@AllArgsConstructor
public class JpaBookRepository implements BookRepository {
    private EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        var graph = em.getEntityGraph("book_graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.id = :id", Book.class);
        query.setParameter("id", id);
        query.setHint(FETCH.getKey(), graph);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        var graph = em.getEntityGraph("book_graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint(FETCH.getKey(), graph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        var result = em.merge(book);
        if (result.getId() != book.getId()) {
            throw new EntityNotFoundException("Book with id %d not found".formatted(book.getId()));
        }
        return result;
    }

    @Override
    public void deleteById(long id) {
        var query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
