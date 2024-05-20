package com.homework.todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodoRequestDto {
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    @Length (max = 200)
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String contents;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String user;

    @NotBlank(message = "비밀번호은 필수 입력 값입니다.")
    private String password;
}
