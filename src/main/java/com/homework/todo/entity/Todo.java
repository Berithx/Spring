package com.homework.todo.entity;

import com.homework.todo.dto.TodoRequestDto;
import jakarta.persistence.*;
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

    @Column(name = "contents", length = 500)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Todo(TodoRequestDto todoRequestDto, User user) {
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.user = user;
    }

    public void update(TodoRequestDto todoRequestDto) {
        if (todoRequestDto.getTitle() != null) {
            this.title = todoRequestDto.getTitle();
        }
        if (todoRequestDto.getContents() != null) {
            this.contents = todoRequestDto.getContents();
        }
    }

    public void checkPassword(User user) {
        if(!this.user.getPassword().equals(user.getPassword())) {
            throw new InvalidParameterException("비밀번호가 일치하지 않습니다.");
        }
    }
}
