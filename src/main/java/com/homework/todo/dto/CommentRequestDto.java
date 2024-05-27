package com.homework.todo.dto;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "내용을 필수 입력 값입니다.")
    @Length (max = 500)
    private String comment;

    @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
    @Length(max = 50)
    private String username;
}
