package com.homework.todo.controller;

import com.homework.todo.dto.CommentRequestDto;
import com.homework.todo.dto.CommentResponseDto;
import com.homework.todo.service.CommentService;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/query/{todoId}/comments")
    public CommentResponseDto createComment(@PathVariable(required = false) @Positive Long todoId, @Validated @RequestBody CommentRequestDto requestDto) {
        if (todoId == null) {
            throw new NullPointerException("URL의 ID값이 공백입니다.");
        }
        return commentService.createComment(todoId, requestDto);
    }

    @GetMapping("/query/{todoId}/comments")
    public List<CommentResponseDto> getComment(@PathVariable(required = false) @Positive Long todoId) {
        if (todoId == null) {
            throw new NullPointerException("URL의 ID값이 공백입니다.");
        }
        return commentService.getComment(todoId);
    }
}
