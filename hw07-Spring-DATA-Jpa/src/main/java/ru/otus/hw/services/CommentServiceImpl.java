package ru.otus.hw.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository repository;

    @Transactional
    @Override
    public Optional<Comment> findById(long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public List<Comment> findByBookId(long id) {
        return repository.findByBookId(id);
    }
}
