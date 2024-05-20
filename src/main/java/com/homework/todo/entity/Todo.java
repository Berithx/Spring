package com.homework.todo.entity;

import com.homework.todo.dto.TodoRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.InvalidParameterException;

@Entity
@Getter
@Setter
@Table(name = "todo")
@NoArgsConstructor
public class Todo extends Date{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Column(name = "user", nullable = false, length = 50)
    private String user;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    public Todo(TodoRequestDto todoRequestDto) {
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.user = todoRequestDto.getUser();
        this.password = todoRequestDto.getPassword();
    }

    public void update(TodoRequestDto todoRequestDto) {
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.user = todoRequestDto.getUser();
    }

    public void checkPassword(String requestDtoPassword) {
        if(!this.password.equals(requestDtoPassword)) {
            throw new InvalidParameterException("비밀번호가 일치하지 않습니다.");
        }
    }
}
