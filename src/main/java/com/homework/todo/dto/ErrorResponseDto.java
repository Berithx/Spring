package com.homework.todo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {
    private String message;
    private int status;

    public ErrorResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
