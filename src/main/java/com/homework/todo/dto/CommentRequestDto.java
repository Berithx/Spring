package com.homework.todo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "내용을 필수 입력 값입니다.", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Length (max = 500, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String comment;

    @NotBlank(message = "사용자 이름은 필수 입력 값입니다.", groups = {ValidationGroups.Create.class})
    @Email(message = "이메일 형식에 맞지 않습니다", groups = {ValidationGroups.Create.class})
    @Length(max = 50, groups = {ValidationGroups.Create.class})
    private String username;
}
