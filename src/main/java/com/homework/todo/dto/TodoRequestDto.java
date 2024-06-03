package com.homework.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodoRequestDto {
    @NotBlank(message = "제목은 공백일 수 없습니다.", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Length (max = 200, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String title;

    @Length(max = 500, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String contents;
}
