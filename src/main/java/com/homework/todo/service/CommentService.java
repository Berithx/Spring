package com.homework.todo.service;

import com.homework.todo.dto.CommentRequestDto;
import com.homework.todo.dto.CommentResponseDto;
import com.homework.todo.entity.Comment;
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
        todoService.existsById(todoId);
        Comment comment = new Comment(todoId, requestDto);

        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComment(Long todoId) {
        existsByTodoId(todoId);
        return commentRepository.findAllByTodoIdOrderById(todoId).stream().map(CommentResponseDto::new).toList();
    }

    @Transactional(readOnly = false)
    public CommentResponseDto updateComment(Long todoId, Long commentId, CommentRequestDto requestDto) {
        existsByTodoId(todoId);
        Comment comment = findCommentById(todoId, commentId);
        if (comment != null) {
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new NoSuchElementException("수정하려는 댓글이 존재하지 않습니다.");
        }
    }

    @Transactional(readOnly = false)
    public void deleteComment(Long todoId, Long commentId) {
        existsByTodoId(todoId);
        Comment comment = findCommentById(todoId, commentId);
        if (comment != null) {
            commentRepository.delete(comment);
        } else {
            throw new NoSuchElementException("삭제하려는 댓글이 존재하지 않습니다.");
        }
    }

    private void existsByTodoId(Long todoId) {
        if (!todoService.existsById(todoId)) {
            throw new NoSuchElementException("존재하지 않는 일정입니다.");
        }
    }

    private Comment findCommentById(Long todoId, Long commentId) {
        return commentRepository.findAllByIdAndTodoId(commentId, todoId);
    }
}
