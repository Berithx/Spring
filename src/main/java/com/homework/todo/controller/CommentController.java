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
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable @Positive Long todoId, @Validated(ValidationGroups.Create.class) @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        CommentResponseDto responseDto = commentService.createComment(todoId, requestDto, request);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping({"/query/{todoId}/comments", "/query//comments"})
    public ResponseEntity<List<CommentResponseDto>> getComment(@Validated @PathVariable @Positive Long todoId) {
        List<CommentResponseDto> responseList = commentService.getComment(todoId);
        return ResponseEntity.ok(responseList);
    }

    @PutMapping({"/query/{todoId}/comments/{commentId}", "/query//comments", "/query//comments/"})
    public ResponseEntity<CommentResponseDto> updateComment(@Validated @PathVariable @Positive Long todoId, @Validated @PathVariable @Positive Long commentId, @Validated(ValidationGroups.Update.class) @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.updateComment(todoId, commentId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/query/{todoId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@Validated @PathVariable @Positive Long todoId, @Validated @PathVariable @Positive Long commentId) {
            commentService.deleteComment(todoId, commentId);
            return ResponseEntity.ok("정상 삭제 처리되었습니다.");
    }
}
