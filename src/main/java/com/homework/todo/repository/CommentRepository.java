package com.homework.todo.repository;

import com.homework.todo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTodoIdOrderById(Long todoId);

    Optional<Comment> findAllByIdAndTodoId(Long commentId, Long todoId);
}
