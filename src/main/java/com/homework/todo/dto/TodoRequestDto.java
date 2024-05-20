package com.homework.todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodoRequestDto {
    @NotNull(message = "제목은 필수 입력 값입니다.")
    @NotEmpty(message = "제목을 공백으로 할 수 없습니다.")
    @Length (max = 200)
    private String title;

    @NotNull(message = "내용은 필수 입력 값입니다.")
    @NotEmpty(message = "내용을 공백으로 할 수 없습니다.")
    private String contents;

    @NotEmpty(message = "user를 공백으로 할 수 없습니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String user;

    @NotNull(message = "비밀번호은 필수 입력 값입니다.")
    @NotEmpty(message = "비밀번호에 공백을 사용할 수 없습니다")
    private String password;
}
