package com.homework.todo.dto;

import com.homework.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoResponseDto {
    private String title;
    private String contents;
    private String manager;
    private LocalDate created_At;

    public TodoResponseDto(Todo todo) {
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.manager = todo.getManager();
        this.created_At = todo.getDate();
    }
}
