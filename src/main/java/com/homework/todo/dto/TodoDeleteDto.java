package com.homework.todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class TodoDeleteDto {
    @NotNull(message = "비밀번호은 필수 입력 값입니다.")
    @NotBlank(message = "비밀번호에 공백을 사용할 수 없습니다")
    private String password;
}
