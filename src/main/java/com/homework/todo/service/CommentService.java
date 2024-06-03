package com.homework.todo.service;

import com.homework.todo.dto.CommentRequestDto;
import com.homework.todo.dto.CommentResponseDto;
import com.homework.todo.entity.Comment;
import com.homework.todo.entity.Todo;
import com.homework.todo.entity.User;
import com.homework.todo.jwt.JwtUtil;
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
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, String token) {
        User user = userService.findByUsername(jwtUtil.getUserInfoFromToken(token).getSubject());
        Todo todo = todoService.findTodoById(todoId);

        Comment comment = new Comment(todo, requestDto, user);

        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComment(Long todoId) {
        todoService.existsById(todoId);
        return commentRepository.findAllByTodoIdOrderById(todoId).stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public CommentResponseDto updateComment(Long todoId, Long commentId, CommentRequestDto requestDto, String token) {
        if (!hasAccess(todoId, commentId, token)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        Comment comment = findCommentById(todoId, commentId);
        if (comment != null) {
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new NoSuchElementException("수정하려는 댓글이 존재하지 않습니다.");
        }
    }

    @Transactional
    public void deleteComment(Long todoId, Long commentId, String token) {
        if (!hasAccess(todoId, commentId, token)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        Comment comment = findCommentById(todoId, commentId);
        commentRepository.delete(comment);
    }

    private boolean hasAccess(Long todoId, Long commentId, String token) {
        String commentUsername = findCommentById(todoId, commentId).getUser().getUsername();
        return commentUsername.equals(jwtUtil.getUserInfoFromToken(token).getSubject());
    }

    private Comment findCommentById(Long todoId, Long commentId) {
        return commentRepository.findAllByIdAndTodoId(commentId, todoId).orElseThrow(
                () -> new NoSuchElementException("삭제하려는 댓글이 존재하지 않습니다.")
        );
    }
}
