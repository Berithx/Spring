package com.homework.todo.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private String title;
    private String contents;
    private String user;
    private String password;
}
