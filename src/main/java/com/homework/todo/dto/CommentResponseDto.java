package com.homework.todo.dto;

import com.homework.todo.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentResponseDto {
    private Long id;
    private Long todoId;
    private String comment;
    private String username;
    private LocalDate createAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.username = comment.getUser().getUsername();
        this.createAt = comment.getCreatedAt();
        this.todoId = comment.getTodo().getId();
    }
}
