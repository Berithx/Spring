package com.homework.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "이름은 영어 소문자와 숫자를 포함해서 4~10자 이내로 입력해주세요.")
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 영어와 숫자를 포함해서 8~15자 이내로 입력해주세요.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
