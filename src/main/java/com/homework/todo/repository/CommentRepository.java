package com.homework.todo.repository;

import com.homework.todo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTodoIdOrderById(Long todoId);

    Comment findAllByIdAndTodoId(Long commentId, Long todoId);
}
