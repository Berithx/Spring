package com.homework.todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class TodoDeleteDto {
    @NotBlank(message = "비밀번호은 필수 입력 값입니다.")
    private String password;
}
