package com.homework.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "내용을 필수 입력 값입니다.", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Length (max = 500, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String comment;
}
