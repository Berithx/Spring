package com.homework.todo.controller;

import com.homework.todo.dto.CommentRequestDto;
import com.homework.todo.dto.CommentResponseDto;
import com.homework.todo.dto.ValidationGroups;
import com.homework.todo.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @PostMapping({"/query/{todoId}/comments", "/query//comments"})
    public CommentResponseDto createComment(@PathVariable @Positive Long todoId, @Validated(ValidationGroups.Create.class) @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
            return commentService.createComment(todoId, requestDto, request);
    }

    @GetMapping({"/query/{todoId}/comments", "/query//comments"})
    public List<CommentResponseDto> getComment(@Validated @PathVariable @Positive Long todoId) {
            return commentService.getComment(todoId);

    }

    @PutMapping({"/query/{todoId}/comments/{commentId}", "/query//comments", "/query//comments/"})
    public CommentResponseDto updateComment(@Validated @PathVariable @Positive Long todoId, @Validated @PathVariable @Positive Long commentId, @Validated(ValidationGroups.Update.class) @RequestBody CommentRequestDto requestDto) {
            return commentService.updateComment(todoId, commentId, requestDto);
    }

    @DeleteMapping("/query/{todoId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@Validated @PathVariable @Positive Long todoId, @Validated @PathVariable @Positive Long commentId) {
            commentService.deleteComment(todoId, commentId);
            return ResponseEntity.ok("정상 삭제처리 되었습니다.");
    }
}
