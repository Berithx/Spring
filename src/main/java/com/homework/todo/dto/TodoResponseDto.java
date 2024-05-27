package com.homework.todo.dto;

import com.homework.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private LocalDate createdAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.username = todo.getUsername();
        this.createdAt = todo.getCreatedAt();
    }
}
