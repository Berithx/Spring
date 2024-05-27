package com.homework.todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodoRequestDto {
    @NotBlank(message = "제목은 필수 입력 값입니다.", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Length (max = 200, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String title;

    @Length(max = 500, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String contents;

    @NotBlank(message = "유저는 공백일 수 없습니다.", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Email(message = "이메일 형식에 맞지 않습니다.", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String username;

    @NotBlank(message = "비밀번호은 필수 입력 값입니다.", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class, ValidationGroups.Delete.class})
    private String password;
}
