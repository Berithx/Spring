package com.homework.todo.service;

import com.homework.todo.dto.CommentRequestDto;
import com.homework.todo.dto.CommentResponseDto;
import com.homework.todo.entity.Comment;
import com.homework.todo.entity.Todo;
import com.homework.todo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoService todoService;

    @Transactional(readOnly = false)
    public CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto) {
        if (todoService.existsById(todoId)) {
            Comment comment = new Comment(todoId, requestDto);

            Comment saveComment = commentRepository.save(comment);

            return new CommentResponseDto(comment);
        } else {
            throw new NoSuchElementException("존재하지 않는 게시물입니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComment(Long todoId) {
        if (todoService.existsById(todoId)) {
            return commentRepository.findAllByTodoIdOrderById(todoId).stream().map(CommentResponseDto::new).toList();
        } else {
            throw new NoSuchElementException("존재하지 않는 게시물입니다.");
        }
    }
}
