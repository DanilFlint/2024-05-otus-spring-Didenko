package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class JpaCommentRepository implements CommentRepository {
    private EntityManager entityManager;

    @Override
    public Optional<Comment> findById(long id) {
        var r = Optional.ofNullable(entityManager.find(Comment.class, id));

        return r;
    }

    @Override
    public List<Comment> findByBookId(long id) {
        TypedQuery<Comment> query = entityManager
                .createQuery("select c from Comment c where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", id);
        var r = query.getResultList();

        return r;
    }
}
