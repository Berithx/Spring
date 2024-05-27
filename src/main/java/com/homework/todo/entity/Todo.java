package com.homework.todo.entity;

import com.homework.todo.dto.TodoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "contents", length = 500)
    private String contents;

    @Column(name = "user", length = 50)
    private String user;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @OneToMany(mappedBy = "todo")
    private List<Comment> commentList = new ArrayList<>();

    public Todo(TodoRequestDto todoRequestDto) {
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.user = todoRequestDto.getUser();
        this.password = todoRequestDto.getPassword();
    }

    public void update(TodoRequestDto todoRequestDto) {
        if (todoRequestDto.getTitle() != null) {
            this.title = todoRequestDto.getTitle();
        }
        if (todoRequestDto.getContents() != null) {
            this.contents = todoRequestDto.getContents();
        }
        if (todoRequestDto.getUser() != null) {
            this.user = todoRequestDto.getUser();
        }
    }

    public void checkPassword(String requestDtoPassword) {
        if(!this.password.equals(requestDtoPassword)) {
            throw new InvalidParameterException("비밀번호가 일치하지 않습니다.");
        }
    }
}
